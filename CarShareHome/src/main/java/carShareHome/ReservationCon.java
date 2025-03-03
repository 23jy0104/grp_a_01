package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationOK
 */
@WebServlet("/ReservationCon")
public class ReservationCon extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String startDate =request.getParameter("selectedDate");
		String stationId =request.getParameter("stationId");
		String carCode =request.getParameter("carCode");
		String img =request.getParameter("img");
		String modelName=request.getParameter("modelName");
		
		String path ="";
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs =null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url ="jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user ="23jya01";
			final String pass ="23jya01";
			
			String sql ="SELECT station_name, number, model_name "
					+ "FROM keybox k "
					+ "INNER JOIN car_db car ON car.car_code = k.car_code "
					+ "INNER JOIN model m ON m.model_id = car.model_id "
					+ "INNER JOIN station s ON s.station_id = k.station_id "
					+ "WHERE k.station_id = ? AND k.car_code = ?";

			con =DriverManager.getConnection(url, user, pass);
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, stationId);
			pstmt.setString(2,carCode);
			rs =pstmt.executeQuery();
			
			if(rs.next()) {
				request.getSession().setAttribute("startDate", startDate);
				request.getSession().setAttribute("stationId", stationId);
				request.getSession().setAttribute("carCode",carCode);
				request.getSession().setAttribute("stationName", rs.getString("station_name"));
				request.getSession().setAttribute("img", img);
				request.getSession().setAttribute("number",rs.getString("number"));
				request.getSession().setAttribute("modelName", modelName);
				path ="P84.jsp";
			}		
			RequestDispatcher rd =request.getRequestDispatcher(path);
			rd.forward(request, response);
			} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
