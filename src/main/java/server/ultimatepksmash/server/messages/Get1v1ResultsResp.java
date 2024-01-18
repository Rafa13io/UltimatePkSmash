package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import server.ultimatepksmash.server.database.results.Result1v1Read;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
public class Get1v1ResultsResp implements Serializable {
    List<Result1v1Read> result1vs1List;
}
