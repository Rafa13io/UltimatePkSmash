package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.ultimatepksmash.server.database.smasher.Smasher;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BattleWonMessage implements Serializable {
    Smasher wonSmasher;
}
