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
 * Servlet implementation class EmailUpdate
 */
@WebServlet("/EmailUpdate")
public class EmailUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
	     response.setContentType("text/html; charset=UTF-8");
	     String newEmail =request.getParameter("email");
	     String customerId = request.getParameter("customerId");
	     
	     Connection connection = null;
	     PreparedStatement preparedStatement = null;
	     
	     try {
	            // データベース接続情報
	            String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01?useUnicode=true&characterEncoding=UTF-8";
	            String dbUser = "23jya01";
	            String dbPassword = "23jya01";

	            // データベース接続
	            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
	            String sql = "UPDATE customer SET e_mail = ? WHERE customer_id = ?;";
	            preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, newEmail);
	            preparedStatement.setString(2, customerId);
	            preparedStatement.executeUpdate();
	            preparedStatement.executeUpdate();
	            // 更新した情報をリクエスト属性に設定
	            request.setAttribute("email", newEmail);
	            request.getSession().setAttribute("email", newEmail);
	            
	            // P76.jspにフォワード
	            RequestDispatcher rd = request.getRequestDispatcher("P76.jsp");
	            rd.forward(request, response);
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // エラーハンドリングの処理
	            request.setAttribute("errorMessage", "データベースへの登録に失敗しました。");
	            RequestDispatcher rd = request.getRequestDispatcher("P79.jsp");
	            rd.forward(request, response);
	        } finally {
	            // リソースの解放
	            try {
	                if (preparedStatement != null) preparedStatement.close();
	                if (connection != null) connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	}

}
