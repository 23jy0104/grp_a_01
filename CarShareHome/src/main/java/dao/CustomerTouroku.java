package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Customer;

public class CustomerTouroku {
private Connection con=null;

	public CustomerTouroku() {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
		System.exit(1);
		}
		try {
		con=DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8","23jya01","23jya01");
		} catch (SQLException e) {
		System.out.println("はい");
		e.printStackTrace();
		System.exit(1);
		}
	
	}
	public void connectionClose() {
		try {
		con.close();
		} catch (SQLException e) {
		e.printStackTrace();
		}
	}
	public void addCustomer(Customer customer) {
		Customer cus=new Customer();
		ArrayList<Customer>ar=new ArrayList<>();
		String sql ="INSERT INTO customer(customer_Name,customer_Password,tell_Number,e_Mail,birth_Date,license_Numner,license_Date,customer_Address,omote_Jpg,ura_Jpg";
		try {
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			cus.setCustomerName(rs.getString("customerName"));
			cus.setCustomerNameKana(rs.getString("customerNameKana"));
			cus.setCustomerPassword(rs.getString("customerPassword"));
			cus.setPhoneNumber(rs.getString("phoneNumber"));
			cus.setEmail(rs.getString("email"));
			cus.setBirthDate(rs.getDate("birthDate"));
			cus.setLicenceNumber(rs.getString("licenseNumber"));
			cus.setLicenceDate(rs.getDate("licenseDate"));
			cus.setCustomerAddress(rs.getString("customerAddress"));
		}
		} catch (SQLException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
		}
		
	}
	public ArrayList<Customer> searchCustomer(String name,Date birthDate){
		ArrayList<Customer>cs =new ArrayList<>();
		try {
			String sql="select customerName,customerId,phoNumber,email,birthDate from Customer where cutomerName Like ?  AND birthDate ?";
			PreparedStatement state=con.prepareStatement(sql);
			state.setString(1, "%"+name+"%");
			state.setDate(2, new java.sql.Date(birthDate.getTime()));
			ResultSet rs= state.executeQuery();
			while(rs.next()) {
			Customer cus=new Customer();
			cus.setCustomerName(rs.getString("customerName"));
			cus.setCustomerId(rs.getString("customerId"));
			cus.setPhoneNumber(rs.getNString("phoneNumber"));
			cus.setEmail(rs.getString(rs.getString("email")));
			cus.setBirthDate(rs.getDate("birthDate"));
			cs.add(cus);
			}
		} catch (SQLException e) {
		// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}return cs;
	}
}