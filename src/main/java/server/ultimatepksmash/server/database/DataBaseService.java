package server.ultimatepksmash.server.database;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.user.UserService;

import java.sql.*;

@AllArgsConstructor
public class DataBaseService {
    
    public static Connection connection;
    private final UserService userService;
    
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
    
    public static void main(String[] args) throws SQLException {
        connectToDatabase();
        
        System.out.println(new UserService().getUser("nigger","dupa@gmail.com"));
        closeConnection();
    }
    
    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
