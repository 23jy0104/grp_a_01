package carShareHome;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReservationDAO;

/**
 * Servlet implementation class Reg
 */
@WebServlet("/Reg")
public class Reg extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Reg() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String startDate = (String) request.getSession().getAttribute("startDate");
        String endDate = (String) request.getSession().getAttribute("endDate");
        String customerId = (String) request.getSession().getAttribute("customerId");
        String carCode = request.getParameter("carCode");
        Integer totalCost = (Integer) request.getSession().getAttribute("totalCost");
        
        ReservationDAO reservationDao = new ReservationDAO();
        boolean yoyaku = reservationDao.setReservation(startDate, endDate, customerId, totalCost, carCode);
        if(!yoyaku) {
        	System.out.println("Reg実行に失敗しています");
        }
        String path ="P64.jsp";
        RequestDispatcher rd =request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

}
