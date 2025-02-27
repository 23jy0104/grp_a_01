package carShareHome;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
@MultipartConfig()
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
        String customerName =customerSei +" "+customerMei;
        String customerSeiKana = request.getParameter("customerSeiKana");
        String customerMeiKana = request.getParameter("customerMeiKana");
        String customerKana =customerSeiKana+" "+customerMeiKana;

        String gender = request.getParameter("gender");
        String birthday = request.getParameter("birthday");
        String postcode = request.getParameter("postcode");
        String city = request.getParameter("city");
        String address = request.getParameter("address");
        String building = request.getParameter("building");
        String customerAddress =city+" "+address+" "+building;
        String tellNumber = request.getParameter("TEL");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String licenseNumber = request.getParameter("licenseNumber");       
        Part omoteJpg = request.getPart("file_omote");
        Part uraJpg = request.getPart("file_ura");
        String file_omote =Paths.get(omoteJpg.getSubmittedFileName()).getFileName().toString();
        String file_ura =Paths.get(uraJpg.getSubmittedFileName()).getFileName().toString();
        String path =getServletContext().getRealPath("upload");
        String licenseDate = request.getParameter("licenseDate");
        System.out.println(path);
        omoteJpg.write(path + File.separator+file_omote);
        uraJpg.write(path +File.separator +file_ura); 
        
        if (validateInputs(customerSei, customerMei, customerSeiKana, customerMeiKana, gender, birthday, licenseDate, tellNumber, email)) {
        	Customer customer = new Customer();

            if (isLicenseNumberExists(licenseNumber)) {
                request.setAttribute("errorMessage", "このライセンス番号は既に登録されています。");
                forwardToErrorPage(request, response);
            } else {
                customer.setCustomerName(customerName);
                customer.setCustomerKana(customerKana);
                customer.setGender(gender);
                customer.setCustomerPassword(password);
                customer.setTellNumber(tellNumber);
                customer.setEmail(email);
                customer.setBirthDate(birthday);
                customer.setLicenseNumber(licenseNumber);
                customer.setLicenceDate(licenseDate);
                customer.setCustomerAddress(customerAddress);
                customer.setPostCode(postcode);
                customer.setUra(file_omote);
                customer.setOmote(file_ura);

                HttpSession session = request.getSession();
                session.setAttribute("customer", customer);

				try {
				    
				    RequestDispatcher rd = request.getRequestDispatcher("P20.jsp");
				    rd.forward(request, response);
				} catch (IOException e) {
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
        return sei != null && mei != null && seiKana != null && meiKana != null && gender != null && birthDate != null && licenseDate != null && tellNumber != null && email != null;
    }

    private boolean isLicenseNumberExists(String licenseNumber) {
        return false;
    }

    private void forwardToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
        rd.forward(request, response);
    }

   
}
