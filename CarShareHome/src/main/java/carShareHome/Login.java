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

import model.PasswordHasher;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String email =request.getParameter("email");
		String password =request.getParameter("customerpassword");
		String hashedPassword =PasswordHasher.hashPassword(password);
		String path ="";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String url ="jdbc:mysql://10.64.144.5:3306/23jya01";
			final String user ="23jya01";
			final String pass ="23jya01";
			
			String sql = "SELECT customer_name, customer_kana, e_mail, customer_password, post_code, customer_address, license_date, tell_number FROM Customer WHERE e_mail = ? AND customer_password = ?";

			try(Connection con =DriverManager.getConnection(url,user,pass);
					PreparedStatement pstmt =con.prepareStatement(sql)) {
				pstmt.setString(1, email);
				pstmt.setString(2, hashedPassword);
				ResultSet rs =pstmt.executeQuery();
				if(rs.next()) {
					request.getSession().setAttribute("customerName", rs.getString("customer_name"));
					request.getSession().setAttribute("customerKana", rs.getString("customer_kana"));
					request.getSession().setAttribute("email", rs.getString("e_mail"));
					request.getSession().setAttribute("postCode", rs.getString("post_code"));
					request.getSession().setAttribute("customerAddress", rs.getString("customer_address"));
					request.getSession().setAttribute("licenseDate", rs.getDate("license_date"));
					request.getSession().setAttribute("tellNumber", rs.getString("tell_number"));
					path ="P53.jsp";
				}else {
					request.setAttribute("loginfalse", "メールアドレスまたはパスワードが違います。");
					path ="P29.jsp";
				}
			} catch ( SQLException e) {
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
