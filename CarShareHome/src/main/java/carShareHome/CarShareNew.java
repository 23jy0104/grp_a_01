package carShareHome;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        String customerName = customerSei + " " + customerMei; // スペースを追加
        String customerSeiKana = request.getParameter("customerSeiKana");
        String customerMeiKana = request.getParameter("customerMeiKana");
        String customerNameKana = customerSeiKana + " " + customerMeiKana; // スペースを追加
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
        String birthDate = request.getParameter("birthday");
        String licenseDate = request.getParameter("licenseDate");

        // 入力データの検証
        if (validateInputs(customerSei, customerMei, customerSeiKana, customerMeiKana, gender, birthDate, licenseDate, tellNumber, eMail)) {
            Customer customer = new Customer();
            String licenseNumber = request.getParameter("licenseNumber");

            if (isLicenseNumberExists(licenseNumber)) {
                request.setAttribute("errorMessage", "このライセンス番号は既に登録されています。");
                forwardToErrorPage(request, response);
            } else {
                // Customerオブジェクトを作成
                customer.setCustomerName(customerName);
                customer.setCustomerKana(customerNameKana);
                customer.setGender(gender);
                customer.setCustomerPassword(hashedPassword);
                customer.setTellNumber(tellNumber);
                customer.setEmail(eMail);
                customer.setBirthDate(parseDate(birthDate));
                customer.setLicenseNumber(licenseNumber);
                customer.setLicenceDate(parseDate(licenseDate));
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
                    session.setAttribute("customer", customer);

                    // CreditNewサーブレットにフォワードし、データを渡す
                    RequestDispatcher rd = request.getRequestDispatcher("P20.jsp");
                    rd.forward(request, response);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "画像処理中にエラーが発生しました。");
                    forwardToErrorPage(request, response);
                }
            }
        } else {
            request.setAttribute("errorMessage", "入力データにエラーがあります。");
            forwardToErrorPage(request, response);
        }
    }

    private boolean validateInputs(String sei, String mei, String seiKana, String meiKana, String gender, String birthDate, String licenseDate, String tellNumber, String email) {
        // 簡単な検証ルールを追加
        return sei != null && mei != null && seiKana != null && meiKana != null && gender != null && birthDate != null && licenseDate != null && tellNumber != null && email != null;
    }

    private String parseDate(String dateString) {
        if (dateString != null && !dateString.isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(dateFormat.parse(dateString).getTime());
                return date.toString(); // Dateオブジェクトの文字列表現を返す
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Blob createBlobFromPart(Part part) throws SQLException, IOException {
        try (var inputStream = part.getInputStream()) {
            byte[] blobData = inputStream.readAllBytes();
            return new javax.sql.rowset.serial.SerialBlob(blobData);
        }
    }

    private byte[] convertBlobToBytes(Blob blob) throws SQLException {
        return blob.getBytes(1, (int) blob.length());
    }

    private boolean isLicenseNumberExists(String licenseNumber) {
        // データベース接続のコードはコメントアウト
        return false; // 常にfalseを返す
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
        rd.forward(request, response);
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
