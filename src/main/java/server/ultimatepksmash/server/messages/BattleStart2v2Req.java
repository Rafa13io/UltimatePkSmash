package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class BattleStart2v2Req implements Serializable {
    private Long usersSmasherId;
}
