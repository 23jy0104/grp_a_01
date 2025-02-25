package Kanri;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import dao.ReservationDAO;
import model.Customer;
import model.HenkyakuData;

/**
 * Servlet implementation class k_P21Servlet
 */
@WebServlet("/k_P21Servlet")
public class k_P21Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public k_P21Servlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String customerKana = request.getParameter("name");
		String tellNumber = request.getParameter("tell");
		String customerId = request.getParameter("customerId");
		
		CustomerDao customerDao = new CustomerDao();
		Customer customer = null;
		if(customerId == null || customerId.equals("")) {
			customer = customerDao.searchCustomerBykanatel(customerKana, tellNumber);
		}else {
			customer = customerDao.getCustomerById(customerId);
		}
		
		if(customer == null) {
			request.setAttribute("errMessage", "入力された内容の顧客が見つかりませんでした。");
			request.getRequestDispatcher("k_P21.jsp").forward(request, response);
		}else {
			ReservationDAO reservationDao = new ReservationDAO();
			HenkyakuData henkyaku = reservationDao.getUsage(customer.getCustomerId());
			if(henkyaku == null) {
				request.setAttribute("errMessage", "※現在利用している車両はありません。");
			}
			request.setAttribute("customer", customer);
			request.setAttribute("henkyaku", henkyaku);
			request.getRequestDispatcher("k_P25.jsp").forward(request, response);
		}
	}

}
