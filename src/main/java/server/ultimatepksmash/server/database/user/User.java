package server.ultimatepksmash.server.database.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private int numOfPlayedGames;
    private int numOfWins;
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.numOfPlayedGames = 0;
        this.numOfWins = 0;
    }
}
