package carShareHome;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.Customer;

@WebServlet("/CarShareNew")
@MultipartConfig
public class CarShareNew extends HttpServlet {
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

        String customerSei = request.getParameter("customerSei");
        String customerMei = request.getParameter("customerMei");
        String customerName = customerSei + customerMei;
        String customerSeiKana = request.getParameter("customerSeiKana");
        String customerMeiKana = request.getParameter("customerMeiKana");
        String customerNameKana = customerSeiKana + customerMeiKana;
        String customerPassword = request.getParameter("password");
        String hashedPassword = hashPassword(customerPassword);
        String city =request.getParameter("city");
        String address =request.getParameter("address");
        String building =request.getParameter("building");
        String customerAddress =city+address+building;
        String tellNumber = request.getParameter("TEL");
        String eMail = request.getParameter("email");
        Part omoteJpg = request.getPart("file_omote");
        Part uraJpg = request.getPart("file_ura");

        // 生年月日の取得と変換
        String birthDateString = request.getParameter("birthday");
        Date birthDate = null;
        if (birthDateString != null && !birthDateString.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                birthDate = dateFormat.parse(birthDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 免許証の取得と変換
        String licenseDateString = request.getParameter("licenseDate");
        Date licenseDate = null;
        if (licenseDateString != null && !licenseDateString.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                licenseDate = dateFormat.parse(licenseDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Customer customer = new Customer();
        String licenseNumber = request.getParameter("licenseNumber");

        if (islicenseNumberExists(licenseNumber)) {
            System.out.println("このライセンス番号は既に登録されています。");
            request.setAttribute("errorMessage", "このライセンス番号は既に登録されています。");
            RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
            rd.forward(request, response);
        } else {
            // Customerオブジェクトを作成
            customer.setCustomerName(customerName);
            customer.setCustomerKana(customerNameKana);
            customer.setCustomerPassword(hashedPassword);
            customer.settellNumber(tellNumber);
            customer.setEmail(eMail);
            customer.setBirthDate(birthDate);
            customer.setLicenseNumber(licenseNumber);
            customer.setLicenceDate(licenseDate);
            customer.setCustomerAddress(customerAddress);

            // InputStream から Blob を作成し、Customerオブジェクトにセット
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01", "23jya01", "23jya01")) {
                Blob omoteBlob = connection.createBlob();
                try (InputStream omoteInputStream = omoteJpg.getInputStream();
                     OutputStream omoteOutputStream = omoteBlob.setBinaryStream(1)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = omoteInputStream.read(buffer)) != -1) {
                        omoteOutputStream.write(buffer, 0, bytesRead);
                    }
                }
                customer.setOmote(omoteBlob); // Blobをセット

                Blob uraBlob = connection.createBlob();
                try (InputStream uraInputStream = uraJpg.getInputStream();
                     OutputStream uraOutputStream = uraBlob.setBinaryStream(1)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = uraInputStream.read(buffer)) != -1) {
                        uraOutputStream.write(buffer, 0, bytesRead);
                    }
                }
                customer.setUra(uraBlob); // Blobをセット
            } catch (SQLException e) {
                e.printStackTrace();
                // エラーハンドリング
            }
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            // データベースに挿入
            insertCustomerData(customer);
            RequestDispatcher rd = request.getRequestDispatcher("P20.jsp");
            rd.forward(request, response);
        }
    }

    private void insertCustomerData(Customer customer) {
        // データベース接続の情報を設定
        String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01";
        String dbUser = "23jya01";
        String dbPassword = "23jya01";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // データベースに接続
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // SQL文を準備
            String sql = "INSERT INTO customer (customer_name, customer_kana, customer_password, tell_number, e_mail, birth_date, license_number, license_date, omote_jpg, ura_jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);

            // パラメータを設定
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getCustomerKana());
            preparedStatement.setString(3, customer.getCustomerPassword());
            preparedStatement.setString(4, customer.gettellNumber());
            preparedStatement.setString(5, customer.getEmail());
            preparedStatement.setDate(6, new java.sql.Date(customer.getBirthDate().getTime()));
            preparedStatement.setString(7, customer.getLicenseNumber());
            preparedStatement.setDate(8, new java.sql.Date(customer.getLicenceDate().getTime()));
            preparedStatement.setBlob(9, customer.getOmote());
            preparedStatement.setBlob(10, customer.getUra());

            // SQL文を実行
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースを解放
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean islicenseNumberExists(String licenseNumber) {
        // データベース接続の情報を設定
        String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01";
        String dbUser = "23jya01";
        String dbPassword = "23jya01";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // データベースに接続
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);

            // SQL文を準備
            String sql = "SELECT COUNT(*) FROM customers WHERE license_number = ?";
            preparedStatement = connection.prepareStatement(sql);
            
            // パラメータを設定
            preparedStatement.setString(1, licenseNumber);

            // クエリを実行
            resultSet = preparedStatement.executeQuery();

            // 結果を確認
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // 1以上であれば重複
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // リソースを解放
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false; // 何らかのエラーが
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes("UTF-8"));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
        	throw new RuntimeException(e);
        }
    }
}