package carShareHome;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/CarAvailabilityServlet")
public class CarAvailabilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // データベース接続テスト
        if (!testDatabaseConnection(response)) {
            return; // 接続失敗時は処理を終了
        }

        String stationId = (String) request.getSession().getAttribute("stationId");
        String stationName = request.getParameter("stationName");

        // 受け取った開始日時と終了日時を取得
        String startDate = request.getParameter("startDate");
        String startTime = request.getParameter("startTime");
        String endDate = request.getParameter("endDate");
        String endTime = request.getParameter("endTime");

        // 日時を組み合わせてISO形式に変換
        String startDateTime = startDate + "T" + startTime;
        String endDateTime = endDate + "T" + endTime;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Car> availableCars = new ArrayList<>(); // 車両オブジェクトのリスト
        
        try {
            String dbUrl = "jdbc:mysql://10.64.144.5:3306/23jya01"; // データベースのURL
            String dbUser = "23jya01"; // ユーザー名
            String dbPassword = "23jya01"; // パスワード
            String sql = "SELECT car_code, car_info, drive_type, safety_features, notes FROM cars WHERE station_id = ?";
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // ステーションに関連する空いている車両を取得するSQLクエリ
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
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("データベースエラーが発生しました。");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) {}
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) {}
            if (conn != null) try { conn.close(); } catch (SQLException e) {}
        }
    }

    // データベース接続をテストするメソッド
    private boolean testDatabaseConnection(HttpServletResponse response) {
        Connection conn = null;
        try {
            // JDBCドライバをロード
            Class.forName("com.mysql.jdbc.Driver");
            String dbUrl = "jdbc:mysql://10.64.144.5:3306/23jya01"; // データベースのURL
            String dbUser = "23jya01"; // ユーザー名
            String dbPassword = "23jya01"; // パスワード
            
            // データベースへの接続
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            return true; // 接続成功
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // エラーメッセージを表示
            try {
                response.getWriter().write("データベース接続に失敗しました。");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return false; // 接続失敗
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 接続を閉じる
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
