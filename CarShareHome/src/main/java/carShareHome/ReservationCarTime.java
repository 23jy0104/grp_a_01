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

@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String date =request.getParameter("selectedDate");
        String carCode =request.getParameter("carCode");
        String stationId =request.getParameter("stationId");
        String path ="";
        try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://10.64.144.5:3306/23jya01?useUnicode=true&characterEncoding=UTF-8";
		    String user = "23jya01";
		    String pass = "23jya01";
		    
		    try {
				Connection con =DriverManager.getConnection(url,user,pass);
				String sql = "SELECT start_date, stop_date, r.car_code, k.station_id "
				           + "FROM Reservation r "
				           + "INNER JOIN Car_db car ON r.car_code = car.car_code "
				           + "INNER JOIN Keybox k ON k.car_code = r.car_code "
				           + "INNER JOIN Station s ON s.station_id = k.station_id "
				           + "WHERE start_date >= ? AND stop_date< ?";

				PreparedStatement pstmt =con.prepareStatement(sql);
				pstmt.setString(1, date+"00:00:00"); // 開始日
				pstmt.setString(2, date + " 23:59:59"); // 終了日
				System.out.println("ここまでは来てるよ！");
				ResultSet rs =pstmt.executeQuery();
				if(rs.next()) {
					request.getSession().setAttribute("stationId", stationId);
					request.getSession().setAttribute("carCode", carCode);
					request.getSession().setAttribute("start_Date", date);
					System.out.println("おめでとー！");
					path ="P59.jsp";
				}else {
					System.out.println("ここは来ちゃだめだよ");
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
        RequestDispatcher rd = request.getRequestDispatcher(path);
	    rd.forward(request, response);
    }
}
