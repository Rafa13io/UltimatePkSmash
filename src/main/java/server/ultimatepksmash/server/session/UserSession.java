package server.ultimatepksmash.server.session;

import java.net.Socket;
import java.util.concurrent.Callable;

public class UserSession implements Callable<SessionEndStatus> {
    private final Socket socket;

    public UserSession(Socket socket) {
        this.socket = socket;
    }

    @Override
    public SessionEndStatus call() throws Exception {
        socket.close();
        System.out.println("Call in UserSession not implemented yet");
        throw new  UnsupportedOperationException("Not implemented yet");
        //TODO: Implement this method
        //return new SessionEndStatus(SessionEndReason.unexpectedEndOfSession);
    }
}
