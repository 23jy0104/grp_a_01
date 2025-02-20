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
 * Servlet implementation class CustomerUpdate
 */
@WebServlet("/CustomerUpdate")
public class CustomerUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String postcode =request.getParameter("postcode");
        String customerId =request.getParameter("customerId");
        String address1 =request.getParameter("address1");
        String address2 =request.getParameter("address2");
        String apartment =request.getParameter("apartment");
        String customerAddress =address1 +" "+address2 +" "+apartment;
        String tellNumber =request.getParameter("tellNumber");
        if (validateInputs(customerAddress,tellNumber)) {
        	Customer customer = new Customer();
        		customer.setCustomerId(customerId);
                customer.setTellNumber(tellNumber);
                customer.setCustomerAddress(customerAddress);
                customer.setPostCode(postcode);
                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);
                System.out.println(postcode);
                System.out.println(customerAddress);
                System.out.println(tellNumber);
				try {
				    
				    RequestDispatcher rd = request.getRequestDispatcher("P82.jsp");
				    rd.forward(request, response);
				} catch (IOException e) {
				    e.printStackTrace();
				    request.setAttribute("errorMessage", "画像処理中にエラーが発生しました。");
				    forwardToErrorPage(request, response);
				}
        } else {
            request.setAttribute("errorMessage", "入力データにエラーがあります。");
            forwardToErrorPage(request, response);
        }
    }

    private boolean validateInputs( String customerAddress, String tellNumber) {
        return customerAddress!=null && tellNumber != null;
    }


    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
        rd.forward(request, response);
    }

}

