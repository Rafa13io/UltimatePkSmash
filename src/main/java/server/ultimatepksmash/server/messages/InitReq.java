package server.ultimatepksmash.server.messages;


import java.io.Serializable;

public class InitReq implements Serializable {
    private InitType initType;
    private String userName;
    private String userPassword;
    private String userEmail;

    public InitReq(InitType initType, String userName, String userPassword, String userEmail) {
        this.initType = initType;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    public InitType getInitType() {
        return initType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
