package Service;

import DAO.AccountDAO;
import DAO.UserDAO;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {
    private Connection conn;
    private AccountDAO accountDAO;

    public AccountService(Connection conn) {
        this.conn = conn;
        this.accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public double getBalance(int userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("Error getting balance: " + e.getMessage());
        }
        return 0.0;
    }

    public boolean deposit(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");

        }
        String sql = "UPDATE account SET balance = balance + ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, userId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                double newBalance = getBalance(userId);
                System.out.println("Deposit successful. Current balance: " + newBalance);
                return true;
            } else {
                System.out.println("Deposit failed.");

            }
        } catch (SQLException e) {
            System.out.println("Error during deposit: " + e.getMessage());
        }
        return false;
    }

    public void withdraw(int userId, double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        double balance = getBalance(userId);
        if (balance < amount) {
            System.out.println("Insufficient funds.");
            return;
        }
        String sql = "UPDATE account SET balance = balance - ? WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, userId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                double newBalance = getBalance(userId);
                System.out.println("Withdrawal successful. Current balance: " + newBalance);
            } else {
                System.out.println("Withdrawal failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }
    }
    public Account createAccount(Account account) {
        try {
            return accountDAO.createAccount(account, conn);
        } catch (SQLException ex) {
            System.out.println("Error creating account: " + ex.getMessage());
            return null;
        }
    }
}