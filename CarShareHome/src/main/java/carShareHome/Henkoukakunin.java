package carShareHome;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Reservation.ReservationManager;

/**
 * Servlet implementation class Henkoukakunin
 */
@WebServlet("/Henkoukakunin")
public class Henkoukakunin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Henkoukakunin() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
	     response.setContentType("text/html; charset=UTF-8");
	      
	     String reservationId =request.getParameter("reservationId");
	     String priceParam = request.getParameter("price");
	     int price = Integer.parseInt(priceParam); // 必要に応じて型変換
	        
	     String startDate =request.getParameter("startDate");
	     System.out.println("変更確認"+startDate);
	     String stopDate =request.getParameter("stopDate"); 
	     System.out.println("変更確認"+stopDate);
	     ReservationManager.changeReservation(reservationId, startDate, stopDate,price);
	     
	     RequestDispatcher rd =request.getRequestDispatcher("P71.jsp");
	     rd.forward(request, response);
	}

}
