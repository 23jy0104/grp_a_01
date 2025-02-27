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
        String path = ""; // 遷移先のパスを適切に設定してください
        String selectedDate = request.getParameter("selectedDate");
        String startTimeHour = request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        String stationId = request.getParameter("stationId");

        List<ReservationTime> reservationTimes = new ArrayList<>();
        List<ReservationTime> availableSlots = new ArrayList<>();
        List<String> availableCarModels = new ArrayList<>();

        // 車両情報を格納するリストを作成
        List<String> carCodes = new ArrayList<>();
        List<String> carImages = new ArrayList<>();
        List<String> carModels = new ArrayList<>();
        String stationName = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
            final String user = "23jya01";
            final String pass = "23jya01";

            // 空いている車両を取得するクエリ
            String availableCarsSql = "SELECT k.car_code, m.model_name, car.car_img, s.station_name "
                                       + "FROM keybox k "
                                       + "INNER JOIN car_db car ON car.car_code = k.car_code "
                                       + "INNER JOIN model m ON m.model_id = car.model_id "
                                       + "INNER JOIN station s ON s.station_id = k.station_id "
                                       + "LEFT JOIN Reservation r ON r.car_code = k.car_code AND r.finish_date IS NULL "
                                       + "WHERE k.station_id = ? AND r.car_code IS NULL";

            // 予約情報を取得するクエリ
            String reservationsSql = "SELECT r.car_code, r.start_date, r.finish_date, m.model_name "
                                     + "FROM Reservation r "
                                     + "INNER JOIN car_db car ON r.car_code = car.car_code "
                                     + "INNER JOIN model m ON m.model_id = car.model_id "
                                     + "INNER JOIN keybox k ON k.car_code =r.car_code"
                                     + "WHERE r.finish_date IS NULL AND k.station_id = ?";

            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement availableCarsStmt = con.prepareStatement(availableCarsSql);
                 PreparedStatement reservationsStmt = con.prepareStatement(reservationsSql)) {

                // 空いている車両を取得
                availableCarsStmt.setString(1, stationId);
                ResultSet availableCarsRs = availableCarsStmt.executeQuery();

                while (availableCarsRs.next()) {
                    String carCode = availableCarsRs.getString("car_code");
                    String modelName = availableCarsRs.getString("model_name");
                    String img = availableCarsRs.getString("car_img");
                    stationName = availableCarsRs.getString("station_name");

                    // 車両情報をリストに追加
                    carCodes.add(carCode);
                    carModels.add(modelName);
                    carImages.add(img);

                    // 空いている車両モデルをリストに追加
                    if (!availableCarModels.contains(modelName)) {
                        availableCarModels.add(modelName);
                    }
                }

                // 予約情報を取得
                reservationsStmt.setString(1, stationId);
                ResultSet reservationsRs = reservationsStmt.executeQuery();

                while (reservationsRs.next()) {
                    String carCode = reservationsRs.getString("car_code");
                    Timestamp startDate = reservationsRs.getTimestamp("start_date");
                    Timestamp finishDate = reservationsRs.getTimestamp("finish_date");
                    String modelName = reservationsRs.getString("model_name");

                    // ReservationTimeオブジェクトを作成しリストに追加
                    reservationTimes.add(new ReservationTime(startDate.toString(), finishDate.toString(), modelName));
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
                        Timestamp reservedStartDateTime = Timestamp.valueOf(reserved.getStartDateTime());
                        Timestamp reservedEndDateTime = Timestamp.valueOf(reserved.getEndDateTime());

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
                request.setAttribute("availableCarModels", availableCarModels);
                request.setAttribute("selectedDate", selectedDate);
                request.setAttribute("stationId", stationId);
                request.setAttribute("carCodes", carCodes);
                request.setAttribute("carImages", carImages);
                request.setAttribute("carModels", carModels);
                request.setAttribute("stationName", stationName);

                // 結果を出力
                System.out.println("combinedList:"+combinedList);
                System.out.println("Selected Date: " + selectedDate);
                System.out.println("Start Time: " + startTimeHour + ":" + startTimeMinute);
                System.out.println("Station ID: " + stationId);
                System.out.println("Station Name: " + stationName);
                System.out.println("Car Codes: " + carCodes);
                System.out.println("Car Models: " + carModels);
                System.out.println("Car Images: " + carImages);
                System.out.println("Available Car Models: " + availableCarModels);
                path = "P57.jsp";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }
}
