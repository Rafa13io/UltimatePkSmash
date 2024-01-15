package server.ultimatepksmash.server.messages;

import java.io.Serializable;

public class RegisterResp implements Serializable {
    private boolean result;
    private String message;

    public RegisterResp(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
