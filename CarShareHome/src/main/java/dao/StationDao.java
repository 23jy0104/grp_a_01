package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Station;

public class StationDao {
    private Connection con = null;

    public StationDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8", "23jya01", "23jya01");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("データベース接続失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void connectionClose() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("接続終了失敗: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        String sql = "SELECT s.station_id, s.station_name, s.station_address " +
                     "FROM station s " +
                     "LEFT JOIN keybox kb ON s.station_id = kb.station_id " +
                     "GROUP BY s.station_id, s.station_name, s.station_address " +
                     "HAVING COUNT(kb.car_code)";

        System.out.println("全ステーションを取得中...");

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Station station = new Station(rs.getString("station_id"),
                                               rs.getString("station_name"),
                                               rs.getString("station_address"));
                stations.add(station);
                System.out.println("取得したステーション: " + station.getStationName());
            }
        } catch (SQLException e) {
            System.err.println("ステーション取得失敗: " + e.getMessage());
            e.printStackTrace();
        }

        return stations;
    }

    public List<Station> getStationsByAddress(String address) {
        List<Station> stations = new ArrayList<>();
        String sql = "SELECT s.station_id, s.station_name, s.station_address " +
                     "FROM station s " +
                     "LEFT JOIN keybox kb ON s.station_id = kb.station_id " +
                     "WHERE s.station_address LIKE ? " +
                     "GROUP BY s.station_id, s.station_name, s.station_address " +
                     "HAVING COUNT(kb.car_code) < 3";


        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + address + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Station station = new Station(rs.getString("station_id"),
                                                   rs.getString("station_name"),
                                                   rs.getString("station_address"));
                    stations.add(station);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stations;
    }
}
