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

import model.CarData;

@WebServlet("/CarAvailabilityServlet")
public class CarAvailabilityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String path = "P57.jsp"; // 遷移先のデフォルトパス
        String selectedDate = request.getParameter("selectedDate");
        String startTimeHour = request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        String stationId = request.getParameter("stationId");
        String stationName = request.getParameter("stationName");
        String stationData = request.getParameter("stationData");
        
        String selectDateTime =selectedDate +" "+startTimeHour +":"+startTimeMinute+" 00";
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user = "23jya01";
			final String pass = "23jya01";
			String consql = "SELECT start_date,stop_date "
					+ "FROM keybox k "
					+ "INNER JOIN reservation r ON r.car_code =k.car_code "
					+ "WHERE station_id = ? and finish_date IS null and start_date <= ? ";
			Connection con = DriverManager.getConnection(url, user, pass);
			PreparedStatement pstmt = con.prepareStatement(consql);
			pstmt.setString(1, stationId);
			pstmt.setString(2, selectDateTime);
			ResultSet rs = pstmt.executeQuery();
			
			List<CarData> availableCars = new ArrayList<>();
			while(rs.next()) {
				CarData car =new CarData();
				car.setCarCode(rs.getString("car_code"));
				car.setModelName(rs.getString("model_name"));
				car.setCarImage(rs.getString("car_img"));
				availableCars.add(car);
			}
			
			
			
			
			request.setAttribute("availableCars", availableCars);
			request.setAttribute("stationName", stationName);
			request.setAttribute("stationData", stationData);
			RequestDispatcher rd =request.getRequestDispatcher(path);
			rd.forward(request, response);
			
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        
    }
}
