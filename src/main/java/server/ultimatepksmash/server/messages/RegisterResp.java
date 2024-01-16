package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class RegisterResp implements Serializable {
    private boolean successful;
    private String message;
}
