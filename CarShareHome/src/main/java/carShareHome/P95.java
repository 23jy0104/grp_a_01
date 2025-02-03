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
        String errorMessage = null;

        Customer customer = userDao.getUserByUsername(customerName); // ユーザー情報を取得

        // パスワードの形式を検証
        if (!isValidPassword(currentPassword) || !isValidPassword(newPassword) || !isValidPassword(confirmNewPassword)) {
            errorMessage = "パスワードは半角数字8字～12字までである必要があります。";
        } else if (!newPassword.equals(confirmNewPassword)) {
            errorMessage = "新規パスワードと確認用パスワードが一致しません。";
        }

        if (errorMessage != null) {
            response.sendRedirect("P95.jsp?error=" + errorMessage); // エラーメッセージをリダイレクト
        } else {
            if (customer != null && customer.getCustomerPassword().equals(currentPassword)) {
                customer.setCustomerPassword(newPassword);
                userDao.updateUser(customer); // パスワードを更新
                response.sendRedirect("P99.jsp"); // 成功ページへリダイレクト
            } else {
                response.sendRedirect("P95.jsp?error=現在のパスワードが正しくありません。");
            }
        }
    }

    private boolean isValidPassword(String password) {
        return password != null && password.matches("^[0-9]{8,12}$");
    }
}
