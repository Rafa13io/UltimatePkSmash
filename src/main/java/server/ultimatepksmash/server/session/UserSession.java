package server.ultimatepksmash.server.session;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.results.Result1vs1;
import server.ultimatepksmash.server.database.results.ResultService;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.smasher.SmasherService;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.database.user.UserService;
import server.ultimatepksmash.server.gamesmanager.GameSession;
import server.ultimatepksmash.server.gamesmanager.GamesManager;
import server.ultimatepksmash.server.messages.BattleStart1v1Req;
import server.ultimatepksmash.server.messages.LogOutReq;
import server.ultimatepksmash.server.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
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
            else if(req instanceof BattleStart2v2Req)
            {
                battle2v2Chosen((BattleStart2v2Req) req);
            }
            else if(req instanceof Get1v1ResultsReq)
            {
                ResultService resultService = new ResultService();
                output.writeObject(new Get1v1ResultsResp(resultService.getResults1vs1()));
            }
            else
            {
                throw new  UnsupportedOperationException("Not implemented yet");
            }
            System.out.println("User went back to the lobby");
            //throw new  UnsupportedOperationException("Not implemented yet");
        }
    }

    private void battle1v1Chosen(BattleStart1v1Req battleStart1v1Req) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("User wants"+ user.getUsername() +" to play 1v1 battle");
        SmasherService smasherService = new SmasherService();
        Smasher mySmasher = smasherService.getSmasher(battleStart1v1Req.getUsersSmasherId());
        System.out.println("Try to join game session");
        GameSession gameSession = GamesManager.joinGameSession1v1(user, mySmasher); // locks thread until second player joins
        System.out.println("User " + user.getUsername() +" have joined game session");
        output.writeObject(gameSession.getBattleStartResponse());

        while(gameSession.checkIfTheGameIsStillOn())
        {
            StartRoundReq req =  (StartRoundReq) input.readObject();
            System.out.println("Server received StartRoundReq from user: "+user.getUsername() );
            StartRoundResp resp = gameSession.executeRequest(user, req); // locks until all start round requests are sent
            output.writeObject(resp);
            System.out.println("Server sent StartRoundResp for user: "+user.getUsername() );
        }
        sendBattleEndResult(gameSession, smasherService);
    }

    private void battle2v2Chosen(BattleStart2v2Req battleStart2v2Req) throws IOException, ClassNotFoundException, SQLException {
        System.out.println("User wants"+ user.getUsername() +" to play 2v2 battle");
        SmasherService smasherService = new SmasherService();
        Smasher mySmasher = smasherService.getSmasher(battleStart2v2Req.getUsersSmasherId());
        System.out.println("Try to join game session");
        GameSession gameSession = GamesManager.joinGameSession2v2(user, mySmasher);
        System.out.println("User " + user.getUsername() +" have joined game session");
        output.writeObject(gameSession.getBattleStartResponse());
        while(!gameSession.isUserPlaying(user) && gameSession.checkIfTheGameIsStillOn())
        {
            System.out.println("gameSession.spyRequests");
            StartRoundResp resp = gameSession.spyRequests();
            output.writeObject(resp);
        }
        while(gameSession.isUserPlaying(user) && gameSession.checkIfTheGameIsStillOn())
        {
            System.out.println(" (StartRoundReq) input.readObject()");
            StartRoundReq req = null;
            req =  (StartRoundReq) input.readObject();
            System.out.println("Server received StartRoundReq from user: "+user.getUsername() );
            StartRoundResp resp = gameSession.executeRequest(user, req);
            output.writeObject(resp);
            System.out.println("Server sent StartRoundResp for user: "+user.getUsername() );
        }
        while(!gameSession.isUserPlaying(user) && gameSession.checkIfTheGameIsStillOn())
        {
            System.out.println("gameSession.spyRequests");
            StartRoundResp resp = gameSession.spyRequests();
            output.writeObject(resp);
        }
        sendBattleEndResult(gameSession, smasherService);
        ResultService resultService = new ResultService();
        resultService.addResult1vs1(new Result1vs1( gameSession.players.get(gameSession.winners == 'A' ? 0 : 1).getId(), gameSession.players.get(gameSession.winners == 'A' ? 1 : 0).getId()));
    }

    private void sendBattleEndResult(GameSession gameSession, SmasherService smasherService) throws SQLException, IOException {
        UserService userService = new UserService();
        if(gameSession.isUserAWinner(user))
        {
            Smasher wonSmasher = gameSession.getWonSmasher();
            List<Smasher> mySmashers = smasherService.getUserSmashers(user.getId());

            if(mySmashers.stream().filter(s -> s.getId().equals(wonSmasher.getId())).findAny().orElse(null) == null)
            {
                userService.addWinToUser(user.getId());
                userService.addSmasherToUser(user.getId(), wonSmasher.getId());
            }
            output.writeObject(new BattleWonMessage(wonSmasher));
        }
        else {
            userService.addLoseToUser(user.getId());
            output.writeObject(new BattleLostMessage());
        }
    }
    private void logReq(Object req)
    {
        System.out.println("[REQ]: "+req);
    }
}
