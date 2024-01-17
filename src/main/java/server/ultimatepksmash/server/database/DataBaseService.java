package server.ultimatepksmash.server.database;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.skills.defence.DefenceService;
import server.ultimatepksmash.server.database.smasher.SmasherService;
import server.ultimatepksmash.server.database.skills.attack.AttackService;
import server.ultimatepksmash.server.database.user.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


@AllArgsConstructor
public class DataBaseService {
    
    public static Connection connection;
    
    static {
        // auto Close the database connection when program ends
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                closeConnection();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static void connectToDatabase() throws SQLException {
        String dbName = "ultimatepksmashers";
        String url = "jdbc:postgresql://localhost:5432/";

        try {
            connection = DriverManager.getConnection(url, "postgres", "postgres");
            PreparedStatement statement = connection.prepareStatement("CREATE DATABASE " + dbName);
            statement.executeUpdate();
            System.out.println("created database " + dbName + " schema: " + connection.getSchema());
        }
        catch (Exception e) {
            // if already exists: make connection
            url = "jdbc:postgresql://localhost:5432/" + dbName;
            connection = DriverManager.getConnection(url, "postgres", "postgres");
            System.out.println("connected to database " + dbName + " schema: " + connection.getSchema());
        }
        
    }

    public static void closeConnection() throws SQLException {
        connection.close();
        System.out.println("connection closed");
    }
    
    // ----------------- testing -----------------
    private static <T> void printList(List<T> list) {
        for (var item : list) {
            System.out.println(item);
        }
        System.out.println();
    }
    
    public static void main(String[] args) throws SQLException {
        connectToDatabase();
        
        SmasherService smasherService = new SmasherService();
        UserService userService = new UserService();
        AttackService attackService = new AttackService();
        DefenceService defenceService = new DefenceService();
        
        printList(userService.getUsers());
        printList(smasherService.getSmashers());
        printList(attackService.getSmasherAttacks(1L));
        printList(attackService.getSmasherAttacks(2L));
        printList(attackService.getSmasherAttacks(3L));
        printList(attackService.getSmasherAttacks(4L));
        printList(attackService.getSmasherAttacks(5L));
        printList(attackService.getSmasherAttacks(6L));
        printList(attackService.getSmasherAttacks(7L));
        
        printList(defenceService.getSmasherDefences(1L));
        printList(defenceService.getSmasherDefences(2L));
        printList(defenceService.getSmasherDefences(3L));
        printList(defenceService.getSmasherDefences(4L));
        printList(defenceService.getSmasherDefences(5L));
        printList(defenceService.getSmasherDefences(6L));
        printList(defenceService.getSmasherDefences(7L));
    
//        userService.addUser(new User("user21222","email@pl.com","123"));
    }
}
