package DAO;

import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    public Account createAccount(Account account, Connection conn) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, balance) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, account.getUserId());
            pstmt.setDouble(2, account.getBalance());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int accountId = generatedKeys.getInt(1);
                    account.setAccountId(accountId);
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
            return account;
        }
    }
}
