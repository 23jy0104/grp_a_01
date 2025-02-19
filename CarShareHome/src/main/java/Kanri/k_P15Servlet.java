package Kanri;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;
import model.Customer;

@WebServlet("/k_P15Servlet")
public class k_P15Servlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // クエリパラメータからcustomerIdを取得
        String customerId = request.getParameter("customerId");

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
        }

        // JSPにフォワード
        request.getRequestDispatcher("k_P15.jsp").forward(request, response);
    }
}
