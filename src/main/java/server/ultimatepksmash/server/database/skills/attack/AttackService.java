package server.ultimatepksmash.server.database.skills.attack;

import server.ultimatepksmash.server.database.DataBaseService;
import server.ultimatepksmash.server.database.skills.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AttackService {
    private static final Connection connection = DataBaseService.connection;
    
    // returns list of smashers owned by a user (id)
    public List<Attack> getSmasherAttacks(Long smasherId) throws SQLException {
        String sql = "select * from p_attack_skill pas join p_smasher ps on pas.smasher_id = ps.id where ps.id = ?;";
        PreparedStatement getAttacks = connection.prepareStatement(sql);
        getAttacks.setLong(1, smasherId);
        ResultSet resultSet = getAttacks.executeQuery();
        
        List<Attack> attacks = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Attack attack = new Attack();
            mapAttack(attack, resultSet);
            attacks.add(attack);
        }
        
        resultSet.close();
        getAttacks.close();
        return attacks;
    }
    
    private static void mapAttack(Attack attack, ResultSet resultSet) throws SQLException {
        attack.setId(resultSet.getLong("id"));
        attack.setName(resultSet.getString("name"));
        attack.setDescription(resultSet.getString("description"));
        attack.setType(Type.valueOf(resultSet.getString("type")));
        attack.setAttackPoints(resultSet.getInt("attack_points"));
        attack.setSmasherId(resultSet.getLong("smasher_id"));
    }
}
