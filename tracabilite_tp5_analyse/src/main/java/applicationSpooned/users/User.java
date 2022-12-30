package applicationSpooned.users;
import applicationSpooned.main.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class User {
    /* Generated by Spoon */
    private Main mm;

    /* Generated by Spoon */
    private LogManager lm;

    /* Generated by Spoon */
    private static Logger logger = LogManager.getLogger(User.class.getName());

    private String id;

    private String name;

    private int age;

    private String email;

    private String password;

    public User(String id, String name, int age, String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public final int getAge() {
        return age;
    }

    public final String getEmail() {
        return email;
    }

    public boolean checkPassword(String password) {
        /* Generated by Spoon */
        logger.info("{" + "\"user\" : \"" + (Main.getUserContext() == null ? null : Main.getUserContext().getId()) + "\"" + ", " + "\"method\" : \"" + "checkPassword" + "\"" + ", " + "\"parameters\" : " + "[" + "\"" + password + "\"" + "]" + "}");
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User u = ((User) (o));
        if (u.getName().equals(this.name)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ((((((("[user:" + id) + ", name:") + name) + ", age:") + Integer.toString(age)) + ", email:") + email) + "]";
    }
}