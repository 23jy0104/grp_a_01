package dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DataBaseUtil;

@WebServlet("/LoginDataBase")
public class LoginDataBase extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // JSONデータを取得
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        String jsonData = sb.toString();
        String email = ""; // JSONからメールアドレスを取得
        String password = ""; // JSONからパスワードを取得

        // ここでJSONをパースしてemailとpasswordに値をセットする必要があります。
        // JSONパースにはライブラリが必要です（例：GsonやJacksonなど）。

        boolean isValidUser = false;

        try (Connection con = DataBaseUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            isValidUser = rs.next(); // ユーザーが見つかればtrue
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isValidUser) {
            out.println("{\"success\": true}");
        } else {
            out.println("{\"success\": false}");
        }
    }
}
