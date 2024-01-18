package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.smasher.Smasher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BattleStartResponse implements Serializable  {
    private List<Smasher> smashers = new ArrayList<>();  //0 PLAYER A ,1 PLAYER B
    private List<String>  playersNames = new ArrayList<>(); //0 PLAYER A ,1 PLAYER B
}
