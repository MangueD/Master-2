package applicationSpooned.users;
import applicationSpooned.main.Main;
import applicationSpooned.users.exceptions.UserAlreadyExistsException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
public class UserRepository {
    /* Generated by Spoon */
    private Main mm;

    /* Generated by Spoon */
    private LogManager lm;

    /* Generated by Spoon */
    private static Logger logger = LogManager.getLogger(UserRepository.class.getName());

    private Map<String, User> users;

    private String path;

    private JSONArray jsonArray;

    /**
     * Création d'un repertoire d'utilisateur à partir d'un fichier json
     */
    public UserRepository(String path) throws IOException {
        this.users = new Hashtable<>();
        this.path = path;
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(path);
            Object o = jsonParser.parse(reader);
            jsonArray = ((JSONArray) (o));
            for (Object object : jsonArray) {
                JSONObject productObject = ((JSONObject) (object));
                String name = ((String) (productObject.get("name")));
                String id = ((String) (productObject.get("id")));
                int age = Integer.parseInt(((String) (productObject.get("age"))));
                String email = ((String) (productObject.get("email")));
                String password = ((String) (productObject.get("password")));
                users.put(id, new User(id, name, age, email, password));
            }
        } catch (FileNotFoundException e) {
            /* Generated by Spoon */
            logger.debug("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"where\" : \"catch\"" + ", " + "\"exception\" : \"FileNotFoundException\"" + "}");
            File file = new File(path);
            file.createNewFile();
            jsonArray = new JSONArray();
        } catch (IOException e) {
            /* Generated by Spoon */
            logger.debug("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"where\" : \"catch\"" + ", " + "\"exception\" : \"IOException\"" + "}");
            /* Generated by Spoon */
            logger.debug("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"where\" : \"throw\"" + ", " + "\"exception\" : \"RuntimeException\"" + "}");
            throw new RuntimeException(e);
        } catch (ParseException e) {
            /* Generated by Spoon */
            logger.debug("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"where\" : \"catch\"" + ", " + "\"exception\" : \"ParseException\"" + "}");
            File file = new File(path);
            file.createNewFile();
            jsonArray = new JSONArray();
        }
    }

    /**
     * Ajout d'un utilisateur dans le repertoire et aussi le mettre dans le fichier json
     */
    public User createUser(String id, String name, int age, String email, String password) throws UserAlreadyExistsException {
        /* Generated by Spoon */
        logger.info("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"method\" : \"" + "createUser" + "\"" + ", " + "\"parameters\" : " + "[" + "\"" + id + "\"" + ", " + "\"" + name + "\"" + ", " + "\"" + age + "\"" + ", " + "\"" + email + "\"" + ", " + "\"" + password + "\"" + "]" + "}");
        if (users.containsKey(id)) {
            /* Generated by Spoon */
            logger.debug("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"where\" : \"throw\"" + ", " + "\"exception\" : \"UserAlreadyExistsException\"" + "}");
            throw new UserAlreadyExistsException();
        }
        User nUser = new User(id, name, age, email, password);
        users.put(id, nUser);
        JSONObject nProduct = new JSONObject();
        nProduct.put("id", id);
        nProduct.put("name", name);
        nProduct.put("age", Integer.toString(age));
        nProduct.put("email", email);
        nProduct.put("password", password);
        jsonArray.add(nProduct);
        try {
            FileWriter file = new FileWriter(path);
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            /* Generated by Spoon */
            logger.debug("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"where\" : \"catch\"" + ", " + "\"exception\" : \"IOException\"" + "}");
            e.printStackTrace();
        }
        return nUser;// retourne l'utilisateur afin de se connecter directement

    }

    public boolean logUser(String id, String password) {
        /* Generated by Spoon */
        logger.info("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"method\" : \"" + "logUser" + "\"" + ", " + "\"parameters\" : " + "[" + "\"" + id + "\"" + ", " + "\"" + password + "\"" + "]" + "}");
        if (!users.containsKey(id)) {
            return false;
        }
        return users.get(id).checkPassword(password);
    }

    public User getUser(String id) {
        /* Generated by Spoon */
        logger.info("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"method\" : \"" + "getUser" + "\"" + ", " + "\"parameters\" : " + "[" + "\"" + id + "\"" + "]" + "}");
        return users.get(id);
    }
}