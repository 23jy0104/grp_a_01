package carShareHome;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReservationOK
 */
@WebServlet("/ReservationOK")
public class ReservationOK extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String selectedDate = request.getParameter("selectedDate");
        String carCode = request.getParameter("carCode");
        String startTimeHour = request.getParameter("startTimeHour");
        String startTimeMinute = request.getParameter("startTimeMinute");
        String endTimeHour =request.getParameter("endTimeMinute");
        String endTimeMinute =request.getParameter("endTimeMinute");
        
        System.out.println("OKサーブレット："+selectedDate);
        System.out.println(carCode);
        System.out.println(startTimeHour);
        System.out.println(startTimeMinute);
        System.out.println(endTimeHour);
        System.out.println(endTimeMinute);
        
	
	}

}
