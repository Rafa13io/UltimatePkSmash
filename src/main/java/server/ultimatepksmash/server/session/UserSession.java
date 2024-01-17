package server.ultimatepksmash.server.session;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.samsher.Smasher;
import server.ultimatepksmash.server.database.samsher.SmasherService;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.gamesmanager.GameSession;
import server.ultimatepksmash.server.gamesmanager.GamesManager;
import server.ultimatepksmash.server.messages.BattleStart1v1Req;
import server.ultimatepksmash.server.messages.LogOutReq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.Callable;
@AllArgsConstructor
public class UserSession implements Callable<SessionEndStatus> {
    private final Socket socket;
    private final User user;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public UserSession(Socket socket, User user, ObjectOutputStream output, ObjectInputStream input) throws IOException {
        this.socket = socket;
        this.user = user;
        this.output = output;
        this.input = input;;

    }

    @Override
    public SessionEndStatus call() throws Exception {
        while(true)
        {
            Object req =  (Object) input.readObject();
            if(req instanceof BattleStart1v1Req)
            {
                battle1v1Chosen((BattleStart1v1Req) req);
            }else if(req instanceof LogOutReq)
            {
                System.out.println("Loged out user:" + user.getUsername());
                socket.close();
                return new SessionEndStatus(SessionEndReason.loggedOut);
            }
            else
            {
                throw new  UnsupportedOperationException("Not implemented yet");
            }

            System.out.println("Unexpected message received");
            //throw new  UnsupportedOperationException("Not implemented yet");
        }
    }

    private void battle1v1Chosen(BattleStart1v1Req battleStart1v1Req) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("User wants"+ user.getUsername() +" to play 1v1 batlle");
        //BattleStart1v1Req battleStart1V1Req = (BattleStart1v1Req) input.readObject();
        SmasherService smasherService = new SmasherService();
        Smasher mySmasher = smasherService.getSmasher(battleStart1v1Req.getUsersSmasherId());
        System.out.println("Try to join game session");
        GameSession gameSession = GamesManager.joinGameSession1v1(user, mySmasher);
        System.out.println("User " + user.getUsername() +" have joined game session");
        output.writeObject(gameSession.getBattleStartResponse());
    }
    private void logReq(Object req)
    {
        System.out.println("[REQ]: "+req);
    }
}
