package carShareHome;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ReservationDAO;
import model.Reservation;

@WebServlet("/history")
public class UseHistory  extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String customerName = request.getParameter("customerName");

        ReservationDAO reservationDAO = new ReservationDAO();
        List<Reservation> usedReservations = reservationDAO.getPastReservations(customerId);

        // 取得したデータをリクエスト属性に設定
        request.setAttribute("usedReservations", usedReservations);
        request.setAttribute("customerId", customerId);
        request.setAttribute("customerName", customerName);

        // 履歴表示のJSPにフォワード
        request.getRequestDispatcher("/P74.jsp").forward(request, response);
    }
}
