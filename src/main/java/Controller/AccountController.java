package Controller;

import Service.AccountService;
import Model.Account;
import Utili.ConnectionFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.javalin.Javalin;

import java.sql.Connection;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountController {
    private AccountService accountService;
    Javalin app;

    public AccountController(Javalin app) {

        Connection conn = ConnectionFactory.getConnectionFactory().getConnection();
        this.accountService = new AccountService(conn);
    }

    public void accountEndpoint(Javalin app) {
        app.post("/account", this::createAccount);
        app.get("/account/{userId}/balance", this::getBalance);
        app.put("/account/{userId}/deposit", this::depositFunds);
        app.put("/account/{userId}/withdraw", this::withdrawFunds);
    }
    public void createAccount(Context context) {
        int userId = Integer.parseInt(context.formParam("userId"));
        double initialBalance = Double.parseDouble(context.formParam("initialBalance"));

        Account account = new Account();
        account.setUserId(userId);
        account.setBalance(initialBalance);

        Account createdAccount = accountService.createAccount(account);

        if (createdAccount != null) {
            context.status(201).json(createdAccount);
        } else {
            context.status(500).result("Failed to create account.");
        }

    }

    private void withdrawFunds(Context context) throws JsonProcessingException {
        int userId = Integer.parseInt(context.pathParam("userId"));
        ObjectMapper mapper = new ObjectMapper();
        JsonNode body = mapper.readTree(context.body());
        double amount = body.get("amount").asDouble();

        double currentBalance = accountService.getBalance(userId);
        if (currentBalance < amount) {
            context.status(400).result("Insufficient funds.");
            return;
        }
        accountService.withdraw(userId, amount);

        context.status(200).result("Withdrawal successful.");
    }



    private void depositFunds(Context context) {
        try {
            int userId = Integer.parseInt(context.pathParam("userId"));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode body = mapper.readTree(context.body());
            double amount = body.get("amount").asDouble();

            boolean success = accountService.deposit(userId, amount);

            if (success) {
                context.status(200).result("Deposit successful.");
            } else {
                context.status(400).result("Deposit failed. Invalid user ID.");
            }
        } catch (NumberFormatException | JsonProcessingException e) {
            context.status(400).result("Invalid user ID or amount.");
        }
    }



    private void getBalance(Context context) {
        try {
            int userId = Integer.parseInt(context.pathParam("userId"));

            double balance = accountService.getBalance(userId);

            context.status(200).json(balance);
        } catch (NumberFormatException e) {
            context.status(400).result("Invalid user ID.");
        }
    }


}
