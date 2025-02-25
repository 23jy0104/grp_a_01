package Kanri;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class ReturnServlet
 */
@WebServlet("/ReturnServlet")
public class ReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String customerId = request.getParameter("customerId");
		String stationId = request.getParameter("stationId");
		String reservarionId = request.getParameter("reservationId");
		String selectKeyBoxId = request.getParameter("selectKeyBoxId");
		
		ReservationDAO reservationDao = new ReservationDAO();
		CustomerDao customerDao = new CustomerDao();
		KeyboxDao keyboxDao = new KeyboxDao();
		KeyBox keybox = keyboxDao.getKeyboxByReservationId(reservarionId);
		KeyBox returnKeybox = keyboxDao.getKeyboxById(selectKeyBoxId, stationId);
		HenkyakuData henkyaku = reservationDao.getUsageById(reservarionId);
		Customer customer = customerDao.getCustomerById(customerId);
		
		if(keybox == null || returnKeybox == null) {
			throw new ServletException("キーボックスの取得に失敗しました。");
		}
		try {
			if(keyboxDao.moveKeybox(keybox, returnKeybox ,reservarionId)) {
				request.setAttribute("customer", customer);
				request.setAttribute("henkyaku", henkyaku);
				request.getRequestDispatcher("k_P32.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
