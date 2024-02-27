import Controller.AccountController;
import Controller.UserController;
import DAO.AccountDAO;
import DAO.UserDAO;
import Model.Account;
import Model.User;
import Service.AccountService;
import Service.UserService;
import io.javalin.Javalin;
import DAO.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                    it.exposeHeader("Authorization");
                });
            });
        }).start(8080);

        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserController userController = new UserController(app, userService);
        userController.userEndpoint(app);
        AccountDAO accountDAO = new AccountDAO();
        AccountService accountService = new AccountService(accountDAO);
        AccountController accountController = new AccountController(app);
        accountController.accountEndpoint(app);
    }
}