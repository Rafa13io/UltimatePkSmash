package server.ultimatepksmash.server.messages;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class RegisterReq implements Serializable {
    private String userName;
    private String userPassword;
    private String userEmail;
}
