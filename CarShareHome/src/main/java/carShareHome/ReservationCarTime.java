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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String path = "P59.jsp";
        
        String selectedDate = request.getParameter("selectedDate");
        String carCode = request.getParameter("carCode");
        String startTimeHour = request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        System.out.println("ReservationCarTime："+selectedDate+":"+carCode+":"+startTimeHour+":"+startTimeMinute);
        
        List<ReservationTime> reservationTimes = new ArrayList<>();
        List<ReservationTime> availableSlots = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";

            // 予約があるか確認するクエリ
            String sql = "SELECT start_date, stop_date, car.car_code, s.station_id " +
                         "FROM keybox k " +
                         "INNER JOIN station s ON s.station_id = k.station_id " +
                         "INNER JOIN reservation r ON r.car_code = k.car_code " +
                         "INNER JOIN car_db car ON car.car_code = k.car_code " +
                         "INNER JOIN model m ON m.model_id = car.model_id " +
                         "WHERE k.car_code = ? AND start_date <= ?"
                         + "AND r.finish_date is null";
            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {

                // 予約済みの時間を取得
                String startDateTime = selectedDate + " " + startTimeHour + ":" + startTimeMinute + ":00";
                pstmt.setString(1, carCode);
                pstmt.setString(2, startDateTime);

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String startDate = rs.getString("start_date");
                    String stopDate = rs.getString("stop_date");
                    ReservationTime reservationTime = new ReservationTime(startDate, stopDate, "予約不可");
                    reservationTimes.add(reservationTime);
                }

                // 予約可能な時間帯を設定
             // 予約可能な時間帯を設定
                for (int hour = Integer.parseInt(startTimeHour); hour < Integer.parseInt(startTimeHour) + 24; hour++) {
                    for (int minute = (hour == Integer.parseInt(startTimeHour) ? Integer.parseInt(startTimeMinute) : 0); minute < 60; minute += 15) {
                        String startTime = String.format("%02d:%02d", hour % 24, minute);
                        int nextMinute = minute + 15;
                        int nextHour = hour;

                        // 次の時間を計算（開始時間から15分後）
                        if (nextMinute >= 60) {
                            nextMinute = 0;
                            nextHour++;
                        }

                        String endTime = String.format("%02d:%02d", nextHour % 24, nextMinute);

                        // 予約済み時間との重複チェック
                        boolean isBooked = false;
                        for (ReservationTime reserved : reservationTimes) {
                            String reservedStartTime = reserved.getStartTime(); // 予約済みの開始時間
                            String reservedEndTime = reserved.getEndTime(); // 予約済みの終了時間

                            // 重複判定を強化
                         // 重複判定を強化
                            if (startTime.compareTo(reservedEndTime) < 0 && endTime.compareTo(reservedStartTime) > 0) {
                                isBooked = true; // 予約が重複している場合
                                break; // ループを抜ける
                            }

                        }

                        // 予約状態を判定してリストに追加
                        if (!isBooked) {
                            availableSlots.add(new ReservationTime(startTime, endTime, "予約可能"));
                        }
                    }
                }

                // 予約済み時間を出力（デバッグ用）
                System.out.println("予約済み時間:");
                for (ReservationTime reserved : reservationTimes) {
                    System.out.println(reserved.getStartTime() + " - " + reserved.getEndTime());
                }

                // 予約済み時間と予約可能時間を結合
                List<ReservationTime> combinedList = new ArrayList<>(reservationTimes);
                combinedList.addAll(availableSlots);
                request.setAttribute("combinedList", combinedList);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        // P59.jspにフォワード
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
