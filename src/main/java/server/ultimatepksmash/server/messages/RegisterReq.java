package server.ultimatepksmash.server.messages;


import java.io.Serializable;

public class RegisterReq implements Serializable {
    private String userName;
    private String userPassword;
    private String userEmail;

    public RegisterReq( String userName, String userPassword, String userEmail) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
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
