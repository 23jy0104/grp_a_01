package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
        
        List<ReservationTime> reservationTimes = new ArrayList<>();
        List<ReservationTime> availableSlots = new ArrayList<>();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";

            // 予約があるか確認するクエリ
            String sql = "SELECT start_date, stop_date FROM reservation WHERE car_code = ? AND finish_date IS NULL";
            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {

                // 予約済みの時間を取得
                String startDateTime = selectedDate + " " + startTimeHour + ":" + startTimeMinute + ":00";
                pstmt.setString(1, carCode);

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Timestamp startDate = rs.getTimestamp("start_date");
                    Timestamp stopDate = rs.getTimestamp("stop_date");

                    // TimestampをHH:mm形式のStringに変換
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    String reservedStartTime = sdf.format(startDate);
                    String reservedEndTime = sdf.format(stopDate);

                    ReservationTime reservationTime = new ReservationTime(reservedStartTime, reservedEndTime, "予約不可");
                    reservationTimes.add(reservationTime);
                }

                // 入力された時間を基に24時間後の時間を計算
                Timestamp inputTime = Timestamp.valueOf(selectedDate + " " + startTimeHour + ":" + startTimeMinute + ":00");
                Timestamp endTime24HoursLater = new Timestamp(inputTime.getTime() + 24 * 60 * 60 * 1000); // 24時間後

             // 予約可能な時間帯を設定
                for (long time = inputTime.getTime(); time < endTime24HoursLater.getTime(); time += 60 * 60 * 1000) { // 1時間ごと
                    Timestamp startTime = new Timestamp(time);
                    Timestamp endTime = new Timestamp(time + 60 * 60 * 1000); // 1時間後

                    // 予約済み時間との重複チェック
                    boolean isBooked = false;
                    for (ReservationTime reserved : reservationTimes) {
                        // reservedの時間もString型として扱う必要があるため変換
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String reservedStartTime = reserved.getStartTime(); // HH:mm形式
                        String reservedEndTime = reserved.getEndTime(); // HH:mm形式

                        // 重複判定
                        if (startTime.compareTo(Timestamp.valueOf(selectedDate + " " + reservedEndTime + ":00")) < 0 &&
                            endTime.compareTo(Timestamp.valueOf(selectedDate + " " + reservedStartTime + ":00")) > 0) {
                            isBooked = true; // 予約が重複している場合
                            break; // ループを抜ける
                        }
                    }

                    // 予約状態を判定してリストに追加
                    if (!isBooked) {
                        String availableStartTime = String.format("%tH:%tM", startTime, startTime);
                        String availableEndTime = String.format("%tH:%tM", endTime, endTime);
                        availableSlots.add(new ReservationTime(availableStartTime, availableEndTime, "予約可能"));
                    } else {
                        String unavailableStartTime = String.format("%tH:%tM", startTime, startTime);
                        String unavailableEndTime = String.format("%tH:%tM", endTime, endTime);
                        availableSlots.add(new ReservationTime(unavailableStartTime, unavailableEndTime, "予約不可")); // 予約不可の状態も追加
                    }
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
