package carShareHome;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PasswordHasher;

/**
 * Servlet implementation class CreditNew
 */
@WebServlet("/CreditNew")
public class CreditNew extends HttpServlet {
    private static final long serialVersionUID = 1L;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // 顧客データを取得
        String customerName = request.getParameter("customerName");
        String customerKana = request.getParameter("customerKana");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");
        String birthDateStr = request.getParameter("birthDate");
        String email = request.getParameter("email");
        String tellNumber = request.getParameter("tellNumber");
        String postCode = request.getParameter("postcode");
        String customerAddress = request.getParameter("customerAddress");
        String licenseNumber = request.getParameter("licenseNumber");
        String licenseDateStr = request.getParameter("licenseDate");
        String file_omote =request.getParameter("omote");
        String file_ura =request.getParameter("ura");
        String hashedPassword = PasswordHasher.hashPassword(password);
        // 日付の変換
        java.sql.Date birthDate = convertStringToSqlDate(birthDateStr);
        java.sql.Date licenseDate = convertStringToSqlDate(licenseDateStr);

        // データベースに登録する処理
        try {
            // データベース接続情報
            String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01?useUnicode=true&characterEncoding=UTF-8";
            String dbUser = "23jya01";
            String dbPassword = "23jya01";

            // データベース接続
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            String sql = "INSERT INTO customer (customer_name, customer_kana, gender, customer_password, tell_number, e_mail, birth_date, license_number, license_date, post_code, customer_address, omote_jpg, ura_jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerKana);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, hashedPassword);
            preparedStatement.setString(5, tellNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setDate(7, birthDate);
            preparedStatement.setString(8, licenseNumber);
            preparedStatement.setDate(9, licenseDate);
            preparedStatement.setString(10, postCode);
            preparedStatement.setString(11, customerAddress);
            preparedStatement.setString(12, file_omote);
            preparedStatement.setString(13, file_ura);
       

            // SQLを実行
            preparedStatement.executeUpdate();

            // リダイレクトやフォワード処理
            response.sendRedirect("P28.jsp"); // 成功時のページへリダイレクト
        } catch (SQLException e) {
            e.printStackTrace();
            // エラーハンドリングの処理
            request.setAttribute("errorMessage", "データベースへの登録に失敗しました。");
            RequestDispatcher rd = request.getRequestDispatcher("P22.jsp");
            rd.forward(request, response);
        }
    }

    private java.sql.Date convertStringToSqlDate(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(dateStr);
                return new java.sql.Date(date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
