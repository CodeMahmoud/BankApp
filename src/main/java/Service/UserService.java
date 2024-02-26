package Service;

//import DAO.AccountDAO;
import DAO.UserDAO;
import Model.User;
import Utili.DTO.LoginCreds;

import java.util.Objects;

//import java.util.List;

//public class UserService {
//    private UserDAO userDAO;
//    public UserService(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }
//    public User registerUser(User registerUser) {
//        //User newUser = new User(email, password, customerName);
//        return userDAO.createUser(registerUser);
//
//    }
//
//}

//import DAO.UserDAO;
public class UserService {
    private final UserDAO userDAO;
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(User registerUser) {
        if (!isValidPassword(registerUser.getPassword())) {
            System.out.println("Password must be at least 5 characters long and contain at least one number.");
            return null;
        }

        if (!isValidEmail(registerUser.getEmail())) {
            System.out.println("Invalid email format.");
            return null;
        }

        try {
            User createdUser = userDAO.createUser(registerUser);
            System.out.println("User registered successfully for email: " + registerUser.getEmail());
            return createdUser;
        } catch (Exception e) {
            System.out.println("User registration failed for email: " + registerUser.getEmail());
            e.printStackTrace();
            return null;
        }
    }

    public User loginUser(LoginCreds loginCreds) {
      User user = userDAO.findUserByEmail(loginCreds.getEmail());
      if (Objects.equals(loginCreds.getPassword(),user.getPassword())) {
          return user;
      } else {
          return null;
      }
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5 && password.matches(".*\\d.*");
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
}


//public class UserService {
//    private UserDAO userDAO;
//
//    public UserService(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }
//    public User newUser = new User();
//    public boolean registerUser(User registerUser) {
//        if (!isValidPassword(registerUser.getPassword())) {
//            System.out.println("Password must be at least 5 characters long and contain at least one number.");
//            return false;
//        }
//
//        if (!isValidEmail(registerUser.getEmail())) {
//            System.out.println("Invalid email format.");
//            return false;
//        }
//
//        boolean registrationSuccess = userDAO.createUser(newUser);
//        if (registrationSuccess) {
//            System.out.println("User registered successfully for email: " + registerUser.getEmail());
//            return true;
//        } else {
//            System.out.println("User registration failed for email: " + registerUser.getEmail());
//            return false;
//        }
//    }
//
//    public boolean loginUser(String email, String password) {
//        boolean loginSuccess = userDAO.loginUser(email, password);
//        if (loginSuccess) {
//            System.out.println("Login successful for email: " + email);
//            return true;
//        } else {
//            System.out.println("Login failed for email: " + email);
//            return false;
//        }
//    }
//
//    private boolean isValidPassword(String password) {
//        return password.length() >= 5 && password.matches(".*\\d.*");
//    }
//
//    private boolean isValidEmail(String email) {
//        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
//    }
//}