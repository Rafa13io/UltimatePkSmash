package server.ultimatepksmash.server.database.user;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.DataBaseService;
import server.ultimatepksmash.server.database.smasher.SmasherService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
public class UserService {
    private static final Connection connection = DataBaseService.connection;
    
    public List<User> getUsers() throws SQLException {
        PreparedStatement getUsers = connection.prepareStatement("SELECT * FROM p_user");
        ResultSet resultSet = getUsers.executeQuery();
        
        List<User> users = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            User user = new User();
            mapUser(user, resultSet);
            users.add(user);
        }

        resultSet.close();
        getUsers.close();
        return users;
    }

    public User getUser(Long id) throws SQLException {
        String sql = "SELECT * FROM p_user WHERE id = ?";
        PreparedStatement getUser = connection.prepareStatement(sql);
        getUser.setLong(1, id);

        ResultSet resultSet = getUser.executeQuery();

        resultSet.next(); // move to the returned row
        User user = new User();
        mapUser(user, resultSet);

        if (resultSet.next()) {
            throw new RuntimeException("Query didn't return unique row, method: getUser() in: " + this.getClass());
        }

        resultSet.close();
        getUser.close();
        return user;
    }

    public User findUserNyName(String username) throws SQLException {
        String sql = "SELECT * FROM p_user WHERE username = ? ";
        PreparedStatement getUser = connection.prepareStatement(sql);
        getUser.setString(1, username);

        ResultSet resultSet = getUser.executeQuery();

        User user = new User();
        try {
            resultSet.next(); // move to the returned row
            mapUser(user, resultSet);
        }
        catch (SQLException e) {
            throw new SQLException("error getiign a user (query probably returned no rows)");
        }


        if (resultSet.next()) {
            throw new RuntimeException("Query didn't return unique row, method: getUser() in: " + this.getClass());
        }

        resultSet.close();
        getUser.close();
        return user;
    }

    public User getUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM p_user WHERE username = ? AND password = ?";
        PreparedStatement getUser = connection.prepareStatement(sql);
        getUser.setString(1, username);
        getUser.setString(2, password);
        
        ResultSet resultSet = getUser.executeQuery();
        
        User user = new User();
        try {
            resultSet.next(); // move to the returned row
            mapUser(user, resultSet);
        }
        catch (SQLException e) {
            throw new SQLException("error getiign a user (query probably returned no rows)");
        }
        

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
        List<String> usernames;
        usernames = getUsers().stream().map(User::getUsername).collect(Collectors.toList());
        if (usernames.contains(user.getUsername())) {
            throw new RuntimeException("username not available");
        }
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getEmail().isEmpty()) {
            throw new RuntimeException("empty field in form");
        }

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
        System.out.println("added user: " + user);
        addUser.close();
        User fUser = findUserNyName(user.getUsername());
        addSmasherToUser(fUser.getId(), 1L);
    }

    public void addSmasherToUser(Long userId, Long smasherId) throws SQLException {
        String sql = "insert into p_smasher_user(user_id, smasher_id) values (?,?);";
        PreparedStatement addSmasherToUser = connection.prepareStatement(sql);
        addSmasherToUser.setLong(1, userId);
        addSmasherToUser.setLong(2, smasherId);
        try {
            addSmasherToUser.executeUpdate();
        }
        catch (Exception e)
        {
            int a;
            System.out.println("");
        }
        addSmasherToUser.close();
    }
    
    public void addWinToUser(Long userId) throws SQLException {
        String sql = """
                UPDATE p_user
                SET
                    number_of_played_games = number_of_played_games + 1,
                    number_of_wins = number_of_wins + 1
                WHERE
                    id = ?;
                """;
        PreparedStatement addWinToUser = connection.prepareStatement(sql);
        addWinToUser.setLong(1, userId);
        addWinToUser.executeUpdate();
        addWinToUser.close();
    }
    
    public void addLoseToUser(Long userId) throws SQLException {
        String sql = """
                UPDATE p_user
                SET
                    number_of_played_games = number_of_played_games + 1
                WHERE
                    id = ?;
                """;
        PreparedStatement addLoseToUser = connection.prepareStatement(sql);
        addLoseToUser.setLong(1, userId);
        addLoseToUser.executeUpdate();
        addLoseToUser.close();
    }
    
    private static void mapUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setNumOfPlayedGames(resultSet.getInt("number_of_played_games"));
        user.setNumOfWins(resultSet.getInt("number_of_wins"));
        SmasherService smasherService= new SmasherService();
        user.setSmashers(smasherService.getUserSmashers(user.getId()));
    }
}
