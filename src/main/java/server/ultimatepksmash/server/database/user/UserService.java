package server.ultimatepksmash.server.database.user;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class UserService {
    private final Connection connection = DataBaseConnection.connection;
    
    public List<User> getUsers() throws SQLException {
        PreparedStatement getUsers = connection.prepareStatement("SELECT * FROM p_user");
        ResultSet resultSet = getUsers.executeQuery();
        
        List<User> users = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setNumOfPlayedGames(resultSet.getInt("number_of_played_games"));
            user.setNumOfWins(resultSet.getInt("number_of_wins"));
            
            users.add(user);
        }

        resultSet.close();
        getUsers.close();
        return users;
    }
    
    public User getUser(String username, String email) throws SQLException {
        PreparedStatement getUser = connection.prepareStatement("SELECT * FROM p_user WHERE username = ? AND email = ?");
        getUser.setString(1, username);
        getUser.setString(2, email);
        
        ResultSet resultSet = getUser.executeQuery();
        
        resultSet.next(); // move to the returned row
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setNumOfPlayedGames(resultSet.getInt("number_of_played_games"));
        user.setNumOfWins(resultSet.getInt("number_of_wins"));
        
        if (resultSet.next()) {
            throw new RuntimeException("Query didn't return unique row, method: getUser() in: " + this.getClass());
        }
        
        resultSet.close();
        getUser.close();
        return user;
    }
    
    /**
     * <B>important</B> - you can provide a user with any id but the id will be assigned automatically by database.
     * @param user
     * @throws SQLException
     */
    public void addUser(User user) throws SQLException {
        PreparedStatement addUser = connection.prepareStatement(
                "INSERT INTO p_user (username, email, password, number_of_played_games, number_of_wins) " +
                    "VALUES (?, ?, ?, ?, ?)"
        );

        addUser.setString(1, user.getUsername());
        addUser.setString(2, user.getEmail());
        addUser.setString(3, user.getPassword());
        addUser.setInt(4, user.getNumOfPlayedGames());
        addUser.setInt(5, user.getNumOfWins());
        
        addUser.executeUpdate();
        System.out.printf("add user: %s \n", user);
    }
    
    //todo: check for email uniqueness
    
}
