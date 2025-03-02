package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DiscountDao;
import model.Discount;

@WebServlet("/DiscountCalculatorServlet")
public class DiscountCalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int BASE_RATE_PER_15_MINUTES = 440; // 15分あたりの基本料金
    private static final int INSURANCE_FEE = 550; // 保険料金

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startTimestamp = (String)request.getSession().getAttribute("startDate");
        String endTimestamp = (String)request.getSession().getAttribute("endDate");
        // 時間の計算
        long durationInMillis = calculateDuration(startTimestamp, endTimestamp);
        int durationHours = (int) (durationInMillis / (1000 * 60 * 60)); // ミリ秒を時間に変換

        // 割引情報を取得
        DiscountDao discountDao = new DiscountDao();
        List<Discount> discounts = discountDao.getDiscounts();
        discountDao.connectionClose(); // 接続を閉じる

        // 料金計算
        int totalCost = calculateTotalCost(durationHours, discounts);
        // 結果をリクエストに設定
        request.getSession().setAttribute("totalCost", totalCost);
        String path ="P63.jsp"; // 結果をresult.jspにフォワード
        RequestDispatcher rd =request.getRequestDispatcher(path);
		rd.forward(request, response);

    }

    private long calculateDuration(String startTimestamp, String endTimestamp) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startDate = format.parse(startTimestamp);
            Date endDate = format.parse(endTimestamp);
            return endDate.getTime() - startDate.getTime(); // ミリ秒単位の差
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int calculateTotalCost(int durationHours, List<Discount> discounts) {
        // 基本料金の計算
        int basicCost = (durationHours * 60 / 15) * BASE_RATE_PER_15_MINUTES; // 15分単位で計算
        int insuranceCost = INSURANCE_FEE; // 保険料金は固定

        // 割引の計算
        int discount = calculateDiscount(durationHours, discounts);

        // 合計料金の計算
        int totalCost = basicCost + insuranceCost - discount;
        return totalCost > 0 ? totalCost : 0; // 料金が負にならないように
    }

    private int calculateDiscount(int durationHours, List<Discount> discounts) {
        int totalDiscount = 0;

        for (Discount discount : discounts) {
            if (durationHours >= discount.getTime()) {
                totalDiscount  = discount.getDiscountPrice();
            }
        }

        return totalDiscount;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        
    	String startDate =request.getParameter("startDate");
        String startTimeHour =request.getParameter("startTimeHour");
        String startTimeMinute =request.getParameter("startTimeMinute");
        String[] startdateParts = startDate.split("-");
        String startyear = startdateParts[0];
        String startmonth = startdateParts[1];
        String startday = startdateParts[2];       
        String startformattedMonth = String.format("%02d", Integer.parseInt(startmonth));
        String startformattedDate = startyear + "-" + startformattedMonth + "-" + startday;
    	String startTimestamp =startformattedDate +" "+startTimeHour +":"+startTimeMinute+":00";
        
    	
    	String EndDate =request.getParameter("EndDate");
    	String EndTimeHour =request.getParameter("EndTimeHour");
    	String EndTimeMinute =request.getParameter("EndTimeMinute");
    	String[] EnddateParts = EndDate.split("-");
        String Endyear = EnddateParts[0];
        String Endmonth = EnddateParts[1];
        String Endday = EnddateParts[2];        
        String endformattedMonth = String.format("%02d", Integer.parseInt(Endmonth));
        String endformattedDate = Endyear + "-" + endformattedMonth + "-" + Endday;
        
    	String endTimestamp = endformattedDate+" "+EndTimeHour+":"+EndTimeMinute+":00";
    	
        String carType =request.getParameter("carType");
        String stationId = (String) request.getSession().getAttribute("stationId");
        String stationName =request.getParameter("stationName");
        String stationData =request.getParameter("stationData");
		
		String path ="";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user = "23jya01";
			final String pass = "23jya01";
			
			
			
			 String sql = "SELECT k.car_code, model_name, station_name, car_img, number "
		                + "FROM keybox k "
		                + "INNER JOIN car_db car ON car.car_code = k.car_code "
		                + "INNER JOIN model m ON m.model_id = car.model_id "
		                + "INNER JOIN station s ON s.station_id = k.station_id "
		                + "WHERE s.station_id = ? AND m.model_name = ? AND k.car_code NOT IN ("
		                + "SELECT r.car_code FROM reservation r "
		                + "WHERE (r.stop_date > ? AND r.start_date < ?) "
		                + "AND r.finish_date IS NULL)"; // finish_dateがNULLであることを確認

		        Connection con = DriverManager.getConnection(url, user, pass);
		        PreparedStatement pstmt = con.prepareStatement(sql);
		        pstmt.setString(1, stationId);
		        pstmt.setString(2, carType); // carTypeを条件に追加
		        pstmt.setTimestamp(3, Timestamp.valueOf(startTimestamp));
		        pstmt.setTimestamp(4, Timestamp.valueOf(endTimestamp));
		        ResultSet rs = pstmt.executeQuery();
			 if (rs.next()) {
				    // 予約が可能な場合の処理
				    request.getSession().setAttribute("startDate", startTimestamp);
				    request.getSession().setAttribute("endDate", endTimestamp);
				    request.getSession().setAttribute("carCode", rs.getString("car_code"));
				    request.getSession().setAttribute("img", rs.getString("car_img"));
				    request.getSession().setAttribute("modelName", carType);
				    request.getSession().setAttribute("number", rs.getString("number"));
				    path = "P63.jsp"; // 予約内容確認画面
				} else {
				    // 予約が重複している場合
				    request.setAttribute("errorMessage", "指定できない時間が含まれています。再度空き状況を確認してください。");
				    String detailUrl = "P56.jsp?stationid=" + stationId + "&stationname=" + stationName + "&stationdata=" + stationData;

				    path = detailUrl; // エラーメッセージを表示する画面
				}	   
			 
	    } catch (ClassNotFoundException |SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		 
        // 時間の計算
        long durationInMillis = calculateDuration(startTimestamp, endTimestamp);
        int durationHours = (int) (durationInMillis / (1000 * 60 * 60)); // ミリ秒を時間に変換

        // 割引情報を取得
        DiscountDao discountDao = new DiscountDao();
        List<Discount> discounts = discountDao.getDiscounts();
        discountDao.connectionClose(); // 接続を閉じる

        // 料金計算
        int totalCost = calculateTotalCost(durationHours, discounts);
        // 結果をリクエストに設定
        request.getSession().setAttribute("totalCost", totalCost);
        
        RequestDispatcher rd =request.getRequestDispatcher(path);
		rd.forward(request, response);

    }
}
