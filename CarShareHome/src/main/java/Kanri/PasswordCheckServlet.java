package Kanri;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import carShareHome.PasswordUtil;
import dao.ManagerDao;

@WebServlet("/PasswordCheckServlet")
public class PasswordCheckServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        String currentPath = request.getParameter("currentPath"); // 現在のパスを取得

        // パスワードをハッシュ化
        String hashedPassword = PasswordUtil.hashPassword(password);

        ManagerDao managerDao = new ManagerDao();
        try {
            // データベースからハッシュ化されたパスワードとマネージャー名を取得
            String storedHashedPassword = managerDao.getPasswordByUserId(userId);
            String managerName = managerDao.getManagerNameByUserId(userId);

            // ハッシュ化されたパスワードを比較
            if (hashedPassword.equals(storedHashedPassword)) {
                // マネージャー名の検証
                if (currentPath.contains("k_P2.jsp") && !managerName.equals("車両管理課")) {
                    request.setAttribute("message", "車両管理課のユーザーのみアクセス可能です。");
                    request.getRequestDispatcher("k_P2.jsp").forward(request, response);
                    return;
                } else if (currentPath.contains("k_P19.jsp") && !managerName.equals("オペレーター")) {
                    request.setAttribute("message", "オペレーターのユーザーのみアクセス可能です。");
                    request.getRequestDispatcher("k_P19.jsp").forward(request, response);
                    return;
                } else if (currentPath.contains("k_P11.jsp") && !managerName.equals("システム管理課")) {
                    request.setAttribute("message", "システム管理課のユーザーのみアクセス可能です。");
                    request.getRequestDispatcher("k_P11.jsp").forward(request, response);
                    return;
                }

                // パスワードが一致し、マネージャー名も正しい場合の処理
                if (currentPath.contains("k_P2.jsp")) {
                    request.getRequestDispatcher("/k_P4.jsp").forward(request, response);
                } else if (currentPath.contains("k_P19.jsp")) {
                    request.getRequestDispatcher("/k_P21.jsp").forward(request, response);
                } else if (currentPath.contains("k_P11.jsp")) {
                    request.getRequestDispatcher("/k_P13.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/default.jsp").forward(request, response);
                }
            } else {
                // IDまたはパスワードが間違っている場合の処理
                request.setAttribute("message", "IDまたはパスワードが間違っています。");

                // 現在のパスの最後の部分を取得
                String fileName = currentPath.substring(currentPath.lastIndexOf("/") + 1); // 最後のスラッシュ以降を取得

                // 元のページにエラーメッセージを渡して戻る
                if (!fileName.isEmpty()) {
                    request.setAttribute("errorMessage", "IDまたはパスワードが間違っています。");
                    request.getRequestDispatcher(fileName).forward(request, response); // 元のページにフォワード
                } else {
                    request.getRequestDispatcher("k_P2.jsp").forward(request, response);
                }
            }
        } finally {
            managerDao.connectionClose();
        }
    }
}
