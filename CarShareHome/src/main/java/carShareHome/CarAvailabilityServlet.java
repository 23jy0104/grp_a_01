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
        // リクエストからステーションIDを取得
        String stationId = request.getParameter("stationId"); // ステーションIDを受け取る

        // デバッグ用: 受け取ったステーションIDを表示
        System.out.println("Station ID: " + stationId);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String dbUrl = "jdbc:mysql://10.64.144.5:3306/23jya01"; // データベースのURL
            String dbUser = "23jya01"; // ユーザー名
            String dbPassword = "23jya01"; // パスワード

            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            
            // ステーションの予約状況を取得するSQLクエリ
            String reservationSql = "SELECT car_code, start_date, stop_date FROM reservation WHERE station_id = ?";
            pstmt = conn.prepareStatement(reservationSql);
            pstmt.setString(1, stationId); // ステーションIDをセット

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

            // デバッグ用: 予約の数を確認
            System.out.println("Number of reservations: " + reservations.size());

            // 空いている車両を確認するSQLクエリ
            String carAvailabilitySql = "SELECT car_code, model_name, car_img FROM car_db WHERE station_id = ? AND car_code NOT IN (SELECT car_code FROM reservation WHERE station_id = ?)";
            PreparedStatement carAvailabilityPstmt = conn.prepareStatement(carAvailabilitySql);
            carAvailabilityPstmt.setString(1, stationId);
            carAvailabilityPstmt.setString(2, stationId);

            ResultSet carRs = carAvailabilityPstmt.executeQuery();
            List<CarData> availableCars = new ArrayList<>();
            while (carRs.next()) {
                CarData car = new CarData();
                car.setCarCode(carRs.getString("car_code"));
                car.setCarImage(carRs.getString("car_img"));
                availableCars.add(car);
            }

            // デバッグ用: 空いている車両の数を確認
            System.out.println("Available cars size: " + availableCars.size());

            // 空いている車両がある場合
            if (!availableCars.isEmpty()) {
                request.getSession().setAttribute("availableCars", availableCars);
                RequestDispatcher rd = request.getRequestDispatcher("P57.jsp"); // 車両情報表示ページ
                rd.forward(request, response);
            } else {
                // 空いている車両がない場合
                String encodedMessage = URLEncoder.encode("指定したステーションには空いている車両がありません。", "UTF-8");
                response.sendRedirect("P57.jsp?message=" + encodedMessage);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // エラーメッセージを表示
            String encodedError = URLEncoder.encode("データベースエラーが発生しました。", "UTF-8");
            response.sendRedirect("P57.jsp?error=" + encodedError);
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
