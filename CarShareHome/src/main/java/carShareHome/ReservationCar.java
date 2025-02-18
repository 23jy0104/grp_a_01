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
        String stationId =request.getParameter("stationid");
        String stationName=request.getParameter("stationname");
        String carType =request.getParameter("carType");
        String path ="";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url ="jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user ="23jya01";
			final String pass ="23jya01";
			
			conn = DriverManager.getConnection(url, user, pass);
			String sql ="SELECT model_name,s.station_id,c.car_code,car_img"
					    +"FROM keybox k INNER JOIN station s ON k.station_id = s.station_id "
					           +"INNER JOIN reservation r ON r.car_code =k.car_code"
					           +"INNER JOIN customer cus ON r.customer_id = cus.customer_id "
					           +"INNER JOIN car_db car ON r.car_code =r.carcode "
					           +"INNER JOIN model m ON m.mode_id = car.model_id "
					    + "WHERE car_code =? ";
			pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, carType); // carTypeを使う場合
	        rs = pstmt.executeQuery();
	        if(rs.next()) {
	        	request.getSession().setAttribute("modelName", rs.getString("model_name"));
	        	request.getSession().setAttribute("stationid", rs.getString("station_id"));
	        	request.getSession().setAttribute("car_img", rs.getString("car_img"));
	        	path ="P59.jsp";
	        }
	        RequestDispatcher rd = request.getRequestDispatcher(path);
		    rd.forward(request, response);
		    
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
	        // リソースをクローズ
	        try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

}