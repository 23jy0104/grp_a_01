package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationCar
 */
@WebServlet("/ReservationCar")
public class ReservationCar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationCar() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String stationName=request.getParameter("stationname");
        String stationData=request.getParameter("stationdata");
        String carType =request.getParameter("carType");
        try {
			Class.forName("com.mysql.Driver");
			final String url ="jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user="23jya01";
			final String pass ="23jya01";
			Connection connection = DriverManager.getConnection(url,user, pass);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        
        
	}

}
