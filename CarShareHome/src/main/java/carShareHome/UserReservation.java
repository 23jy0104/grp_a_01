package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReservationDAO;
import model.Reservation;

/**
 * Servlet implementation class UserReservation
 */
@WebServlet("/UserReservation")
public class UserReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserReservation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String customerName = request.getParameter("customerName");
        String customerId = null; // 初期化

        // customerNameがnullまたは空の場合の処理
        if (customerName == null || customerName.isEmpty()) {
            request.setAttribute("errorMessage", "顧客名が指定されていません。");
            request.getRequestDispatcher("P74.jsp").forward(request, response);
            return;
        }

        // customerIdをデータベースから取得
        customerId = getCustomerIdByName(customerName);

        // customerIdがnullの場合の処理
        if (customerId == null || customerId.isEmpty()) {
            request.setAttribute("errorMessage", "指定された顧客名に該当するIDが見つかりません。");
            request.getRequestDispatcher("P65.jsp").forward(request, response);
            return;
        }

        // ReservationDAOのインスタンスを作成
        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> rireki =reservationDAO.Reservationkakunin(customerId);
        
       
        request.setAttribute("customerId", customerId);
        request.setAttribute("customerName", customerName);
        request.setAttribute("rireki", rireki);
        System.out.println(rireki);
        request.getRequestDispatcher("P65.jsp").forward(request, response);
        
        reservationDAO.connectionClose();
	}
	private String getCustomerIdByName(String customerName) {
        String customerId = null;
        String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
        String user = "23jya01";
        String pass = "23jya01";
        
        String sql = "SELECT customer_id FROM Customer WHERE customer_name = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, customerName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                customerId = rs.getString("customer_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return customerId;
    }

}
