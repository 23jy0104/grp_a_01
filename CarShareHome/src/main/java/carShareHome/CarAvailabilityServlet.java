package carShareHome;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import model.CarData;
import model.ReservationData;

@WebServlet("/CarAvailabilityServlet")
public class CarAvailabilityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションを取得
        HttpSession session = request.getSession();
        
        // リクエストパラメータの取得
        String stationId = request.getParameter("stationId");
        String startDate = request.getParameter("startDate");
        String startTime = request.getParameter("startTime");
        String endDate = request.getParameter("endDate");
        String endTime = request.getParameter("endTime");
        
        System.out.println("Station ID: " + stationId);
        System.out.println("Start Date: " + startDate);
        System.out.println("Start Time: " + startTime);
        System.out.println("End Date: " + endDate);
        System.out.println("End Time: " + endTime);

        // 日付と時間を結合
        String startDateTime = startDate + " " + startTime;
        String endDateTime = endDate + " " + endTime;

        // Timestamp の生成
        Timestamp startTimestamp;
        Timestamp endTimestamp;
        try {
            startTimestamp = Timestamp.valueOf(startDateTime);
            endTimestamp = Timestamp.valueOf(endDateTime);
        } catch (IllegalArgumentException e) {
            String encodedError = URLEncoder.encode("日付または時間のフォーマットが不正です。", "UTF-8");
            response.sendRedirect("P57.jsp?error=" + encodedError);
            return;
        }

        // デバッグ用ログ出力
        System.out.println("Station ID: " + stationId);
        System.out.println("Start Time: " + startTimestamp);
        System.out.println("End Time: " + endTimestamp);

        // データベース接続用の変数
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // データベースの接続情報
            String dbUrl = "jdbc:mysql://10.64.144.5:3306/23jya01"; // データベースのURL
            String dbUser = "23jya01"; // ユーザー名
            String dbPassword = "23jya01"; // パスワード

            // データベース接続
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // ステーションの予約状況を取得するSQLクエリ
            String reservationSql = "SELECT r.car_code, r.start_date, r.stop_date " +
                                     "FROM reservation r " +
                                     "INNER JOIN keybox k ON r.car_code = k.car_code " +
                                     "WHERE k.station_id = ? " +
                                     "AND (r.start_date < ? AND r.stop_date > ?)";
            pstmt = conn.prepareStatement(reservationSql);
            pstmt.setString(1, stationId);
            pstmt.setTimestamp(2, endTimestamp); // 終了時間を使用
            pstmt.setTimestamp(3, startTimestamp); // 開始時間を使用

            // クエリの実行
            rs = pstmt.executeQuery();
            List<ReservationData> reservations = new ArrayList<>();
            while (rs.next()) {
                ReservationData reservation = new ReservationData();
                reservation.setCarCode(rs.getString("car_code"));
                reservation.setStartDate(rs.getTimestamp("start_date"));
                reservation.setStopDate(rs.getTimestamp("stop_date"));
                reservations.add(reservation);
            }

            // 空いている車両を確認するSQLクエリ
            String carAvailabilitySql = "SELECT k.car_code, m.model_name, c.car_img " +
                                         "FROM keybox k " +
                                         "INNER JOIN car_db c ON k.car_code = c.car_code " +
                                         "INNER JOIN model m ON c.model_id = m.model_id " +
                                         "WHERE k.station_id = ? AND k.car_code NOT IN " +
                                         "(SELECT r.car_code FROM reservation r WHERE r.start_date < ? AND r.stop_date > ?)";
            PreparedStatement carAvailabilityPstmt = conn.prepareStatement(carAvailabilitySql);
            carAvailabilityPstmt.setString(1, stationId);
            carAvailabilityPstmt.setTimestamp(2, endTimestamp);
            carAvailabilityPstmt.setTimestamp(3, startTimestamp);

            ResultSet carRs = carAvailabilityPstmt.executeQuery();
            List<CarData> availableCars = new ArrayList<>();

            while (carRs.next()) {
                CarData car = new CarData();
                car.setCarCode(carRs.getString("car_code"));
                car.setCarImage(carRs.getString("car_img"));
                car.setModelName(carRs.getString("model_name"));
                availableCars.add(car);
            }

            // 空き時間の取得
            List<String> availableTimes = new ArrayList<>();
            for (ReservationData reservation : reservations) {
                availableTimes.add("空き時間: " + reservation.getStartDate() + " 〜 " + reservation.getStopDate());
            }

            // 結果をリクエストに設定
            request.getSession().setAttribute("reservations", reservations);
            request.getSession().setAttribute("availableCars", availableCars);
            request.getSession().setAttribute("availableTimes", availableTimes);

            // 結果を表示するページに転送
            RequestDispatcher rd = request.getRequestDispatcher("P57.jsp");
            rd.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace(); // エラーの詳細をログに出力
            String encodedError = URLEncoder.encode("データベースエラーが発生しました。", "UTF-8");
            response.sendRedirect("P57.jsp?error=" + encodedError); // エラーメッセージをリダイレクト
        } finally {
            // リソースをクローズ
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
