package Kanri;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import model.Customer;

@WebServlet("/k_P18Servlet")
public class k_P18Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // クエリパラメータからcustomerIdを取得
        String customerId = request.getParameter("customerId");
        String customerName = request.getParameter("customer_name");
        System.out.println("18サーブレット Customer ID: " + customerId); // デバッグ用メッセージ

        if (customerId == null || customerId.isEmpty()) {
            System.err.println("Customer ID is missing or empty."); // エラーメッセージ
            request.setAttribute("errorMessage", "Customer ID is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response); // エラーページにリダイレクト
            return;
        }

        // CustomerDaoを使用して顧客情報を取得
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.getCustomerById(customerId);

        // 顧客情報をリクエスト属性に設定
        if (customer != null) {
            request.setAttribute("customerName", customer.getCustomerName());
            request.setAttribute("customerId", customer.getCustomerId());
            request.setAttribute("tellNumber", customer.getTellNumber());
            request.setAttribute("email", customer.getEmail());
            request.setAttribute("licenseNumber", customer.getLicenseNumber());
            request.setAttribute("licenceDate", customer.getLicenceDate());
            request.setAttribute("postCode", customer.getPostCode());
            request.setAttribute("customerAddress", customer.getCustomerAddress());
            request.setAttribute("omoteJpg", customer.getOmote());
            request.setAttribute("uraJpg", customer.getUra());
        } else {
            System.err.println("No customer found with ID: " + customerId);
        }

        // JSPにフォワード
        request.getRequestDispatcher("k_P18.jsp").forward(request, response);
    }
}
