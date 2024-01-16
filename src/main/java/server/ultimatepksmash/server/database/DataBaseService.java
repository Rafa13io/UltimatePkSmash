package server.ultimatepksmash.server.database;

import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@AllArgsConstructor
public class DataBaseService {
    
    public static Connection connection;
    private final UserService userService;

    public static void connectToDatabase() throws SQLException {
        String dbName = "ultimatepksmashers";
        String url = "jdbc:postgresql://localhost:5432/postgres";
        connection = DriverManager.getConnection(url, "postgres", "postgres");
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

    // testing
    public static void main(String[] args) throws SQLException {
        connectToDatabase();
        SmasherService smasherService = new SmasherService();
        for (var smasher : smasherService.getUserSmashers(1L)) {
            System.out.println(smasher);
        }

        closeConnection();
    }
}
