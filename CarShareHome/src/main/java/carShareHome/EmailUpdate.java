package carShareHome;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Customer;

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
        String customerId =request.getParameter("customerId");
        String email =request.getParameter("email");
        if (validateInputs(email)) {
        	Customer customer = new Customer();
        		customer.setCustomerId(customerId);
                customer.setTellNumber(email);
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                RequestDispatcher rd=request.getRequestDispatcher("P88.jsp");
                rd.forward(request, response);
        } else {
            request.setAttribute("errorMessage", "入力データにエラーがあります。");
            forwardToErrorPage(request, response);
        }
	}
	
	private boolean validateInputs( String email) {
        return email!=null;
    }
	private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
        rd.forward(request, response);
    }


}
