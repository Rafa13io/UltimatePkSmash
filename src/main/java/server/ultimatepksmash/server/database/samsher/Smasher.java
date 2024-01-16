package server.ultimatepksmash.server.database.samsher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Smasher {
    private Long id;
    private String name;
    private String description;
    private Double healthPoints;
    private String photoPath;
}
