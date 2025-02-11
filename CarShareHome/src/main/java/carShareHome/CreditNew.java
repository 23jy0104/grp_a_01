package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreditNew
 */
@WebServlet("/CreditNew")
public class CreditNew extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreditNew() {
        super();
        // TODO Auto-generated constructor stub
    }
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 顧客データを取得
        String customerName = (String) request.getAttribute("customerName");
        String customerKana = (String) request.getAttribute("customerKana");
        String email = (String) request.getAttribute("email");
        String tellNumber = (String) request.getAttribute("tellNumber");
        String postCode = (String) request.getAttribute("postCode");
        String customerAddress = (String) request.getAttribute("customerAddress");
        String licenseNumber = (String) request.getAttribute("licenseNumber");
        String licenseDate = (String) request.getAttribute("licenseDate");

        // クレジットカード情報を取得
        String creditNumber = request.getParameter("credit_number");
        String creditDate = request.getParameter("credittime");
        String securityCode = request.getParameter("security");

        // データベースに登録する処理
        try {
            // データベース接続情報
            String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01";
            String dbUser = "23jya01";
            String dbPassword = "23jya01";

            // データベース接続
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            String sql = "INSERT INTO customer (customer_name, customer_kana, email, tell_number, post_code, customer_address, license_number, license_date, credit_number, credit_date, security_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerKana);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, tellNumber);
            preparedStatement.setString(5, postCode);
            preparedStatement.setString(6, customerAddress);
            preparedStatement.setString(7, licenseNumber);
            preparedStatement.setString(8, licenseDate);
            preparedStatement.setString(9, creditNumber);
            preparedStatement.setString(10, creditDate);
            preparedStatement.setString(11, securityCode);

            // SQLを実行
            preparedStatement.executeUpdate();

            // リダイレクトやフォワード処理
            response.sendRedirect("P28.jsp"); // 成功時のページへリダイレクト
        } catch (SQLException e) {
            e.printStackTrace();
            // エラーハンドリングの処理
            request.setAttribute("errorMessage", "データベースへの登録に失敗しました。");
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }
	}

}
