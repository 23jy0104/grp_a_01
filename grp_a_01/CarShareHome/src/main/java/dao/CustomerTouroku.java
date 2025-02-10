
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Customer;

public class CustomerTouroku implements UserDao {
    private Connection con = null;

    public CustomerTouroku() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8", "23jya01", "23jya01");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void connectionClose() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_name,gender, customer_password, tell_number,fixed_call, e_mail, birth_date, license_number, license_date,license_date,post_code,customer_address,omote_jpg,ura_jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
        	pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getCustomerKana());
            pstmt.setString(3, customer.getGender());
            pstmt.setString(4, customer.getCustomerPassword());
            pstmt.setString(5, customer.gettellNumber());
            //if(fiexed !=null){
            pstmt.setString(6,customer.getFixedCall());//}閉じる
            pstmt.setString(7, customer.getEmail());
            pstmt.setDate(8, new java.sql.Date(customer.getBirthDate().getTime()));
            pstmt.setString(9, customer.getLicenceNumber());
            pstmt.setDate(10, new java.sql.Date(customer.getLicenceDate().getTime()));
            pstmt.setString(11, customer.getPostCode());
            pstmt.setString(12, customer.getPostCode());
            pstmt.setString(13, customer.getCustomerAddress());
            pstmt.setBlob(14, customer.getOmote());
            pstmt.setBlob(15, customer.getUra());

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("ユーザーが正常に追加されました");
            } else {
                System.out.println("ユーザーの追加に失敗しました");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getUserByUsername(String customerName) {
		Customer customer = null;
        try{
            String sql = "SELECT * FROM Customer WHERE customer_name = ?";
            PreparedStatement sa=con.prepareStatement(sql);
            sa.setString(2, customerName);
            ResultSet rs = sa.executeQuery();
            if (rs.next()) {
            	customer = new Customer(
                        String.valueOf(rs.getInt("customer_Id")), // int型をString型に変換
                        rs.getString("customer_Name"),
                        rs.getString("customer_kana"),
                        rs.getString("gender"),
                        rs.getString("customer_Password"),
                        rs.getString("tell_number"),
                        rs.getString("fixed_call"),
                        rs.getString("e_mail"),
                        rs.getDate("birth_date"),
                        rs.getString("license_number"),
                        rs.getDate("license_date"),
                        rs.getString("post_code"),
                        rs.getString("customer_address"),
                        rs.getString("credit_id"),
                        rs.getBlob("omote_jpg"),
                        rs.getBlob("ura_jpg")
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }


    public void updateUser(Customer customerId) {
        try{
        	String sql = "UPDATE Customer SET customer_password = ? WHERE customer_Name = ?";
        	PreparedStatement sa=con.prepareStatement(sql);
            sa.setString(1, customerId.getCustomerPassword());
            sa.setString(2, customerId.getCustomerName());
            sa.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  ArrayList<Customer>findAll(){
    	String sql ="select customer_name,customer_kana,customerPassword,tellNumber,emai,birthDate,licenseNumber,licenseDate,customerAddress,omote_jpg,ura_jpg from Customer";
    	ArrayList<Customer>ary =null;
    	
    	try {
			Statement state =con.createStatement();
			ResultSet rs= state.executeQuery(sql);
			ary= new ArrayList<Customer>();
			while(rs.next()) {
				Customer one=new Customer();
				one.setCustomerName(rs.getString("customerName"));
				one.setCustomerKana(rs.getString("customerKana"));
				one.setCustomerPassword(rs.getString("customerPassword"));
				one.settellNumber(rs.getString("tellNumner"));
				one.setEmail(rs.getString("email"));
				one.setBirthDate(rs.getDate("birthDate"));
				one.setLicenceNumber(rs.getString("licenseNumber"));
				one.setLicenceDate(rs.getDate("locenseDate"));
				one.setCustomerAddress("customerAddress");
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	return ary;
    	
    }

}
