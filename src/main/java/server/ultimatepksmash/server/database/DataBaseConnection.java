package server.ultimatepksmash.server.database;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@AllArgsConstructor
public class DataBaseConnection {
    
    public static Connection connection;
    
    public static void connectToDatabase() throws SQLException {
        
        String url = "jdbc:postgresql://localhost:5432/ultimatepksmashers";
        connection = DriverManager.getConnection(url, "postgres", "postgres");
        try {
            PreparedStatement statement = connection.prepareStatement("CREATE DATABASE ultimatepksmashers");
            statement.executeUpdate();
            System.out.println("created database schema: " + connection.getSchema());
        }
        catch (Exception e) {
            connection = DriverManager.getConnection(url, "postgres", "postgres");
            System.out.println("connected to database schema: " + connection.getSchema());
        }
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("connection closed");
    }
}
