package server.ultimatepksmash.server.database;

import lombok.RequiredArgsConstructor;
import server.ultimatepksmash.server.database.user.UserService;

import java.sql.SQLException;


@RequiredArgsConstructor
public class DatabaseManager {
    
    private static UserService userService;
    // other services ...
    
    private static void initializeServices() {
        userService = new UserService();
        // create other services ...
    }
    
    public static void connect() throws SQLException {
        DataBaseConnection.connectToDatabase();
        initializeServices();
    }
    
    public static void closeConnection() throws SQLException {
        DataBaseConnection.closeConnection();
    }
    
    public static void main(String[] args) throws SQLException {
        connect();
        
        // ---- examples ----
        //add users
        //        userService.addUser(new User(null,"user","user@gmail.com","123",0,0));
        //        userService.addUser(new User("user","user2@gmail.com","123"));
        
        //get users
        System.out.println(userService.getUser("nigger","dupa@gmail.com"));
        
        closeConnection();
    }
}
