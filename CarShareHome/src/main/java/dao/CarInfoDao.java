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
}
