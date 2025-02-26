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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String stationId =request.getParameter("stationId");
		String carCode =request.getParameter("carCode");
		String img =request.getParameter("img");
		String modelName=request.getParameter("modelName");

		System.out.println(stationId);
		System.out.println(img);
		System.out.println(modelName);
        
        
	
	}

}
