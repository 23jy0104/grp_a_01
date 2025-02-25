package carShareHome;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerTouroku; // インポート
import dao.UserDao; // インポート
import model.Customer;

@WebServlet("/PasswordServlet")
public class PasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new CustomerTouroku(); // UserDaoインターフェースを実装

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId"); // customerIdを取得
        String newPassword = request.getParameter("new-password"); // 新しいパスワード
        String confirmNewPassword = request.getParameter("confirm-password"); // 確認用パスワード
        String errorMessage = null;

        // ユーザー情報を取得
        Customer customer = userDao.getUserByCustomerId(customerId);

        // ユーザーが見つからない場合の処理
        if (customer == null) {
            errorMessage = "ユーザーが見つかりません。";
            response.sendRedirect("P35.jsp?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
            return; // 処理を終了
        }

        // パスワードの形式を検証
        if (!isValidPassword(newPassword) || !isValidPassword(confirmNewPassword)) {
            errorMessage = "パスワードは半角英数字8字～12字までである必要があります。";
        } else if (!newPassword.equals(confirmNewPassword)) {
            errorMessage = "新規パスワードと確認用パスワードが一致しません。";
        }

        if (errorMessage != null) {
            response.sendRedirect("P35.jsp?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
        } else {
            // 新しいパスワードをハッシュ化して設定
            customer.setCustomerPassword(PasswordUtil.hashPassword(newPassword));
            userDao.updateUser(customer); // パスワードを更新
            response.sendRedirect("P38.jsp"); // 成功ページへリダイレクト
        }
    }

    private boolean isValidPassword(String password) {
        // 半角英数字8字～12字の正規表現
        return password != null && password.matches("^[a-zA-Z0-9]{8,12}$");
    }
}
