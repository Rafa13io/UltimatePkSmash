package server.ultimatepksmash.server.gamesmanager;

import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;

public class GamesManager {

    private static GameSession awaitingGameUsersOn1v1 = new GameSession(2);
    //List<User> awaitingUsersOn2v2 = new ArrayList<>();
    //List<User> awaitingUsersOn3v3 = new ArrayList<>();

    static public GameSession joinGameSession1v1(User user, Smasher smasher)
    {
        GameSession gameSession = null;
        synchronized (awaitingGameUsersOn1v1)
        {
            gameSession = awaitingGameUsersOn1v1;
            if(awaitingGameUsersOn1v1.getNumberOfPlayersReady() > 0)
            {
                awaitingGameUsersOn1v1 = new GameSession(2);
            }
        }
        gameSession.addPlayerAndWaitForOthersToJoin(user, smasher);
        return gameSession;
    }
}
