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
        Date birthDate = parseDate(request.getParameter("birthday"));
        Date licenseDate = parseDate(request.getParameter("licenseDate"));

        Customer customer = new Customer();
        String licenseNumber = request.getParameter("licenseNumber");

        if (islicenseNumberExists(licenseNumber)) {
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
                byte[] omoteBytes = convertBlobToBytes(createBlobFromPart(omoteJpg));
                byte[] uraBytes = convertBlobToBytes(createBlobFromPart(uraJpg));
                
                // セッションにbyte[]をセット
                HttpSession session = request.getSession();
                session.setAttribute("omoteImage", omoteBytes);
                session.setAttribute("uraImage", uraBytes);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

            // セッションにcustomerオブジェクトをセット
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);

            // CreditNewサーブレットにフォワードし、データを渡す
            RequestDispatcher rd = request.getRequestDispatcher("P20.jsp");
            rd.forward(request, response);
        }
    }

    private Date parseDate(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Blob createBlobFromPart(Part part) throws SQLException, IOException {
        byte[] blobData = part.getInputStream().readAllBytes();
        return new javax.sql.rowset.serial.SerialBlob(blobData);
    }

    private byte[] convertBlobToBytes(Blob blob) throws SQLException {
        return blob.getBytes(1, (int) blob.length());
    }

    private boolean islicenseNumberExists(String licenseNumber) {
        // データベース接続のコードはコメントアウト
        return false; // 常にfalseを返す
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
