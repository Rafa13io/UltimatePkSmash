package server.ultimatepksmash.server.database.skills.defence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.skills.Type;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Defence {
    private Long id;
    private String name;
    private String description;
    private Type type;
    private Integer defencePoints;
    private Long smasherId;
}
