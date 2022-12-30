package analizer.analizers;

import analizer.parser.Log;
import application.products.Product;
import application.products.ProductRepository;
import application.products.exceptions.ProductNotFoundException;
import application.users.User;
import application.users.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

/**
 * the class which extract informations according to question 1.5
 */
public class ReadWriteAnalizer {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private List<Log> userLogs;// contains only INFO log

    public ReadWriteAnalizer(String usersPath, String productsPath, List<Log> logs) throws IOException {
        userRepository = new UserRepository(usersPath);
        productRepository = new ProductRepository(productsPath);
        userLogs = new ArrayList<>();

        for(Log l : logs){
            if(l.getLevel().equals("INFO")){
                userLogs.add(l);
            }
        }
    }

    /**
     * @return users that mostly write
     */
    public Set<String> mostWrite(){
        //getting every user from logs
        Set<String> users = new HashSet<>();
        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            users.add(userId);
        }

        //filter
        Set<String> res = new HashSet<>();
        for(String s : users){
            if(readCount(s) < writeCount(s)){//mostly write
                res.add(s);
            }
        }
        return res;
    }

    /**
     * @return users that mostly read
     */
    public Set<String> mostRead(){
        //getting every user from logs
        Set<String> users = new HashSet<>();
        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            users.add(userId);
        }

        //filter
        Set<String> res = new HashSet<>();
        for(String s : users){
            if(readCount(s) > writeCount(s)){//mostly read
                res.add(s);
            }
        }
        return res;
    }

    /**
     * @return users that searched for the most expensive item
     */
    public Set<String> mostExpensive() throws ProductNotFoundException {
        //getting every user from logs
        Set<String> users = new HashSet<>();
        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            users.add(userId);
        }

        //filter
        Set<String> res = new HashSet<>();
        for(String s : users){
            if(getMostExpensiveForUser(s) == getMostExpensive().getPrice()){//searched for the most expensive
                res.add(s);
            }
        }

        return res;
    }

    /**
     * @return the most expensive item that the user search
     */
    public Double getMostExpensiveForUser(String id){
        double price = 0;

        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            String methodId = (String) j.get("method");

            if(!(userId.equals(id) && isRead(l))){//not the corresponding user
               continue;
            }

            JSONArray paramList = (JSONArray) j.get("parameters");
            if(!isRead(l)){//not a read method
                continue;
            } else if (methodId.equals("getAllProduct")) {//in that case, it means the user searched for the most expensive item
                try {
                    return getMostExpensive().getPrice();
                } catch (ProductNotFoundException e) {
                    return 0.;
                }
            }

            //we are sure that the log is now a read method wich contains an id input from the user
            try {
                String param = (String) paramList.get(0);
                Double p = productRepository.getProduct(param).getPrice();
                if(p > price){
                    price = p;
                }
            }
            catch (IndexOutOfBoundsException e){}
            catch (ProductNotFoundException e) {}
        }
        return price;
    }

    /**
     * @return the most expensive item in the product repository
     */
    public Product getMostExpensive() throws ProductNotFoundException {
        String id = null;
        double price = 0;
        for(Product p : productRepository.getAllProduct()){
           if(p.getPrice() > price){
               price = p.getPrice();
               id = p.getId();
           }
        }
        return productRepository.getProduct(id);
    }

    /**
     * @return every INFO log from a specific user
     */
    public List<Log> getLogsForUser(String id){
        ArrayList<Log> res = new ArrayList<>();
        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            if(userId.equals(id)){
                res.add(l);
            }
        }
        return res;
    }

    /**
     * check if it's a read method
     */
    private boolean isRead(Log log){
        JSONObject j = log.getMessage();
        String methodName = (String) j.get("method");

        return methodName.contains("get") &&
                log.getClassName().contains("ProductRepository");
    }

    /**
     * check if it's a write method
     */
    private boolean isWrite(Log log){
        JSONObject j = log.getMessage();
        String methodName = (String) j.get("method");

        return (methodName.contains("add") || methodName.contains("delete") || methodName.contains("edit")) &&
                log.getClassName().contains("ProductRepository");
    }


    /**
     * count how many times a specific user has read
     */
    public int readCount(String id){
        int count = 0;
        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            if(userId.equals(id) && isRead(l)){
                count++;
            }
        }
        return count;
    }

    /**
     * count how many times a specific user has written
     */
    public int writeCount(String id){
        int count = 0;
        for(Log l : userLogs){
            JSONObject j = l.getMessage();
            String userId = (String) j.get("user");
            if(userId.equals(id) && isWrite(l)){
                count++;
            }
        }
        return count;
    }

    public User getUser(String id){
        return userRepository.getUser(id);
    }
}
