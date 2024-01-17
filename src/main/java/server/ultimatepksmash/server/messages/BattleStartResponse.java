package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.samsher.Smasher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BattleStartResponse implements Serializable  {
    private List<Smasher> smashers = new ArrayList<>();
    private List<String>  playersNames = new ArrayList<>();
}
