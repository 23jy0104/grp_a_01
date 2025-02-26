package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationOK
 */
@WebServlet("/ReservationOK")
public class ReservationOK extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String startDateString =request.getParameter("startDate");
		String startTimeHour =request.getParameter("startTimeHour");
		String startTimeMinute=request.getParameter("startTimeHout");
		String EndDateString =request.getParameter("EndDate");
		String EndTimeHour =request.getParameter("EndTimeHour");
		String EndTimeMinute =request.getParameter("EndTimeMinute");
		
		String stationId =request.getParameter("stationId");
		String carCode =request.getParameter("carCode");
		String img =request.getParameter("img");
		String stationName =request.getParameter("stationName");
		String modelName =request.getParameter("modelName");
		String number =request.getParameter("number");
		
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		
		String path ="";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user = "23jya01";
			final String pass = "23jya01";
			 String sql = "SELECT start_date, stop_date FROM reservation r "
                     + "INNER JOIN keybox k ON k.car_code = r.car_code "
                     + "WHERE k.car_code = ? AND k.station_id = ? "
                     + "AND finish_date IS NULL "
                     + "AND (start_date < ? AND stop_date > ?)";
			String startDate =startDateString+" "+startTimeHour+" "+startTimeMinute;
			String endDate =EndDateString+" "+EndTimeHour+" "+EndTimeMinute+" ";
			con =DriverManager.getConnection(url, user, pass);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			pstmt.setString(1, carCode);
			pstmt.setString(2, stationId);
			pstmt.setTimestamp(3, Timestamp.valueOf(endDate)); // 終了日時を渡す
            pstmt.setTimestamp(4, Timestamp.valueOf(startDate)); // 開始日時を渡す
            if (rs.next()) {
                // 予約が重複している場合
                request.setAttribute("errorMessage", "予約が重複しています。");
                request.getRequestDispatcher("P84.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("startDate", startDate);
                request.getSession().setAttribute("endDate", endDate);
                request.getSession().setAttribute("stationId", stationId);
                request.getSession().setAttribute("carCode", carCode);
                request.getSession().setAttribute("img",img);
                request.getSession().setAttribute("stationName", stationName);
                request.getSession().setAttribute("modelName", modelName);
                request.getSession().setAttribute("number", number);
                
                path ="DiscountCalculatorServlet";
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
		RequestDispatcher rd =request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
