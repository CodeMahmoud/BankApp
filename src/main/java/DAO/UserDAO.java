package DAO;

import Model.User;
import Utili.ConnectionFactory;
import java.sql.*;

public class UserDAO {

    public User createUser(User registerUser) {

        try (Connection conn = ConnectionFactory.getConnectionFactory().getConnection()) {


            String sqlUser = "INSERT INTO user (email, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatementUser = conn.prepareStatement(
                    sqlUser, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatementUser.setString(1, registerUser.getEmail());
                preparedStatementUser.setString(2, registerUser.getPassword());
                int affectedRows = preparedStatementUser.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }
                long userId = -1;
                try (ResultSet generatedKeys = preparedStatementUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getLong(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                String sqlAccount = "INSERT INTO account (user_id, balance) VALUES (?, ?)";
                try (PreparedStatement preparedStatementAccount = conn.prepareStatement(sqlAccount)) {
                    preparedStatementAccount.setLong(1, userId);
                    preparedStatementAccount.setDouble(2, 0.0);
                    preparedStatementAccount.executeUpdate();
                }
            }
            conn.commit();
            return registerUser;
        } catch (SQLException ex) {
            System.out.println("Error registering user: " + ex.getMessage());

            return registerUser;
        }
    }


    public User findUserByEmail(String email) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM user WHERE email = ?";
            try (PreparedStatement preState = conn.prepareStatement(sql)) {
                preState.setString(1, email);
                try (ResultSet rs = preState.executeQuery()) {
                    if (rs.next()) {
                        return convertSqlIntoUser(rs);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error logging in user: " + ex.getMessage());
            return null;
        }
    }

    private User convertSqlIntoUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword((rs.getString("password")));
        return user;
    }
}
