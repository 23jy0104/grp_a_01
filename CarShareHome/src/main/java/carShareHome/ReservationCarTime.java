package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationCarTime
 */
@WebServlet("/ReservationCarTime")
public class ReservationCarTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationCarTime() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8"); 
        String startdate =request.getParameter("startCalender");
        String starttime =request.getParameter("startTime");
        String startDate =startdate+" "+starttime;
        String enddate =request.getParameter("endCalender");
        String endtime =request.getParameter("endTime");
        String endDate =enddate+" "+endtime;
        String stationid =request.getParameter("stationId");
        String makername =request.getParameter("mekerName");
        
        Connection con =null;
        PreparedStatement ps =null;
        ResultSet rs =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
			String user = "23jya01";
			String pass = "23jya01";

			// データベース接続
			con = DriverManager.getConnection(url, user, pass);
			
			String sql ="SELECT c.car_code, c.model_year, c.number, m.maker_name, mo.model_name\r\n"
					+ "FROM car_db c\r\n"
					+ "JOIN maker m ON c.maker_id = m.maker_id\r\n"
					+ "JOIN model mo ON c.model_id = mo.model_id\r\n"
					+ "JOIN keybox k ON c.car_code = k.car_code\r\n"
					+ "WHERE k.station_id = '選択したステーションID'\r\n"
					+ "  AND c.model_id = '選択した車種ID' /*ステーション+車種選択*/\r\n"
					+ "  AND c.car_code NOT IN (\r\n"
					+ "      SELECT car_code\r\n"
					+ "      FROM reservation\r\n"
					+ "      WHERE (start_date < '指定された終了日時' AND stop_date > '指定された開始日時')\r\n"
					+ "  );";
			
			ps =con.prepareStatement(sql);
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
