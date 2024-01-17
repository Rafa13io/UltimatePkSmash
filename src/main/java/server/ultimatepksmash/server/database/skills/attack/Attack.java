package server.ultimatepksmash.server.database.skills.attack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.skills.Type;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attack {
    private Long id;
    private String name;
    private String description;
    private Type type;
    private Integer attackPoints;
    private Long smasherId;
}
