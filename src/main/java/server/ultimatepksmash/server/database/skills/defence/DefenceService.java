package server.ultimatepksmash.server.database.skills.defence;

import server.ultimatepksmash.server.database.DataBaseService;
import server.ultimatepksmash.server.database.skills.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DefenceService {
    private static final Connection connection = DataBaseService.connection;
    
    public List<Defence> getSmasherDefences(Long smasherId) throws SQLException {
        String sql = "select * from p_defence_skill pas join p_smasher ps on pas.smasher_id = ps.id where ps.id = ?;";
        PreparedStatement getDefences = connection.prepareStatement(sql);
        getDefences.setLong(1, smasherId);
        ResultSet resultSet = getDefences.executeQuery();
        
        List<Defence> attacks = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Defence defence = new Defence();
            mapAttack(defence, resultSet);
            attacks.add(defence);
        }
        
        resultSet.close();
        getDefences.close();
        return attacks;
    }
    
    private static void mapAttack(Defence defence, ResultSet resultSet) throws SQLException {
        defence.setId(resultSet.getLong("id"));
        defence.setName(resultSet.getString("name"));
        defence.setDescription(resultSet.getString("description"));
        defence.setType(Type.valueOf(resultSet.getString("type")));
        defence.setDefencePoints(resultSet.getInt("defence_points"));
        defence.setSmasherId(resultSet.getLong("smasher_id"));
    }
}
