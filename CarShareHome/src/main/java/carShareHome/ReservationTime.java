package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationTime
 */
@WebServlet("/ReservationTime")
public class ReservationTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationTime() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String stationId= request.getParameter("stationid");
        String stationName=request.getParameter("stationname");
        String startDate =request.getParameter("startdate");
        String stopDate =request.getParameter("stopdate");
        String sql = "SELECT c.car_code, c.model_year, c.number, m.maker_name, mo.model_name\r\n"
        		+ "FROM car_db c\r\n"
        		+ "JOIN maker m ON c.maker_id = m.maker_id\r\n"
        		+ "JOIN model mo ON c.model_id = mo.model_id\r\n"
        		+ "JOIN keybox k ON c.car_code = k.car_code\r\n"
        		+ "WHERE k.station_id = ?"
        		+ "	c.car_code NOT IN"
        		+ "    SELECT car_code"
        		+ "    FROM reservation"
        		+ "    WHERE (start_date < ? AND stop_date > ? AND );";
        try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url ="jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user ="23jya01";
			final String pass ="23jya01";
			Connection con =DriverManager.getConnection(url, user, pass);
			Class.forName("com.mysql.jdbc.Driver");
			PreparedStatement pstmt =con.prepareStatement(sql);
			pstmt.setString(1, stationId);
			pstmt.setString(2, startDate);
			pstmt.setString(3, stopDate);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
