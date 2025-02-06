package carShareHome;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		LoginCustomer customer = new LoginCustomer();
		String email =request.getParameter("email");
		String password =request.getParameter("password");
		customer.setEmail(email);
		customer.setPassword(password);
		HttpSession session= request.getSession();
		session.setAttribute("customer", customer);
		
		LoginCustomer login =(LoginCustomer)session.getAttribute("customer");
		
	}

}
