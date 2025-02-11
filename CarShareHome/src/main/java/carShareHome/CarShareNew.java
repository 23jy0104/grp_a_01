package carShareHome;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
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
        String gender = request.getParameter("gender");
        String customerPassword = request.getParameter("password");
        String hashedPassword = hashPassword(customerPassword);
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String building = request.getParameter("building");
        String customerAddress = city + address + building;
        String tellNumber = request.getParameter("TEL");
        String eMail = request.getParameter("email");
        Part omoteJpg = request.getPart("file_omote");
        Part uraJpg = request.getPart("file_ura");
        String postCode = request.getParameter("postcode");

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
            customer.setGender(gender);
            customer.setCustomerPassword(hashedPassword);
            customer.setTellNumber(tellNumber);
            customer.setEmail(eMail);
            customer.setBirthDate(birthDate);
            customer.setLicenseNumber(licenseNumber);
            customer.setLicenceDate(licenseDate);
            customer.setCustomerAddress(customerAddress);
            customer.setPostCode(postCode);

            // 画像ファイルの処理
            try {
                Blob omoteBlob = createBlobFromPart(omoteJpg);
                customer.setOmote(omoteBlob);
                Blob uraBlob = createBlobFromPart(uraJpg);
                customer.setUra(uraBlob);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

            // セッションにcustomerオブジェクトをセット
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);

            // CreditNewサーブレットにフォワードし、データを渡す
            request.setAttribute("customerName", customerName);
            request.setAttribute("customerKana", customerNameKana);
            request.setAttribute("gender", gender);
            request.setAttribute("customerPassword", hashedPassword);
            request.setAttribute("email", eMail);
            request.setAttribute("tellNumber", tellNumber);
            request.setAttribute("postCode", postCode);
            request.setAttribute("birthDate", birthDate);
            request.setAttribute("customerAddress", customerAddress);
            request.setAttribute("licenseNumber", licenseNumber);
            request.setAttribute("licenseDate", licenseDateString); // 生年月日を文字列で渡す

            RequestDispatcher rd = request.getRequestDispatcher("P20.jsp");
            rd.forward(request, response);
        }
    }

    private Blob createBlobFromPart(Part part) throws SQLException, IOException {
        // PartからBlobを生成
        byte[] blobData = part.getInputStream().readAllBytes();
        return new javax.sql.rowset.serial.SerialBlob(blobData);
    }

    private boolean islicenseNumberExists(String licenseNumber) {
        // 本来のデータベース接続はコメントアウト
        /*
        String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01";
        String dbUser = "23jya01";
        String dbPassword = "23jya01";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            String sql = "SELECT COUNT(*) FROM customer WHERE license_number = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, licenseNumber);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // 1以上であれば重複
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        */
        return false; // データベース接続を行わないため、常にfalseを返す
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
