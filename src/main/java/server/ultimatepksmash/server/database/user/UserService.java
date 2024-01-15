package server.ultimatepksmash.server.database.user;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.DataBaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class UserService {
    private final Connection connection = DataBaseService.connection;
    
    public List<User> getUsers() throws SQLException {
        PreparedStatement getUsers = connection.prepareStatement("SELECT * FROM p_user");
        ResultSet resultSet = getUsers.executeQuery();
        
        List<User> users = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setNumOfPlayedGames(resultSet.getInt("number_of_played_games"));
            user.setNumOfWins(resultSet.getInt("number_of_wins"));
            
//            System.out.printf("%-5d %-10s %-10s %-10s %-10d %-5d %n",
//                    user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
//                    user.getNumOfPlayedGames(), user.getNumOfWins());
            
            users.add(user);
        }

        resultSet.close();
        getUsers.close();
        return users;
    }
    
    public User getUser(String username, String email) throws SQLException {
        PreparedStatement getUsers = connection.prepareStatement("SELECT * FROM p_user WHERE username = ? AND email = ?");
        getUsers.setString(1, username);
        getUsers.setString(2, email);
        
        ResultSet resultSet = getUsers.executeQuery();
        
        resultSet.next(); // move to the returned row
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setNumOfPlayedGames(resultSet.getInt("number_of_played_games"));
        user.setNumOfWins(resultSet.getInt("number_of_wins"));
        
        if (resultSet.next()) {
            throw new RuntimeException("Query didn't return unique row, method: getUser() in: " + this.getClass());
        }
        
        resultSet.close();
        getUsers.close();
        return user;
    }
    
}
