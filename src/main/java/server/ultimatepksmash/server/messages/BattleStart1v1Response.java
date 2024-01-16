package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.ultimatepksmash.server.database.samsher.Smasher;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BattleStart1v1Response implements Serializable  {
    private Smasher mySmasher;
    private Smasher opnoentsSmasher;
}
