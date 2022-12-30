package application.users;

public class User {
    private String id;
    private String name;
    private int age;
    private String email;
    private String password;

    public User(String id, String name, int age, String email, String password){
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public final String getId(){
        return id;
    }

    public final String getName(){
        return name;
    }

    public final int getAge(){
        return age;
    }

    public final String getEmail(){
        return email;
    }

    public boolean checkPassword(String password){
        if(this.password.equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof User)){
            return false;
        }

        User u = (User)o;
        if(u.getName().equals(this.name)){
            return true;
        }

        return false;
    }

    @Override
    public String toString(){
        return "[user:" + id + ", name:" + name + ", age:" + Integer.toString(age) + ", email:" + email + "]";
    }
}
