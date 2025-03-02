package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarData;

@WebServlet("/CarAvailabilityServlet")
public class CarAvailabilityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String path = ""; // 遷移先のデフォルトパス
        String selectedDate = request.getParameter("selectedDate");
        String startTimeHour = request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        String stationId = request.getParameter("stationId");
        String stationName = request.getParameter("stationName");
        String stationData = request.getParameter("stationData");
        String[] dateParts = selectedDate.split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];

        // 月を2桁にフォーマット
        String formattedMonth = String.format("%02d", Integer.parseInt(month));

        // 新しい日付文字列を作成
        String formattedDate = year + "-" + formattedMonth + "-" + day;
        
        String selectDateTime =formattedDate +" "+startTimeHour +":"+startTimeMinute+":00";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(selectDateTime, formatter);
        // 6時間後の時間を取得
        LocalDateTime sixHoursLater = dateTime.plusHours(6);
        String sixHoursLaterString = sixHoursLater.format(formatter);
        List<CarData> carData =new ArrayList<>();
        try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user = "23jya01";
			final String pass = "23jya01";
		
			
			String sql = "SELECT k.car_code, model_name, station_name, car_img "
		            + "FROM keybox k "
		            + "LEFT JOIN reservation r ON r.car_code = k.car_code AND (r.stop_date > ? AND r.start_date < ?) "
		            + "INNER JOIN car_db car ON car.car_code = k.car_code "
		            + "INNER JOIN model m ON m.model_id = car.model_id "
		            + "INNER JOIN station s ON s.station_id = k.station_id "
		            + "WHERE s.station_id = ? AND r.car_code IS NULL";
			try {
				Connection con = DriverManager.getConnection(url, user, pass);
				try(PreparedStatement pstmt =con.prepareStatement(sql)) {
					pstmt.setString(1, selectDateTime);
					pstmt.setString(2, sixHoursLaterString);
					pstmt.setString(3,stationId);
					
					try(ResultSet rs = pstmt.executeQuery()) {
						while(rs.next()) {
							CarData car =new CarData();
							car.setCarCode(rs.getString("car_code"));
							car.setModelName(rs.getString("model_name"));
							car.setCarImage(rs.getString("car_img"));
							carData.add(car);
						}
						request.getSession().setAttribute("stationId", stationId);
						request.getSession().setAttribute("stationName", stationName);
						request.getSession().setAttribute("stationData", stationData);
						request.setAttribute("carData",carData);
						request.setAttribute("stopDate",sixHoursLaterString);
						request.setAttribute("selectedDate",selectedDate);
						request.setAttribute("startTimeHour",startTimeHour);
						request.setAttribute("startTimeMinute", startTimeMinute);
						path ="P57.jsp";
					}
				}
				RequestDispatcher rd =request.getRequestDispatcher(path);
				rd.forward(request, response);
				
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        
    }
}
