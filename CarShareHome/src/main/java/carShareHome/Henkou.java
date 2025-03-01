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
 * Servlet implementation class Henkou
 */
@WebServlet("/Henkou")
public class Henkou extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Henkou() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String reservationId =request.getParameter("reservationId");
        String path="";
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user = "23jya01";
			final String pass = "23jya01";
			
			try {
				Connection connection = DriverManager.getConnection(url, user, pass);
				String sql = "SELECT reservation_id,s.station_id,station_name,start_date,stop_date,model_name,number "
						+ "from Reservation r "
						+ "INNER JOIN car_db car ON car.car_code =r.car_code "
						+ "INNER JOIN keybox k ON k.car_code =r.car_code "
						+ "INNER JOIN model m ON m.model_id =car.model_id "
						+ "INNER JOIN station s ON s.station_id =k.station_id "
						+ "WHERE reservation_id = ?;";
				PreparedStatement ptsmt = connection.prepareStatement(sql);
				ptsmt.setString(1, reservationId);
				ResultSet rs =ptsmt.executeQuery();
				
				if(rs.next()) {
					request.setAttribute("reservationId",rs.getString("reservation_id"));
					request.setAttribute("stationId", rs.getString("station_id"));
					request.setAttribute("stationName", rs.getString("station_name"));
					request.setAttribute("startDate", rs.getString("start_date"));
					request.setAttribute("stopDate", rs.getString("stop_date"));
					request.setAttribute("modelName",rs.getString("model_name"));
					request.setAttribute("number", rs.getString("number"));
					
					path ="P67.jsp";
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
