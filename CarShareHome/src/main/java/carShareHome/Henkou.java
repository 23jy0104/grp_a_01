package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Reservation;
import model.Reservation.ReservationManager;

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
				String sql = "SELECT reservation_id, s.station_id, station_name, start_date, stop_date, model_name, number, r.car_code,price "
			             + "FROM Reservation r "
			             + "INNER JOIN car_db car ON car.car_code = r.car_code "
			             + "INNER JOIN keybox k ON k.car_code = r.car_code "
			             + "INNER JOIN model m ON m.model_id = car.model_id "
			             + "INNER JOIN station s ON s.station_id = k.station_id "
			             + "WHERE reservation_id = ?;";

				PreparedStatement ptsmt = connection.prepareStatement(sql);
				ptsmt.setString(1, reservationId);
				ResultSet rs =ptsmt.executeQuery();
				
				if(rs.next()) {
					request.setAttribute("carCode",rs.getString("car_code"));
					request.setAttribute("reservationId",rs.getString("reservation_id"));
					request.setAttribute("stationId", rs.getString("station_id"));
					request.setAttribute("stationName", rs.getString("station_name"));
					request.setAttribute("startDate", rs.getString("start_date"));
					request.setAttribute("stopDate", rs.getString("stop_date"));
					request.setAttribute("modelName",rs.getString("model_name"));
					request.setAttribute("number", rs.getString("number"));
					request.setAttribute("price", rs.getInt("price"));
					
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        String reservationId =request.getParameter("reservationId");
        String stationId =request.getParameter("stationId");
        String stationName =request.getParameter("stationName");
        
        String carCode =request.getParameter("carCode");
        String customerId =request.getParameter("customerId");
        String modelName =request.getParameter("modelName");
        String number =request.getParameter("number");
        String priceParam = request.getParameter("price");
        int price = Integer.parseInt(priceParam); // 必要に応じて型変換
        
        String startDateTime =request.getParameter("startDate");
        String startTimeHour =request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        
        String endDateTime =request.getParameter("endDate");
        String endTimeHour =request.getParameter("endTimeHour");
        String endTimeMinute =request.getParameter("endTimeMinute");
                
        String startDate =startDateTime+" "+ startTimeHour +":"+startTimeMinute+":00";
        String endDate =endDateTime+" "+endTimeHour+":"+endTimeMinute+":00";
        
        Integer totalPrice = (Integer) request.getAttribute("totalPrice");
        if (totalPrice != null) {
            request.setAttribute("totalPrice", totalPrice);
        }
        
        String path="";
			
		 List<Reservation> reservations = new ArrayList<Reservation>();// ここで予約リストを取得する処理を実装
			
		 Reservation.ReservationManager reservationManager = new ReservationManager(reservations);

			
			boolean isAvailable = ReservationManager.isTimeSlotAvailableForOtherCustomers(customerId, carCode, startDateTime, endDateTime);
		    
		    if (isAvailable) {
		    	request.setAttribute("stationName", stationName);
	        	request.setAttribute("reservationId", reservationId);
				request.setAttribute("startDate", startDate);
				request.setAttribute("stopDate", endDate);
				request.setAttribute("modelName",modelName);
				request.setAttribute("number", number);
				path ="P70.jsp";
		    	String discountCalculatorUrl = "DiscountHenkou?startDate=" + startDate 
                         + "&endDate=" + endDate ;
		    	response.sendRedirect(discountCalculatorUrl);
				return;
		    } else {
		    	 request.setAttribute("errorMessage", "指定された時間は他の顧客によって予約されています。");
		    	 path="P67.jsp";
		    }
		
		    RequestDispatcher rd =request.getRequestDispatcher(path);
		    rd.forward(request, response);
		
        
	
	}
	
}
