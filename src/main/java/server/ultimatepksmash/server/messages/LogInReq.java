package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.ultimatepksmash.server.database.user.User;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LogInReq implements Serializable {
    private String userName;
    private String userPassword;
}
