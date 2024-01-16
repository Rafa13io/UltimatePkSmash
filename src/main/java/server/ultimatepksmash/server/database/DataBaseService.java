package server.ultimatepksmash.server.database;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.user.UserService;

import java.sql.*;

@AllArgsConstructor
public class DataBaseService {
    public static Connection connection;
    private static UserService userService;
    
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
    
    // this has to be after the successful connection because services need the connection reference
    private static void initServices() {
        userService = new UserService();
        // ...
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
    
    public static void main(String[] args) throws SQLException {
        connectToDatabase();
        initServices();
        
        // ---- examples ----
        //add users
//        userService.addUser(new User(null,"user","user@gmail.com","123",0,0));
//        userService.addUser(new User("user","user2@gmail.com","123"));
        
        //get users
        System.out.println(userService.getUser("nigger","dupa@gmail.com"));
        
        
        closeConnection();
    }
    
}
