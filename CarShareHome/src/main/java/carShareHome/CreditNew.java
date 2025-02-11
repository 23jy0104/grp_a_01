package carShareHome;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        // 顧客データを取得
        String customerName = (String) request.getAttribute("customerName");
        String customerKana = (String) request.getAttribute("customerKana");
        String gender = (String) request.getAttribute("gender");
        String password = (String) request.getAttribute("customerPassword");
        java.util.Date birthDate = (java.util.Date) request.getAttribute("birthDate");
        String email = (String) request.getAttribute("email");
        String tellNumber = (String) request.getAttribute("tellNumber");
        String postCode = (String) request.getAttribute("postCode");
        String customerAddress = (String) request.getAttribute("customerAddress");
        String licenseNumber = (String) request.getAttribute("licenseNumber");
        java.util.Date licenseDate = (java.util.Date) request.getAttribute("licenseDate");

        // クレジットカード情報を取得
        String creditNumber = request.getParameter("credit_number");
        String creditDate = request.getParameter("credittime");
        String securityCode = request.getParameter("security");

        // セッションから画像データを取得
        HttpSession session = request.getSession();
        byte[] omoteBytes = (byte[]) session.getAttribute("omoteImage");
        byte[] uraBytes = (byte[]) session.getAttribute("uraImage");

        // データベースに登録する処理
        try {
            // データベース接続情報
            String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01";
            String dbUser = "23jya01";
            String dbPassword = "23jya01";

            // データベース接続
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            String sql = "INSERT INTO customer (customer_name, customer_kana, gender, customer_password, tell_number, e_mail, birth_date, license_number, license_date, post_code, customer_address, omote_jpg, ura_jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, customerKana);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, tellNumber);
            preparedStatement.setString(6, email);
            preparedStatement.setDate(7, new java.sql.Date(birthDate.getTime()));
            preparedStatement.setString(8, licenseNumber);
            preparedStatement.setDate(9, new java.sql.Date(licenseDate.getTime()));
            preparedStatement.setString(10, postCode);
            preparedStatement.setString(11, customerAddress);
            
            // 画像データをBlobとして設定
            if (omoteBytes != null) {
                Blob omoteBlob = new javax.sql.rowset.serial.SerialBlob(omoteBytes);
                preparedStatement.setBlob(12, omoteBlob);
            } else {
                preparedStatement.setNull(12, java.sql.Types.BLOB);
            }

            if (uraBytes != null) {
                Blob uraBlob = new javax.sql.rowset.serial.SerialBlob(uraBytes);
                preparedStatement.setBlob(13, uraBlob);
            } else {
                preparedStatement.setNull(13, java.sql.Types.BLOB);
            }

            // SQLを実行
            preparedStatement.executeUpdate();

            // リダイレクトやフォワード処理
            response.sendRedirect("P28.jsp"); // 成功時のページへリダイレクト
        } catch (SQLException e) {
            e.printStackTrace();
            // エラーハンドリングの処理
            request.setAttribute("errorMessage", "データベースへの登録に失敗しました。");
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        }
    }
}
