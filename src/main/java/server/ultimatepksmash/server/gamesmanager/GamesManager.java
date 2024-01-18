package server.ultimatepksmash.server.gamesmanager;

import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;

public class GamesManager {

    private static GameSession awaitingGameUsersOn1v1 = new GameSession(2);
    private static GameSession awaitingGameUsersOn2v2 = new GameSession(4);

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

    static public GameSession joinGameSession2v2(User user, Smasher smasher)
    {
        GameSession gameSession = null;
        synchronized (awaitingGameUsersOn2v2)
        {
            gameSession = awaitingGameUsersOn2v2;
            if(awaitingGameUsersOn2v2.getNumberOfPlayersReady() > 3)
            {
                awaitingGameUsersOn2v2 = new GameSession(4);
            }
        }
        gameSession.addPlayerAndWaitForOthersToJoin(user, smasher);
        return gameSession;
    }
}
