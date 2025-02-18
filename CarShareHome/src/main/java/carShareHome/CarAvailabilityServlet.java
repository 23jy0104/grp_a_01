package carShareHome;
// CarAvailabilityServlet.java
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CarAvailabilityServlet")
public class CarAvailabilityServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String stationId = (String) request.getSession().getAttribute("stationId");
	    String stationName = request.getParameter("stationName");

	    // 受け取った開始日時と終了日時を取得
	    String startDateTime = request.getParameter("datetime1");
	    String endDateTime = request.getParameter("datetime2");

        String dbUrl = "jdbc:mysql://10.64.144.5:3306/23jya01"; // データベースのURL
        String dbUser = "23jya01"; // ユーザー名
        String dbPassword = "23jya01"; // パスワード

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Car> availableCars = new ArrayList<>(); // 車両オブジェクトのリスト

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // ステーションに関連する空いている車両を取得するSQLクエリ
            String sql = "SELECT car_code, car_info, drive_type, safety_features, notes FROM cars WHERE station_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, stationId);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                String carCode = rs.getString("car_code");
                String carInfo = rs.getString("car_info");
                String driveType = rs.getString("drive_type");
                String safetyFeatures = rs.getString("safety_features");
                String notes = rs.getString("notes");

                // 各車両の空き時間を判定するロジックを追加
                List<String> availableTimes = getAvailableTimes(conn, carCode, startDateTime, endDateTime);
                availableCars.add(new Car(carInfo, availableTimes, driveType, safetyFeatures, notes));
            }

            request.setAttribute("availableCars", availableCars);
            request.setAttribute("stationName", stationName); // ステーション名を渡す
            request.getRequestDispatcher("P57.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }

    // 空き時間を取得するメソッド
    private List<String> getAvailableTimes(Connection conn, String carCode, String startDateTime, String endDateTime) throws SQLException {
        List<String> availableTimes = new ArrayList<>();

        // 0時から23時までの時間帯を設定
        for (int hour = 0; hour < 24; hour++) {
            String timeSlot = String.format("%02d:00:00", hour);
            // 予約状況を確認するSQLクエリ
            String sql = "SELECT COUNT(*) FROM reservation WHERE car_code = ? AND (start_date < ? AND stop_date > ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, carCode);
                pstmt.setString(2, timeSlot);
                pstmt.setString(3, timeSlot);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    availableTimes.add(timeSlot);
                }
            }
        }
        return availableTimes;
    }
}

// 車両情報を格納するクラス
class Car {
    String carInfo;
    List<String> availableTimes; // 空き時間のリスト
    String driveType;
    String safetyFeatures;
    String notes;

    public Car(String carInfo, List<String> availableTimes, String driveType, String safetyFeatures, String notes) {
        this.carInfo = carInfo;
        this.availableTimes = availableTimes;
        this.driveType = driveType;
        this.safetyFeatures = safetyFeatures;
        this.notes = notes;
    }
}
