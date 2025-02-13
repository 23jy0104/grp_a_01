package carShareHome;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

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
        String customerName = customerSei + " " + customerMei;
        String customerSeiKana = request.getParameter("customerSeiKana");
        String customerMeiKana = request.getParameter("customerMeiKana");
        String customerKana = customerMeiKana + customerSeiKana;
        String gender = request.getParameter("gender");
        String Password = request.getParameter("password");
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

        if (validateInputs(customerSei, customerMei, customerSeiKana, customerMeiKana, gender, birthDate, licenseDate, tellNumber, eMail,city,address,building,Password)) {
            Customer customer = new Customer();
            String licenseNumber = request.getParameter("licenseNumber");

            if (isLicenseNumberExists(licenseNumber)) {
                request.setAttribute("errorMessage", "このライセンス番号は既に登録されています。");
                forwardToErrorPage(request, response);
            } else {
                customer.setCustomerName(customerName);
                customer.setCustomerKana(customerKana);
                customer.setGender(gender);
                customer.setCustomerPassword(Password);
                customer.setTellNumber(tellNumber);
                customer.setEmail(eMail);
                customer.setBirthDate(birthDate);
                customer.setLicenseNumber(licenseNumber);
                customer.setLicenceDate(licenseDate);
                customer.setCustomerAddress(customerAddress);
                customer.setPostCode(postCode);

                HttpSession hs = request.getSession();
                hs.setAttribute("customer", customer);

                try {
                    byte[] omoteBytes = convertBlobToBytes(createBlobFromPart(omoteJpg));
                    byte[] uraBytes = convertBlobToBytes(createBlobFromPart(uraJpg));
                    
                    hs.setAttribute("omoteImage", omoteBytes);
                    hs.setAttribute("uraImage", uraBytes);

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

    private boolean validateInputs(String sei, String mei, String seiKana, String meiKana, String gender, String birthDate, String licenseDate, String tellNumber, String email,String city,String address,String building, String Password) {
        return sei != null && mei != null && seiKana != null && meiKana != null && gender != null && birthDate != null && licenseDate != null && tellNumber != null && email != null &&city!=null &&address !=null &&building!=null && Password !=null;
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
        return false;
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
        rd.forward(request, response);
    }

   
}
