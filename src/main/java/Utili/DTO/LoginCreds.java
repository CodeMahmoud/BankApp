package Utili.DTO;

public class LoginCreds {
    private String email;
    private String password;

    public LoginCreds(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public LoginCreds() {
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
