package carShareHome;

import java.io.IOException;
<<<<<<< HEAD

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginSql;
import model.LoginCustomer;
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String user_id =request.getParameter("id");
		String password =request.getParameter("password");
		
		LoginSql loginsql = new LoginSql();
		LoginCustomer login =loginsql.check(user_id,password);
		
		if(login.isLogin_flag()) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("P53.jsp");
			dispatcher.forward(request, response);
		}else {
			RequestDispatcher dispatcher= request.getRequestDispatcher("P29.jsp");
			dispatcher.forward(request, response);
		}
		
=======
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
		String email=request.getParameter("email");
		String password=request.getParameter("customerPassword");
		
		String DBurl="jdbd:mysql://10.64.144.5:3306/23jya01";
		String DBuser="23jya01";
		String DBpasswordData="23jya01";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con =DriverManager.getConnection(DBurl, DBuser, DBpasswordData);
			
			String SQL ="select * from Customer where email =? AND customerPassword = ?";
			PreparedStatement statement = con.prepareStatement(SQL);
			statement.setString(1, email);
			statement.setString(2, password);
			
			ResultSet set =statement.executeQuery();
			
			if(set.next()) {
				request.getRequestDispatcher("").forward(request, response);
			}else {
				response.sendRedirect("login.jsp?error=true");
			}
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
>>>>>>> branch 'main' of https://github.com/23jy0104/grp_a_01.git
	}

}
