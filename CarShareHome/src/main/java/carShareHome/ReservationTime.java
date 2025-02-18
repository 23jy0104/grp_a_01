package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
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
        String stationId = request.getParameter("stationid");
        String startDateStr = request.getParameter("startdate").replace("T", " ");
        String stopDateStr = request.getParameter("stopdate").replace("T", " ");
        
        // 日付のフォーマットを指定
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp startDate = null;
        Timestamp stopDate = null;
        
        try {
            // 文字列をTimestampに変換
            startDate = new Timestamp(sdf.parse(startDateStr).getTime());
            stopDate = new Timestamp(sdf.parse(stopDateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            // エラーハンドリングを追加することをお勧めします
        }
        System.out.println(startDate);
        System.out.println(stopDate);
        String path ="";
        try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url ="jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user ="23jya01";
			final String pass ="23jya01";
			Connection con =DriverManager.getConnection(url, user, pass);
<<<<<<< HEAD
			Class.forName("com.mysql.jdbc.Driver");
			String sql = "SELECT start_date, stop_date, cu.customer_id, ca.car_code, s.station_id, s.station_name, ca.car_img, cu.customer_name, station_data "
			           + " FROM reservation r "
			           + " JOIN car_db ca ON ca.car_code = r.car_code "
			           + " JOIN model m ON ca.model_id = m.model_id "
			           + " JOIN keybox k ON ca.car_code = k.car_code "
			           + " JOIN Station s ON s.station_id = k.station_id "
			           + " JOIN customer cu ON cu.customer_id = r.customer_id "
			           + " WHERE k.station_id = ? "
			           + " AND ca.car_code NOT IN ( "
			           + "     SELECT car_code "
			           + "     FROM reservation "
			           + "     WHERE (start_date < ? AND stop_date > ?) "
			           + " );";



=======
			 String sql = "SELECT r.start_date,r.stop_dater,r.customer_id,c.car_code,s.station_id,station_name,car_img,customer_name"
		        		+ "FROM reservation r"
		        		+ "JOIN cardb c ON c.car_code = r.car_code"
		        		+ "JOIN model m ON c.model_id = m.model_id"
		        		+ "JOIN keybox k ON c.car_code = k.car_code"
		        		+"JOIN Station s on s.station_id =k.station_id"
		        		+ "WHERE k.station_id = ?"
		        		+ "	c.car_code NOT IN("
		        		+ "    SELECT car_code"
		        		+ "    FROM reservation"
		        		+ "    WHERE (start_date < ? AND stop_date > ? AND ));";
>>>>>>> branch 'main' of https://github.com/23jy0104/grp_a_01.git
			PreparedStatement pstmt =con.prepareStatement(sql);
			pstmt.setString(1, stationId);
			pstmt.setTimestamp(2, startDate);
			pstmt.setTimestamp(3, stopDate);
			ResultSet rs =pstmt.executeQuery();
			if(rs.next()) {
				request.getSession().setAttribute("customerName", rs.getString("customer_name"));
				request.getSession().setAttribute("stationId", rs.getString("station_id"));
				request.getSession().setAttribute("stationName", rs.getString("station_name"));
				request.getSession().setAttribute("startDate", rs.getString("start_date"));
				request.getSession().setAttribute("stopDate", rs.getString("stop_date"));
				request.getSession().setAttribute("img", rs.getString("car_img"));
				request.getSession().setAttribute("code", rs.getString("car_code"));
				request.getSession().setAttribute("stationdata", rs.getString("station_data"));
				path ="P59.jsp";
			}else {
				path ="P56.jsp";
			}
			 RequestDispatcher rd = request.getRequestDispatcher(path);
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
