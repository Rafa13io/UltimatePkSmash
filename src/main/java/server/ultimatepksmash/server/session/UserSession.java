package server.ultimatepksmash.server.session;

import lombok.AllArgsConstructor;
import server.ultimatepksmash.server.database.smasher.Smasher;
import server.ultimatepksmash.server.database.user.User;
import server.ultimatepksmash.server.messages.BattleStart1v1Req;
import server.ultimatepksmash.server.messages.BattleStart1v1Response;
import server.ultimatepksmash.server.messages.LogOutReq;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
                battle1v1Chosen();
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

    private void battle1v1Chosen() throws IOException, ClassNotFoundException {
        System.out.println("User wants"+ user.getUsername() +" to play 1v1 batlle");
        //BattleStart1v1Req battleStart1V1Req = (BattleStart1v1Req) input.readObject();

        Smasher mySmasher = new Smasher(0L,"pi","description", "100", 1, "\\photo");
        Smasher oponentsSmasher = new Smasher(1L,"kolo","description", "100", 2,"\\photo");
        output.writeObject(new BattleStart1v1Response(mySmasher, oponentsSmasher));
    }
    private void logReq(Object req)
    {
        System.out.println("[REQ]: "+req);
    }
}
