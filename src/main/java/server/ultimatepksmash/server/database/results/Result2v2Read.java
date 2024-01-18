package server.ultimatepksmash.server.database.results;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class Result2v2Read implements Serializable {
    private Long id;
    private String date;
    private String winner;
    private String loser;
}
