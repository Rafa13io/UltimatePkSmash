package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BattleStart1v1Req implements Serializable {
    Long usersSmasherId;
}
