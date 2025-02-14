package carShareHome;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerTouroku; // インポート
import dao.UserDao; // インポート
import model.Customer;

@WebServlet("/P95")
public class P95 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new CustomerTouroku(); // UserDaoインターフェースを実装

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String customerName = (String) session.getAttribute("customerName");

        String currentPassword = request.getParameter("password"); // 現在のパスワード
        String newPassword = request.getParameter("new-password"); // 新しいパスワード
        String confirmNewPassword = request.getParameter("confirm-password"); // 確認用パスワード
        String errorMessage = null;

        Customer customer = userDao.getUserByUsername(customerName);

        // パスワードの形式を検証
        if (!isValidPassword(currentPassword) || !isValidPassword(newPassword) || !isValidPassword(confirmNewPassword)) {
            errorMessage = "パスワードは半角英数字8字～12字までである必要があります。";
        } else if (!newPassword.equals(confirmNewPassword)) {
            errorMessage = "新規パスワードと確認用パスワードが一致しません。";
        }

        if (errorMessage != null) {
            response.sendRedirect("P95.jsp?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
        } else {
            // 現在のパスワードをハッシュ化して比較
            if (customer != null && PasswordUtil.hashPassword(currentPassword).equals(customer.getCustomerPassword())) {
                // 新しいパスワードをハッシュ化して設定
                customer.setCustomerPassword(PasswordUtil.hashPassword(newPassword));
                userDao.updateUser(customer); // パスワードを更新
                response.sendRedirect("P99.jsp"); // 成功ページへリダイレクト
            } else {
                String encodedError = URLEncoder.encode("現在のパスワードが正しくありません。", "UTF-8");
                response.sendRedirect("P95.jsp?error=" + encodedError);
            }
        }
    }

    private boolean isValidPassword(String password) {
        // 半角英数字8字～12字の正規表現
        return password != null && password.matches("^[a-zA-Z0-9]{8,12}$");
    }
}
