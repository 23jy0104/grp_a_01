package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CarInfo;

public class CarInfoDao {
    private static final String URL = "jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8";
    private static final String USER = "23jya01";
    private static final String PASSWORD = "23jya01";

    public List<CarInfo> getAllCarInfo() {
        List<CarInfo> carInfoList = new ArrayList<>();
        String sql = "SELECT m.maker_name, c.number, mo.model_name, MAX(c.model_year) AS model_year " +
                     "FROM maker m " +
                     "JOIN car_db c ON m.maker_id = c.maker_id " +
                     "JOIN model mo ON c.model_id = mo.model_id " +
                     "LEFT JOIN keybox k ON c.car_code = k.car_code " +
                     "WHERE k.car_code IS NULL " +
                     "GROUP BY m.maker_name, c.number, mo.model_name " +
                     "ORDER BY c.number ASC"; // numberで昇順に並び替え

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String makerName = rs.getString("maker_name");
                String number = rs.getString("number");
                String modelName = rs.getString("model_name");
                String modelYear = rs.getString("model_year");

                // CarInfoオブジェクトを作成してリストに追加
                carInfoList.add(new CarInfo(makerName, number, modelName, modelYear));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carInfoList;
    }

    // 新規メソッド: ナンバープレートで車両情報を取得
    public CarInfo getCarInfoByNumber(String number) {
        CarInfo carInfo = null;
        String sql = "SELECT m.maker_name, c.number, mo.model_name, c.model_year " +
                     "FROM maker m " +
                     "JOIN car_db c ON m.maker_id = c.maker_id " +
                     "JOIN model mo ON c.model_id = mo.model_id " +
                     "WHERE c.number = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, number);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String makerName = rs.getString("maker_name");
                String modelName = rs.getString("model_name");
                String modelYear = rs.getString("model_year");

                // CarInfoオブジェクトを作成
                carInfo = new CarInfo(makerName, number, modelName, modelYear);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carInfo; // ナンバープレートに対応する情報がない場合はnullを返す
    }
    
    // 車両をkeyboxに追加するメソッド
    public boolean addCarToKeybox(String selectedPlate, String stationName, String stationAddress) {
        // 空いているkeybox_idを取得
        Integer availableKeyboxId = getAvailableKeyboxId(stationName, stationAddress);

        if (availableKeyboxId == null) {
            System.out.println("すべてのkeybox_idが埋まっています。");
            return false;
        }

        // keyboxに車両を追加
        String sql = "INSERT INTO keybox (keybox_id, station_id, car_code) " +
                     "VALUES (?, (SELECT station_id FROM station WHERE station_name = ? AND station_address = ? LIMIT 1), " +
                     "(SELECT car_code FROM car_db WHERE number = ? LIMIT 1))";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, availableKeyboxId);
            pstmt.setString(2, stationName);
            pstmt.setString(3, stationAddress);
            pstmt.setString(4, selectedPlate);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("挿入行数: " + rowsAffected);
            return rowsAffected > 0; // 成功した場合はtrueを返す
        } catch (SQLException e) {
            e.printStackTrace(); // エラーの詳細を出力
            return false; // 失敗した場合はfalseを返す
        }
    }
    // 使用可能なkeybox_idを取得するメソッド
    private Integer getAvailableKeyboxId(String stationName, String stationAddress) {
        // station_idを取得するためのサブクエリ
        String sql = "SELECT keybox_id FROM keybox WHERE station_id = (" +
                     "SELECT station_id FROM station WHERE station_name = ? AND station_address = ? LIMIT 1" +
                     ") ORDER BY keybox_id ASC";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, stationName);
            pstmt.setString(2, stationAddress);
            
            ResultSet rs = pstmt.executeQuery();

            // 1から6までのkeybox_idをチェック
            boolean[] usedIds = new boolean[7]; // 1-indexedで使うため、0は未使用

            while (rs.next()) {
                int keyboxId = rs.getInt("keybox_id");
                usedIds[keyboxId] = true; // 使用中のIDをマーク
            }

            // 空いている最小のkeybox_idを返す
            for (int i = 1; i <= 6; i++) {
                if (!usedIds[i]) {
                    return i; // 空いているIDを返す
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 空いているIDがない場合はnullを返す
    }

}
