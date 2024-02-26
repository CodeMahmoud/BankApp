package Utili;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();

    private static Properties properties = new Properties();
    private ConnectionFactory(){
//        try{
//            properties.load(new FileReader("src/main/resources/db.properties"));
//        } catch (IOException e){
//            e.printStackTrace();
//        }
    }

    public static ConnectionFactory getConnectionFactory(){
        return connectionFactory;
    }


    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://ba-database.crm486eaux5t.us-east-1.rds.amazonaws.com:3306/BankApp";
            String user = "admin";
            String pass = "mahmoud1122!";
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}