package Kanri;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import dao.KeyboxDao;
import dao.ReservationDAO;
import model.Customer;
import model.HenkyakuData;
import model.KeyBox;

/**
 * Servlet implementation class TestReturnServlet
 */
@WebServlet("/TestReturnServlet")
public class TestReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestReturnServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String customerId = request.getParameter("customerId");
		String stationId = request.getParameter("stationId");
		String reservationId = request.getParameter("reservationId");
		String keyboxId = request.getParameter("keyboxId");
		KeyboxDao keyboxDao = new KeyboxDao();
		
		if(keyboxId != null && !keyboxId.equals("")) {
			if(keyboxDao.updateKeyboxStatus(stationId, keyboxId, "2")) {
				request.getRequestDispatcher("testReturnCompleat.jsp").forward(request, response);
			}
		}
		
		ReservationDAO reservationDao = new ReservationDAO();
		CustomerDao customerDao = new CustomerDao();
		HenkyakuData henkyaku = reservationDao.getUsageById(reservationId);
		Customer customer = customerDao.getCustomerById(customerId);
		KeyBox keybox = keyboxDao.getKeyboxOpenYobiBox(stationId);
		if (keybox == null) {
			keybox = new KeyBox();
		}
		
		request.setAttribute("customer", customer);
		request.setAttribute("henkyaku", henkyaku);
		request.setAttribute("keybox", keybox);
		request.getRequestDispatcher("testReturn.jsp").forward(request, response);
	}

}
