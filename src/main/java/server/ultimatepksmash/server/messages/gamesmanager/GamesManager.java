package server.ultimatepksmash.server.messages.gamesmanager;

import server.ultimatepksmash.server.database.user.User;

import java.util.ArrayList;
import java.util.List;

public class GamesManager {
    List<User> awaitingUsersOn1v1 = new ArrayList<>();
    List<User> awaitingUsersOn2v2 = new ArrayList<>();
    List<User> awaitingUsersOn3v3 = new ArrayList<>();

    public void awaitForSession1v1(User user)
    {

    }
}
