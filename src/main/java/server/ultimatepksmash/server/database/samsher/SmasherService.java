package server.ultimatepksmash.server.database.samsher;

import server.ultimatepksmash.server.database.DataBaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SmasherService {
    private final Connection connection = DataBaseService.connection;
    
    public List<Smasher> getSmashers() throws SQLException {
        PreparedStatement getSmashers = connection.prepareStatement("SELECT * FROM p_smasher");
        ResultSet resultSet = getSmashers.executeQuery();
        
        List<Smasher> smashers = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Smasher smasher = new Smasher();
            smasher.setId(resultSet.getLong("id"));
            smasher.setName(resultSet.getString("name"));
            smasher.setDescription(resultSet.getString("description"));
            smasher.setHealthPoints(resultSet.getString("health_points"));
            smasher.setPhotoPath(""); //todo: make it later
            
            smashers.add(smasher);
        }
        
        resultSet.close();
        getSmashers.close();
        return smashers;
    }
    
    
}
