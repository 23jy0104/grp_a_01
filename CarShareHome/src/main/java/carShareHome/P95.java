package carShareHome;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerTouroku; // インポート
import dao.UserDao; // インポート
import model.Customer;

@WebServlet("/p95")
public class P95 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new CustomerTouroku(); // UserDaoインターフェースを実装

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String customerName = (String) session.getAttribute("customerName");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmNewPassword = request.getParameter("confirmNewPassword");

        Customer customer = userDao.getUserByUsername(customerName); // 小文字に修正

        if (customer != null && customer.getCustomerPassword().equals(currentPassword)) {
            if (newPassword.equals(confirmNewPassword)) {
                customer.setCustomerPassword(newPassword);
                userDao.updateUser(customer);
                response.sendRedirect("P99.jsp");
            } else {
                response.sendRedirect("changePassword.jsp?error=新しいパスワードが一致しません。");
            }
        } else {
            response.sendRedirect("changePassword.jsp?error=現在のパスワードが正しくありません。");
        }
    }
}