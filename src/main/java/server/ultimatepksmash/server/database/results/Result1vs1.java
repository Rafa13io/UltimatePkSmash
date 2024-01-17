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
    private Long winnerId;
    private Long loserId;
    
    public Result1vs1(Long winnerId, Long loserId) {
        this.winnerId = winnerId;
        this.loserId = loserId;
    }
}
