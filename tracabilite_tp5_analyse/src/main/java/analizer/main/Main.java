package analizer.main;

import analizer.analizers.CommonErrorsAnalizer;
import analizer.parser.Log;
import application.products.exceptions.ProductNotFoundException;
import application.users.User;
import analizer.analizers.ReadWriteAnalizer;
import analizer.parser.Parser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    static final String SEPARATOR = "---------------------------------------------------------------------";

    public static void main(String args[]) throws IOException, ParseException, ProductNotFoundException {
        Parser parser = new Parser();
        List<Log> logs = parser.parse("logs/examples/tp_example2.json");
        ReadWriteAnalizer readWriteAnalizer = new ReadWriteAnalizer("data/users.json", "data/products.json", logs);
        CommonErrorsAnalizer commonErrorsAnalizer = new CommonErrorsAnalizer(logs);

        /*
         * a profile for those that mostly performed read operations on your repository.
         */
        System.out.println(SEPARATOR);
        System.out.println("Users which mostly read : ");
        for(String u : readWriteAnalizer.mostRead()){
            List<Log> userLog = readWriteAnalizer.getLogsForUser(u);
            User user = readWriteAnalizer.getUser(u);
            System.out.println("\tid:" + user.getId() + " name:" + user.getName() + " number_of_read:" + readWriteAnalizer.readCount(user.getId()));
            System.out.println("\t\toperations done :");
            for(Log l : userLog){
                if(l.getClassName().contains("ProductRepository")){
                    JSONObject j = l.getMessage();
                    String methodName = (String)j.get("method");
                    System.out.println("\t\t\t" + methodName);
                }
            }
        }
        ////////////////////////////////////

        /*
         * a profile for those that mostly performed write operations on your repository.
         */
        System.out.println(SEPARATOR);
        System.out.println("Users which mostly write : ");
        for(String u : readWriteAnalizer.mostWrite()){
            List<Log> userLog = readWriteAnalizer.getLogsForUser(u);
            User user = readWriteAnalizer.getUser(u);
            System.out.println("\tid:" + user.getId() + " name:" + user.getName() + " number_of_write :" + readWriteAnalizer.writeCount(user.getId()));
            System.out.println("\t\toperations done :");
            for(Log l : userLog){
                if(l.getClassName().contains("ProductRepository")){
                    JSONObject j = l.getMessage();
                    String methodName = (String)j.get("method");
                    System.out.println("\t\t\t" + methodName);
                }
            }
        }
        ///////////////////////////////

        /*
         * a profile for those that searched for the most expensive products in your repository
         */
        System.out.println(SEPARATOR);
        System.out.println("Users which searched for the most expensive product : ");
        for(String u : readWriteAnalizer.mostExpensive()){
            List<Log> userLog = readWriteAnalizer.getLogsForUser(u);
            User user = readWriteAnalizer.getUser(u);
            System.out.println("\tid:" + user.getId() + " name:" + user.getName() + " price:" + readWriteAnalizer.getMostExpensiveForUser(user.getId()));
            System.out.println("\t\toperations done :");
            for(Log l : userLog){
                if(l.getClassName().contains("ProductRepository")){
                    JSONObject j = l.getMessage();
                    String methodName = (String)j.get("method");
                    System.out.println("\t\t\t" + methodName);
                }
            }
        }
        System.out.println(SEPARATOR);
        ////////////////////////////////////:




        System.out.println("Most common errors thrown:");
        Map<String, Integer> errorsOccurence = commonErrorsAnalizer.getErrorsOccurencesThrown();
        for(String s : commonErrorsAnalizer.orderedExceptionThrown()){
            System.out.println("\t" + s + " size:" + errorsOccurence.get(s));
            System.out.println("\t\tfrom:");
            for(String ss : commonErrorsAnalizer.errorDatasStringThrown(s)){
                System.out.println("\t\t\t" + ss);
            }

        }

        System.out.println(SEPARATOR);
        System.out.println("Most common errors caught:");
        errorsOccurence = commonErrorsAnalizer.getErrorsOccurencesCaught();
        for(String s : commonErrorsAnalizer.orderedExceptionCaught()){
            System.out.println("\t" + s + " size:" + errorsOccurence.get(s));
            System.out.println("\t\tfrom:");
            for(String ss : commonErrorsAnalizer.errorDatasStringCaught(s)){
                System.out.println("\t\t\t" + ss);
            }

        }

    }
}
