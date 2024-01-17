package server.ultimatepksmash.server.database.results;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import server.ultimatepksmash.server.database.DataBaseService;
import server.ultimatepksmash.server.database.user.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
public class ResultService {
    private static final Connection connection = DataBaseService.connection;
    private UserService userService = new UserService();
    
    public List<Result1vs1> getResults1vs1() throws SQLException {
        String sql = "SELECT * FROM p_result_1vs1";
        PreparedStatement getResults1vs1 = connection.prepareStatement(sql);
        ResultSet resultSet = getResults1vs1.executeQuery();
        
        List<Result1vs1> results1vs1 = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Result1vs1 result1vs1 = new Result1vs1();
            mapResult1vs1(result1vs1, resultSet);
            results1vs1.add(result1vs1);
        }
        resultSet.close();
        getResults1vs1.close();
        return results1vs1;
    }
    
    public void addResult1vs1(Result1vs1 result1vs1) throws SQLException {
        if (Objects.equals(result1vs1.getWinnerId(), result1vs1.getLoserId())) {
            throw new RuntimeException("A player cannot verse himself lol");
        }
        
        PreparedStatement addResult1vs1 = connection.prepareStatement(
            "INSERT INTO public.p_result_1vs1 (\"date\", winner_id, loser_id) " +
                    "VALUES(?, ?, ?);"
        );
        
        addResult1vs1.setDate(1, new java.sql.Date(new Date().getTime()));
        addResult1vs1.setLong(2, result1vs1.getWinnerId());
        addResult1vs1.setLong(3, result1vs1.getLoserId());
        addResult1vs1.executeUpdate();
        System.out.println("added result from 1vs1 battle: " + result1vs1);
        addResult1vs1.close();
        
        // update users statistics
        userService.addWinToUser(result1vs1.getWinnerId());
        userService.addLoseToUser(result1vs1.getLoserId());
    }
    
    private static void mapResult1vs1(Result1vs1 result1vs1, ResultSet resultSet) throws SQLException {
        result1vs1.setId(resultSet.getLong("id"));
        result1vs1.setDate(resultSet.getString("date"));
        result1vs1.setWinnerId(resultSet.getLong("winner_id"));
        result1vs1.setLoserId(resultSet.getLong("loser_id"));
    }
    
    public List<Result2vs2> getResults2vs2() throws SQLException {
        String sql = "SELECT * FROM p_result_2vs2";
        PreparedStatement getResults2vs2 = connection.prepareStatement(sql);
        ResultSet resultSet = getResults2vs2.executeQuery();
        
        List<Result2vs2> results2vs2 = new ArrayList<>();
        // Iterate over the rows
        while (resultSet.next()) {
            Result2vs2 result2vs2 = new Result2vs2();
            mapResult2vs2(result2vs2, resultSet);
            results2vs2.add(result2vs2);
        }
        resultSet.close();
        getResults2vs2.close();
        return results2vs2;
    }
    
    public void addResult2vs2(Result2vs2 result2vs2) throws SQLException {
        PreparedStatement addResult2vs2 = connection.prepareStatement(
                "INSERT INTO public.p_result_2vs2 (\"date\", winner1_id, winner2_id, loser1_id, loser2_id) " +
                        "VALUES(?, ?, ?, ?, ?);"
        );
        
        addResult2vs2.setDate(1, new java.sql.Date(new Date().getTime()));
        addResult2vs2.setLong(2, result2vs2.getWinner1Id());
        addResult2vs2.setLong(3, result2vs2.getWinner2Id());
        addResult2vs2.setLong(4, result2vs2.getLoser1Id());
        addResult2vs2.setLong(5, result2vs2.getLoser2Id());
        addResult2vs2.executeUpdate();
        System.out.println("added result from 2vs2 battle: " + result2vs2);
        addResult2vs2.close();
        
        // update users statistics
        userService.addWinToUser(result2vs2.getWinner1Id());
        userService.addWinToUser(result2vs2.getWinner2Id());
        userService.addLoseToUser(result2vs2.getLoser1Id());
        userService.addLoseToUser(result2vs2.getLoser2Id());
    }
    
    private static void mapResult2vs2(Result2vs2 result2vs2, ResultSet resultSet) throws SQLException {
        result2vs2.setId(resultSet.getLong("id"));
        result2vs2.setDate(resultSet.getString("date"));
        result2vs2.setWinner1Id(resultSet.getLong("winner1_id"));
        result2vs2.setWinner2Id(resultSet.getLong("winner2_id"));
        result2vs2.setLoser1Id(resultSet.getLong("loser1_id"));
        result2vs2.setLoser2Id(resultSet.getLong("loser2_id"));
    }
}
