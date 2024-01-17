package server.ultimatepksmash.server.database.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result2vs2 {
    private Long id;
    private String date;
    private Long winner1Id;
    private Long winner2Id;
    private Long loser1Id;
    private Long loser2Id;
    
    public Result2vs2(Long winner1Id, Long winner2Id, Long loser1Id, Long loser2Id) {
        this.winner1Id = winner1Id;
        this.winner2Id = winner2Id;
        this.loser1Id = loser1Id;
        this.loser2Id = loser2Id;
    }
}
