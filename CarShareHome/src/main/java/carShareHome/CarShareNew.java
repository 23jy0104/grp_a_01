package carShareHome;



import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerTouroku;
import model.Customer;



/**

 * Servlet implementation class CarShareNew

 */

@WebServlet("/CarShareNew")

public class CarShareNew extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		response.setContentType("text/html; Charset=UTF-8");

		String customerName=request.getParameter("customer_Name");
		
		String customerNameKana=request.getParameter("customer_Name_kana");

		String customerPassword=request.getParameter("customer_Password");

		String tellNumber=request.getParameter("tell_Number");

		String eMail=request.getParameter("e_Mail");

		String birthDateString=request.getParameter("birth_Date");

		Date birthDate=null;

		String licenseNumber=request.getParameter("license_Number");

		String licenseDateString=request.getParameter("license_Date");

		Date licenseDate=null;

		SimpleDateFormat dateFormat=new SimpleDateFormat("yyy-MM-dd");	
		

		try {

	        birthDate = dateFormat.parse(birthDateString);

	        licenseDate = dateFormat.parse(licenseDateString);

	    } catch (Exception e) {

	        e.printStackTrace(); // 例外処理

	    }

		if (islicenseNumberExists(licenseNumber)) {

	        // 同じ licenseNumber が存在する場合の処理

	        System.out.println("このライセンス番号は既に登録されています。");

	    } else {

	        // ユーザーを登録する処理

	        registerUser(customerName, customerPassword,customerNameKana, tellNumber, eMail, birthDate, licenseNumber, licenseDate);

	    }

		Customer customer=new Customer(customerPassword, customerName,customerNameKana, customerPassword, licenseNumber, eMail, birthDate, tellNumber, licenseDate, customerName, birthDateString, licenseNumber, licenseDateString);

		CustomerTouroku ct=new CustomerTouroku();

		ct.addCustomer(customer);

		ct.connectionClose();

		RequestDispatcher rd=request.getRequestDispatcher("");

		rd.forward(request, response);

	}

	private void registerUser(String customerName, String customerPassword,String customerNameKana, String tellNumber, String eMail,

			Date birthDate, String licenseNumber, Date licenseDate) {

		Connection con=null;

		PreparedStatement ptsmt=null;

		

		String url="jdbc:mysql:10.64.144.5:3306/23jya01";

		String user="23jya01";

		String password="23jya01";

		try {

			con=DriverManager.getConnection(url, user, password);

			ptsmt.setString(1, customerName);
			
			
			ptsmt.setString(2, customerPassword);

			ptsmt.setString(3, tellNumber);

			ptsmt.setString(4, eMail);

			ptsmt.setDate(5, (java.sql.Date) birthDate);

			ptsmt.setString(6, licenseNumber);

			ptsmt.setDate(7, (java.sql.Date) licenseDate);
			
			ptsmt.setString(8, customerNameKana);

			int affected=ptsmt.executeUpdate();

			if(affected>0) {

				System.out.println("ユーザーが正常に登録されていました");

			}else {

				System.out.println("ユーザーの登録に失敗しました");

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}finally {

			try {

				if(ptsmt!=null)ptsmt.close();

				if(con !=null)con.close();

			} catch (SQLException e) {

				// TODO 自動生成された catch ブロック

				e.printStackTrace();

			}

		}

		

	}

	private boolean islicenseNumberExists(String licenseNumberString) {

		// TODO 自動生成されたメソッド・スタブ

		return false;

	}

}