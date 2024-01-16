package server.ultimatepksmash.server.database.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import server.ultimatepksmash.server.database.samsher.Smasher;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{
    private Long id;
    private String username;
    private String email;
    private String password;
    private int numOfPlayedGames;
    private int numOfWins;
    List<Smasher> smashers;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.numOfPlayedGames = 0;
        this.numOfWins = 0;
    }
}
