package server.ultimatepksmash.server.database.smasher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.skills.attack.Attack;
import server.ultimatepksmash.server.database.skills.defence.Defence;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;


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
    
    private List<Attack> attacks = null;
    private List<Defence> defences = null;
    
    public Smasher(Long id, String name, String description, Double healthPoints, Integer ects, String photoPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.healthPoints = healthPoints;
        this.ects = ects;
        this.photoPath = photoPath;
    }
    
    @Override
    public String toString() {
        return new StringJoiner("", Smasher.class.getSimpleName() + "\n", "\n")
                .add("id=" + id + " ")
                .add("name='" + name + "' ")
                .add("description='" + description + "' ")
                .add("healthPoints='" + healthPoints + "' ")
                .add("ects=" + ects + " ")
                .add("photoPath='" + photoPath + "' \n")
                .add(formatList(attacks))
                .add(formatList(defences))
                .toString();
    }
    
    private <T> String formatList(List<T> list) {
        if (list == null) {
            return "null";
        }
        
        StringJoiner joiner = new StringJoiner("");
        for (T item : list) {
            joiner.add(item.toString() + "\n");
        }
        return joiner.toString();
    }
}
