package carShareHome;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReservationDAO;
import model.HenkyakuData;

/**
 * Servlet implementation class StationQRcodeReader
 */
@WebServlet("/StationQRcodeReader")
public class StationQRcodeReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StationQRcodeReader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reservationId = request.getParameter("reservationId");
		request.setAttribute("reservationId", reservationId);
		ReservationDAO reservationDao = new ReservationDAO();
		HenkyakuData henkyaku = reservationDao.getUsageById(reservationId);
		if(henkyaku.getTimeDate() == null || henkyaku.equals("")) {
			reservationDao.setTimeDate(reservationId);
		}else if(henkyaku.getFinishDate() == null || henkyaku.equals("")) {
			reservationDao.setFinishDate(reservationId);
		}else {
			System.out.println("指定された予約は利用が終了しています。");
			request.getRequestDispatcher("noqrcode.jsp").forward(request, response);
		}
		request.getRequestDispatcher("qrcode.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
