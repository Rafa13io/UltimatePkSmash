package server.ultimatepksmash.server.database.smasher;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.DataBaseService;
import server.ultimatepksmash.server.database.skills.attack.AttackService;
import server.ultimatepksmash.server.database.skills.defence.DefenceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
public class SmasherService {
    private static final Connection connection = DataBaseService.connection;
    private AttackService attackService = new AttackService();
    private DefenceService defenceService = new DefenceService();
    
    public List<Smasher> getSmashers() throws SQLException {
        PreparedStatement getSmashers = connection.prepareStatement("SELECT * FROM p_smasher");
        ResultSet resultSet = getSmashers.executeQuery();
        
        List<Smasher> smashers = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Smasher smasher = new Smasher();
            mapSmasher(smasher, resultSet);
            smashers.add(smasher);
        }
        
        resultSet.close();
        getSmashers.close();
        return smashers;
    }
    
    public Smasher getSmasher(Long smasherId) throws SQLException {
        String sql = "select * from p_smasher where id = ?;";
        PreparedStatement getSmasher = connection.prepareStatement(sql);
        getSmasher.setLong(1, smasherId);
        ResultSet resultSet = getSmasher.executeQuery();
        
        // Iterate over the rows
        resultSet.next();
        Smasher smasher = new Smasher();
        mapSmasher(smasher, resultSet);
        
        resultSet.close();
        getSmasher.close();
        return smasher;
    }
    
    // returns list of smashers owned by a user (id)
    public List<Smasher> getUserSmashers(Long userId) throws SQLException {
        String sql = "select * from p_smasher ps join p_smasher_user psu on psu.smasher_id = ps.id where psu.user_id = ?;";
        PreparedStatement getSmashers = connection.prepareStatement(sql);
        getSmashers.setLong(1, userId);
        ResultSet resultSet = getSmashers.executeQuery();
        
        List<Smasher> smashers = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Smasher smasher = new Smasher();
            mapSmasher(smasher, resultSet);
            smashers.add(smasher);
        }
        
        resultSet.close();
        getSmashers.close();
        return smashers;
    }
    
    // sets empty smasher fields with data returned by database
    private void mapSmasher(Smasher smasher, ResultSet resultSet) throws SQLException {
        smasher.setId(resultSet.getLong("id"));
        smasher.setName(resultSet.getString("name"));
        smasher.setDescription(resultSet.getString("description"));
        smasher.setHealthPoints(resultSet.getDouble("health_points"));
        smasher.setEcts(resultSet.getInt("ECTS"));
        smasher.setPhotoPath("none"); //todo: make it later
        
        if (attackService != null && defenceService != null) {
            smasher.setAttacks(attackService.getSmasherAttacks(smasher.getId()));
            smasher.setDefences(defenceService.getSmasherDefences(smasher.getId()));
        }
    }
}
