package Kanri;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;  // CustomerDaoをインポート
import model.Customer;    // Customerモデルをインポート

/**
 * Servlet implementation class k_P13Servlet
 */
@WebServlet("/k_P13Servlet")
public class k_P13Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public k_P13Servlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerDao customerDao = new CustomerDao(); // CustomerDaoのインスタンスを作成
        List<Customer> customerList = customerDao.getAllCustomers(); // 顧客情報を取得

        request.setAttribute("customerList", customerList); // リクエスト属性に設定
        request.getRequestDispatcher("k_P13.jsp").forward(request, response); // JSPにフォワード
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // POSTリクエストもGETリクエストとして処理
    }
}
