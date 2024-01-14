package server.ultimatepksmash;

import java.sql.*;

public class Server {
    public static void main(String[] args) throws SQLException {
//TODO: EXAMPLE DataBase communication, delete this
//                String url = "jdbc:postgresql://localhost:5432/project?currentSchema=public";
        String url = "jdbc:postgresql://localhost/project";

        Connection connection = DriverManager.getConnection(url, "postgres", "postgres");

        //            Statement statement = connection.createStatement();
        //            ResultSet resultSet = statement.executeQuery("SELECT * FROM p_user");

        PreparedStatement getUsers = connection.prepareStatement("SELECT * FROM p_user");

        ResultSet resultSet = getUsers.executeQuery();

        // Iterate over the result set and print the values
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            String email = resultSet.getString("email");

            System.out.printf("%-5d %-15s %-15s %-20s%n", id, first_name, last_name, email);
        }

        resultSet.close();
        //            statement.close();
        connection.close();
    }
}