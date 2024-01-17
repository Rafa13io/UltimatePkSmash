package server.ultimatepksmash.server.database.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result1vs1 {
    private Long id;
    private String date;
    private Long winner_id;
    private Long loser_id;
}
