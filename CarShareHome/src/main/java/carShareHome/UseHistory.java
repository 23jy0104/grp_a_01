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

@WebServlet("/UseHistory")
public class UseHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerName = request.getParameter("customerName");

        System.out.println("customerName: " + customerName);

        // customerIdをデータベースから取得
        String customerId = getCustomerIdByName(customerName);

        // customerIdがnullの場合の処理
        if (customerId == null || customerId.isEmpty()) {
            System.out.println("Error: customerId is null or empty.");
            response.sendRedirect("error.jsp"); // エラーページにリダイレクト
            return;
        }

        // ReservationDAOのインスタンスを作成
        ReservationDAO reservationDAO = new ReservationDAO();

        // 利用履歴を取得
        List<Reservation> usedReservations = reservationDAO.getAllReservations(customerId); // customerIdを引数として渡す

        // リクエスト属性に設定
        request.setAttribute("customerId", customerId);
        request.setAttribute("customerName", customerName);
        request.setAttribute("usedReservations", usedReservations);

        // デバッグ用のログ
        System.out.println("取得した予約数: " + (usedReservations != null ? usedReservations.size() : 0));

        // JSPにフォワード
        request.getRequestDispatcher("P74.jsp").forward(request, response);

        // 最後に接続を閉じる
        reservationDAO.connectionClose();
    }

    private String getCustomerIdByName(String customerName) {
        String customerId = null;
        String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
        String user = "23jya01";
        String pass = "23jya01";
        
        String sql = "SELECT customer_id FROM Customer WHERE customer_name = ?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(url, user, pass);
                 PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, customerName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    customerId = rs.getString("customer_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return customerId;
    }
}
