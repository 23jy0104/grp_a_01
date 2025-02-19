package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.TimeTableAPI;
import model.CarData;
import model.Maker;
import model.Model;

@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String startdate = request.getParameter("startCalendar");
        String starttime = request.getParameter("startTime");
        String startDate = startdate + " " + starttime;
        String enddate = request.getParameter("endCalendar");
        String endtime = request.getParameter("endTime");
        String endDate = enddate + " " + endtime;
        String stationId = request.getParameter("stationId");
        String modelId = request.getParameter("modelId"); // 車種IDを取得

        List<CarData> availableCars = new ArrayList<>();
        List<Timestamp[]> availableSlots = new ArrayList<>();

        try (Connection con = getConnection()) {
            // 空き車両を取得
            String sql = "SELECT c.car_code, c.model_year, c.number, m.maker_id, m.maker_name, mo.model_id, mo.model_name "
                       + "FROM car_db c "
                       + "JOIN maker m ON c.maker_id = m.maker_id "
                       + "JOIN model mo ON c.model_id = mo.model_id "
                       + "JOIN keybox k ON c.car_code = k.car_code "
                       + "WHERE k.station_id = ? "
                       + "AND c.model_id = ? "
                       + "AND c.car_code NOT IN ( "
                       + "    SELECT car_code "
                       + "    FROM reservation "
                       + "    WHERE (start_date < ? AND stop_date > ?) "
                       + ");";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, stationId);
                ps.setString(2, modelId);
                ps.setString(3, endDate);
                ps.setString(4, startDate);

                try (ResultSet rs = ps.executeQuery()) {
                    // 結果をリストに格納
                    while (rs.next()) {
                        CarData car = new CarData();
                        car.setCarCode(rs.getString("car_code"));
                        car.setModelYear(rs.getString("model_year"));
                        car.setCarNumber(rs.getString("number"));

                        // Makerオブジェクトの作成
                        Maker maker = new Maker();
                        maker.setMakerID(rs.getString("maker_id"));
                        maker.setMakerName(rs.getString("maker_name"));
                        car.setMaker(maker);

                        // Modelオブジェクトの作成
                        Model model = new Model();
                        model.setModelId(rs.getString("model_id"));
                        model.setModelName(rs.getString("model_name"));
                        car.setModel(model);

                        availableCars.add(car);
                    }
                }
            }

            // 空き時間をAPIから取得
            availableSlots = TimeTableAPI.getAvailableSlotsFromAPI(startDate, endDate);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "データベースエラーが発生しました。");
        }

        // リクエスト属性に結果を設定
        request.setAttribute("availableCars", availableCars);
        request.setAttribute("availableSlots", availableSlots);

        // JSPにフォワード
        request.getRequestDispatcher("P59.jsp").forward(request, response);
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
        String user = "23jya01";
        String pass = "23jya01";
        return DriverManager.getConnection(url, user, pass);
    }
}
