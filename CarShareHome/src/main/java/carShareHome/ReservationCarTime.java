package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CarData;
import model.Maker;
import model.Model;

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
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    request.setCharacterEncoding("UTF-8");
    	    response.setContentType("text/html; charset=UTF-8"); 

    	    String startdate = request.getParameter("startCalendar");
    	    String starttime = request.getParameter("startTime");
    	    String startDate = startdate + " " + starttime;
    	    String enddate = request.getParameter("endCalendar");
    	    String endtime = request.getParameter("endTime");
    	    String endDate = enddate + " " + endtime;
    	    String stationId = request.getParameter("stationId");
    	    String modelId = request.getParameter("modelId"); // 車種IDを取得

    	    Connection con = null;
    	    PreparedStatement ps = null;
    	    ResultSet rs = null;
    	    
    	    try {
    	        Class.forName("com.mysql.jdbc.Driver");
    	        String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
    	        String user = "23jya01";
    	        String pass = "23jya01";

    	        // データベース接続
    	        con = DriverManager.getConnection(url, user, pass);
    	        
    	        String sql = "SELECT c.car_code, c.model_year, c.number, m.maker_id, m.maker_name, mo.model_id, mo.model_name " +
    	                     "FROM car_db c " +
    	                     "JOIN maker m ON c.maker_id = m.maker_id " +
    	                     "JOIN model mo ON c.model_id = mo.model_id " +
    	                     "JOIN keybox k ON c.car_code = k.car_code " +
    	                     "WHERE k.station_id = ? " +
    	                     "AND c.model_id = ? " +
    	                     "AND c.car_code NOT IN ( " +
    	                     "    SELECT car_code " +
    	                     "    FROM reservation " +
    	                     "    WHERE (start_date < ? AND stop_date > ?) " +
    	                     ");";

    	        ps = con.prepareStatement(sql);
    	        ps.setString(1, stationId); // ステーションID
    	        ps.setString(2, modelId); // 車種ID
    	        ps.setString(3, endDate); // 終了日時
    	        ps.setString(4, startDate); // 開始日時

    	        rs = ps.executeQuery();

    	        // 結果をリストに格納
    	        List<CarData> availableCars = new ArrayList<>();
    	        while (rs.next()) {
    	            CarData car = new CarData();
    	            car.setCarCode(rs.getString("car_code"));
    	            car.setModelYear(rs.getString("model_year"));
    	            car.setCarNumber(rs.getString("number"));

    	            // Makerオブジェクトの作成
    	            Maker maker = new Maker();
    	            maker.setMakerID(rs.getString("maker_id"));
    	            maker.setMakerName(rs.getString("maker_name"));
    	            car.setMker(maker);

    	            // Modelオブジェクトの作成
    	            Model model = new Model();
    	            model.setModelId(rs.getString("model_id"));
    	            model.setModelName(rs.getString("model_name"));
    	            car.setModel(model);

    	            availableCars.add(car);
    	        }

    	        // リクエスト属性に結果を設定
    	        request.setAttribute("availableCars", availableCars);
    	        
    	        // JSPにフォワード
    	        request.getRequestDispatcher("P57.jsp").forward(request, response);
    	    } catch (ClassNotFoundException | SQLException e) {
    	        e.printStackTrace();
    	    } finally {
    	        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
    	        if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
    	        if (con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
    	    }
    	}

}
