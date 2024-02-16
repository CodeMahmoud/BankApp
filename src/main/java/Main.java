import Model.User;
import Service.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    public static User sessionUser = null;
    private static List<User> userList = new ArrayList<>();
    public static UserService userService = new UserService(userList);
    public static List<String> registeredUsers = new ArrayList<>();
    public static void main (String[] args){
        String url = "jdbc:mysql://ba-database.crm486eaux5t.us-east-1.rds.amazonaws.com:3306/banktable";
        String username= "admin";
        String password = "mahmoud1122!";

//        try (
//                Connection conn = DriverManager.getConnection(url, username, password);
//             Statement statement = conn.createStatement()
//        ) {
////            System.out.println("Connected to the database successfully");
//            String userSql = "CREATE TABLE user(user_id INT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(255), password VARCHAR(255))";
//            System.out.println("table user created");
//            String accountSql = "CREATE TABLE account(account_id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, balance DECIMAL (10, 2), FOREIGN KEY (user_id) REFERENCES user(user_id)";
//            String sql2 = "CREATE TABLE account (account_id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, balance DECIMAL(10, 2), FOREIGN KEY (user_id) REFERENCES user(user_id))";
////            statement.execute(userSql);
//            statement.execute(accountSql);
//        } catch (
//                SQLException e) {
//            System.out.println("Error connecting to the database");
//            e.printStackTrace();
//        }
        System.out.print("Welcome to Maks Bank!\n" + "\n" +
                "Please select one of the following options:\n" + "\n" +
                "To register Select 1\n" +
                "To login Select 2\n" +
                "To exit Select 3\n");
        Scanner scanner = new Scanner(System.in);
         int input = scanner.nextInt();
        scanner.nextLine();

        switch (input) {
            case 1:
                register(scanner);
                System.out.println("Registration completed successfully.");

                break;
            case 2:
                login(scanner);
                break;

            case 3:
                System.exit(0);
                break;
        }

    }

    private static void register(Scanner scanner) {
        System.out.println("Please enter email");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email address. Please enter a valid email.");
            email = scanner.nextLine();
        }
        System.out.println("Please enter password");
        String password = scanner.nextLine();
        while (password.length() < 6) {
            System.out.println("Password must be at least 6 characters long. Please try again.");
            password = scanner.nextLine();
        }
        System.out.println("Please enter name");
        String customerName = scanner.nextLine();
        while (customerName.length() < 3) {
            System.out.println("Name must be at least 3 characters long and no numbers or special characters. Please try again.");
            customerName = scanner.nextLine();
        }

        userService.registerUser(email, password, customerName);
        registeredUsers.add(email);
        registeredUsers.add(password);
    }
    private static boolean isValidEmail(String email) {
        // Regular expression for email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    private static void login(Scanner scanner) {
        System.out.println("Please enter email");
        String email = scanner.nextLine();
        while (!isValidEmail(email)) {
            System.out.println("Invalid email address. Please enter a valid email.");
            email = scanner.nextLine();
        }
        if (registeredUsers.contains(email)) {
            System.out.println("Please enter password");
             String password = scanner.nextLine();
            if (registeredUsers.contains(password)) {
                System.out.println("Logged in Successfully");
            }


        } else {
            System.out.println("Email or password is incorrect");
        }


    }

}
