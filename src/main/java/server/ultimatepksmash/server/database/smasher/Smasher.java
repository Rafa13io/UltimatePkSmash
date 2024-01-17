package server.ultimatepksmash.server.database.smasher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Smasher implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Integer ects;
    private Double healthPoints;
    private String photoPath;
}
