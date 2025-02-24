package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ReservationTime;

@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String path = "P59.jsp";
        
        String selectedDate = request.getParameter("selectedDate");
        String stationId = request.getParameter("stationId");
        String modelName = request.getParameter("modelName");
        System.out.println("selectedDate: " + selectedDate);
        System.out.println("stationId: " + stationId);
        System.out.println("modelName: " + modelName);

        List<ReservationTime> reservationTimes = new ArrayList<>(); // 予約している時間を格納
        List<ReservationTime> availableSlots = new ArrayList<>();   // 予約していない時間を格納

        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";

            // 予約があるか確認するクエリ
            String sql = "SELECT start_date, stop_date, k.station_id, m.model_name " +
                         "FROM keybox k " +
                         "INNER JOIN station s ON s.station_id = k.station_id " +
                         "INNER JOIN reservation r ON r.car_code = k.car_code " +
                         "INNER JOIN car_db car ON car.car_code = k.car_code " +
                         "INNER JOIN model m ON m.model_id = car.model_id " +
                         "WHERE start_date >= ? AND stop_date <= ? " +
                         "AND s.station_id = ? AND model_name = ?";

            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {

                pstmt.setString(1, selectedDate + " 00:00:00");
                pstmt.setString(2, selectedDate + " 23:45:00");
                pstmt.setString(3, stationId);  
                pstmt.setString(4, modelName);   

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String startDate = rs.getString("start_date");
                    String stopDate = rs.getString("stop_date");
                    ReservationTime reservationTime = new ReservationTime(startDate, stopDate, "予約済み");
                    reservationTimes.add(reservationTime);
                }
                System.out.println("予約時間のリストサイズ: " + reservationTimes.size());
                request.setAttribute("reservationTimes", reservationTimes);
            }

            // 予約可能な時間帯を設定（例: 9:00から18:00まで、15分単位）
         // 予約可能な時間帯を設定（例: 9:00から18:00まで、15分単位）
         // 予約可能な時間帯を設定（例: 0:00から23:45まで、15分単位）
            for (int hour = 0; hour < 24; hour++) {
                for (int minute = 0; minute < 60; minute += 15) {
                    String startTime = String.format("%02d:%02d", hour, minute);
                    
                    // 次の時間を計算
                    int nextMinute = minute + 15;
                    int nextHour = hour;

                    // 分が60を超える場合の処理
                    if (nextMinute >= 60) {
                        nextMinute = 0;
                        nextHour++; // 時間を1つ進める
                    }

                    // 23:59を超えないようにチェック
                    if (nextHour >= 24) {
                        break; // 24時を超えたらループを終了
                    }

                    String endTime = String.format("%02d:%02d", nextHour, nextMinute);

                    // ReservationTimeオブジェクトを作成
                    ReservationTime availableTime = new ReservationTime(startTime, endTime, "予約可能");
                    availableSlots.add(availableTime);
                }
            }


            // 予約済み時間と予約可能時間を結合
            List<ReservationTime> combinedList = new ArrayList<>(reservationTimes);
            combinedList.addAll(availableSlots);
            request.setAttribute("combinedList", combinedList); // 結合したリストをリクエストに設定

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // P59.jspにフォワード
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
