package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.ultimatepksmash.server.database.user.User;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class LogInResp implements Serializable {
    private boolean success;
    public User user = new User();

}
