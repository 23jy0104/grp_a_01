package carShareHome;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDao;
import model.Customer;

/**
 * Servlet implementation class Top
 */
@WebServlet("/Top")
public class Top extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        CustomerDao customerDao = new CustomerDao();
        ArrayList<Customer> customerList = customerDao.searchCustomer("", null); // 例: 引数を適切に設定

        HttpSession session = request.getSession();
        session.setAttribute("customerList", customerList); // 顧客リストをセッションに保存

        RequestDispatcher rd = request.getRequestDispatcher("top.jsp");
        rd.forward(request, response);
    }
}
