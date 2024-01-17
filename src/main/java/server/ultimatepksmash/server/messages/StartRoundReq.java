package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class StartRoundReq implements Serializable {
    private Long idAttack;
    private Long idDefence;
}
