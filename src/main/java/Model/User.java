package Model;

public class User {
    private int userId = 1;
    private String email;
    private String password;


    public User(String email, String password, String customerName) {

        this.email = email;
        this.password = password;

    }

    public User() {
        this.userId = userId;
        this.email = email;
        this.password = password;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
