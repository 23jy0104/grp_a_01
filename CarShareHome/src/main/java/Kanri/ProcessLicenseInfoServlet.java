package Kanri;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDao;

@WebServlet("/processLicenseInfo")
public class ProcessLicenseInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // 顧客IDを取得
        String customerId = request.getParameter("customerId");
        System.out.println("判定 ID: " + customerId); // 顧客IDを出力

        // 顧客IDがnullの場合のエラーハンドリング
        if (customerId == null || customerId.isEmpty()) {
            out.print("{\"error\": \"Customer ID is missing.\"}");
            out.flush();
            return;
        }

        // ランダムに0または1を生成
        Random random = new Random();
        int result = random.nextInt(2); // 0か1を生成

        String managerCheck = (result == 0) ? "○" : "×"; // 判定の設定
        System.out.println("Generated Random Result: " + result + " (" + managerCheck + ")"); // 判定を出力

        // DAOを使って結果をデータベースに保存
        CustomerDao customerDao = new CustomerDao(); // DAOインスタンスの生成
        customerDao.updateManagerCheck(customerId, managerCheck); // 更新メソッドを呼び出し

        // JSON形式で結果を返す
        out.print("{\"result\": " + result + ", \"managerCheck\": \"" + managerCheck + "\"}");
        out.flush();
    }
}
