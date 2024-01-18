package server.ultimatepksmash.server.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartRoundResp implements Serializable {//end round resp
    Double damageTeamsA;
    Double damageTeamsB;
    boolean wasAttackFatalForTeamA = false;
    boolean wasAttackFatalForTeamB = false;
    private Long idAttackTeamA = null;
    private Long idAttackTeamB = null;
    private Long idDefenceTeamA = null;
    private Long idDefenceTeamB = null;
    boolean isNextRoundPossible = true;
    char firstTeam; // A albo B
}
