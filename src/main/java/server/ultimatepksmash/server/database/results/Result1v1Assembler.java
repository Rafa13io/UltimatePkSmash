package server.ultimatepksmash.server.database.results;

import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.database.user.UserService;

import java.sql.SQLException;

public class Result1v1Assembler {
    public static Result1v1Read toRead(Result1vs1 results1v1) throws SQLException {
        UserService userService = new UserService();
        User user1 = userService.getUser(results1v1.getWinnerId());
        User user2 = userService.getUser(results1v1.getLoserId());
        return new Result1v1Read(results1v1.getId(), results1v1.getDate(),user1.getUsername(),user2.getUsername());
    }
}
