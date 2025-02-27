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

@WebServlet("/CarAvailabilityServlet")
public class CarAvailabilityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String path = "";
        String selectedDate = request.getParameter("selectedDate");
        String startTimeHour = request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        String stationId = request.getParameter("stationId");

        List<ReservationTime> reservationTimes = new ArrayList<>();
        List<ReservationTime> availableSlots = new ArrayList<>();
        List<String> availableCarModels = new ArrayList<>(); // 空いている車種を格納するリスト

        // 車両情報を格納するリストを作成
        List<String> carCodes = new ArrayList<>();
        List<String> carImages = new ArrayList<>();
        List<String> carModels = new ArrayList<>();
        String stationName = ""; // 最後に使用するステーション名を格納する変数を用意

        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";

            // 予約があるか確認するクエリ
            String sql = "SELECT start_date, stop_date, k.car_code, m.model_name, car.car_img, s.station_name "
                    + "FROM keybox k "
                    + "INNER JOIN Reservation r ON r.car_code = k.car_code "
                    + "INNER JOIN car_db car ON car.car_code = k.car_code "
                    + "INNER JOIN model m ON m.model_id = car.model_id "
                    + "INNER JOIN station s ON s.station_id = k.station_id "
                    + "WHERE k.station_id = ? AND r.finish_date IS NULL";

            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {

                pstmt.setString(1, stationId);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    // 予約の取得
                    Timestamp startDate = rs.getTimestamp("start_date");
                    Timestamp stopDate = rs.getTimestamp("stop_date");
                    String carCode = rs.getString("car_code");
                    String modelName = rs.getString("model_name");
                    String img = rs.getString("car_img");
                    stationName = rs.getString("station_name"); // 最後の車両のステーション名を取得

                    SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String reservedStartTime = sdfDateTime.format(startDate);
                    String reservedEndTime = sdfDateTime.format(stopDate);

                    ReservationTime reservationTime = new ReservationTime(reservedStartTime, reservedEndTime, "予約不可");
                    reservationTimes.add(reservationTime);

                    // 車両情報をリストに追加
                    carCodes.add(carCode);
                    carModels.add(modelName);
                    carImages.add(img);
                    
                    // 予約済みの車両モデルをリストに追加
                    if (!availableCarModels.contains(modelName)) {
                        availableCarModels.add(modelName); // 重複チェック
                    }
                }

                // 入力された時間を基に24時間後の時間を計算
                Timestamp inputTime = Timestamp.valueOf(selectedDate + " " + startTimeHour + ":" + startTimeMinute + ":00");
                Timestamp endTime24HoursLater = new Timestamp(inputTime.getTime() + 24 * 60 * 60 * 1000);

                for (long time = inputTime.getTime(); time < endTime24HoursLater.getTime(); time += 60 * 60 * 1000) {
                    SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    Timestamp startTime = new Timestamp(time);
                    Timestamp endTime = new Timestamp(time + 60 * 60 * 1000);

                    boolean isBooked = false;
                    for (ReservationTime reserved : reservationTimes) {
                        String reservedStartTime = reserved.getStartDateTime();
                        String reservedEndTime = reserved.getEndDateTime();

                        Timestamp reservedStartDateTime = Timestamp.valueOf(reservedStartTime + ":00");
                        Timestamp reservedEndDateTime = Timestamp.valueOf(reservedEndTime + ":00");

                        if (startTime.compareTo(reservedEndDateTime) < 0 && endTime.compareTo(reservedStartDateTime) > 0) {
                            isBooked = true;
                            break;
                        }
                    }

                    String availableStartTime = sdfDateTime.format(startTime);
                    String availableEndTime = sdfDateTime.format(endTime);
                    if (!isBooked) {
                        availableSlots.add(new ReservationTime(availableStartTime, availableEndTime, "予約可能"));
                    } else {
                        availableSlots.add(new ReservationTime(availableStartTime, availableEndTime, "予約不可"));
                    }
                }

                List<ReservationTime> combinedList = new ArrayList<>(reservationTimes);
                combinedList.addAll(availableSlots);
                request.setAttribute("combinedList", combinedList);
                request.setAttribute("availableCarModels", availableCarModels); // 空いている車種をリクエストにセット
                request.setAttribute("selectedDate", selectedDate);
                request.setAttribute("stationId", stationId);
                request.setAttribute("carCodes", carCodes);
                request.setAttribute("carImages", carImages);
                request.setAttribute("carModels", carModels);
                request.setAttribute("stationName", stationName); // 最後のステーション名を設定
                path = "P57.jsp";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
