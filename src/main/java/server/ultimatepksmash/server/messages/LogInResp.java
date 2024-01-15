package server.ultimatepksmash.server.messages;

import java.io.Serializable;

public class LogInResp implements Serializable {
    private boolean result;

    public LogInResp(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }
}
