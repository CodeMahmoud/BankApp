package Service;

import Model.User;

import java.util.List;

public class UserService {
    private List<User> userList;
    public UserService(List<User> userList) {
        this.userList = userList;
    }
    public void registerUser(String email, String password, String customerName) {
        User newUser = new User(email, password, customerName);
        userList.add(newUser);
    }

}
