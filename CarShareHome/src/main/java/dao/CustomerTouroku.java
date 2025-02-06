package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

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
        String sql = "INSERT INTO customers (customerName, customerNameKana, customerPassword, phoneNumber, email, birthDate, licenseNumber, licenseDate, customerAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getCustomerNameKana());
            pstmt.setString(3, customer.getCustomerPassword());
            pstmt.setString(4, customer.gettellNumber());
            pstmt.setString(5, customer.getEmail());
            pstmt.setDate(6, new java.sql.Date(customer.getBirthDate().getTime()));
            pstmt.setString(7, customer.getLicenceNumber());
            pstmt.setDate(8, new java.sql.Date(customer.getLicenceDate().getTime()));
            pstmt.setString(9, customer.getCustomerAddress());

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

    public ArrayList<Customer> searchCustomer(String name, Date birthDate) {
        ArrayList<Customer> cs = new ArrayList<>();
        String sql = "SELECT customerName, customerId, phoneNumber, email, birthDate FROM customers WHERE customerName LIKE ? AND birthDate = ?";
        try (PreparedStatement state = con.prepareStatement(sql)) {
            state.setString(1, "%" + name + "%");
            state.setDate(2, new java.sql.Date(birthDate.getTime()));
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                Customer cus = new Customer();
                cus.setCustomerName(rs.getString("customerSei"),rs.getNString("customerMei"));
                cus.setCustomerId(rs.getString("customerId"));

                cus.settellNumber(rs.getString("tellNumber"));

                cus.settellNumber(rs.getString("phoneNumber"));
                cus.setEmail(rs.getString("email"));
                cus.setBirthDate(rs.getDate("birthDate"));
                cs.add(cus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cs;
    }
    public Customer getUserByUsername(String customerName) {
		Customer customer = null;
        try{
            String sql = "SELECT * FROM Customer WHERE customer_Name = ?";
            PreparedStatement sa=con.prepareStatement(sql);
            sa.setString(1, customerName);
            ResultSet rs = sa.executeQuery();
            if (rs.next()) {
            	customer = new Customer(
                        String.valueOf(rs.getInt("customer_Id")), // int型をString型に変換
                        rs.getString("customer_Name"),
                        rs.getString("customer_Password"),
                        rs.getString("tell_number"),
                        rs.getString("e_mail"),
                        sql, sql, sql, rs.getDate("birth_date"),
                        rs.getString("license_number"),
                        rs.getDate("license_date"),
                        rs.getString("customer_address"),
                        rs.getString("credit_id"),
                        rs.getString("omote_jpg"),
                        rs.getString("ura_jpg")
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
    	String sql ="select customerSei,customerMei,CustomerSeiKana,CustomerMeiKana,customerPassword,tellNumber,emai,birthDate,licenseNumber,licenseDate,customerAddress from Customer";
    	ArrayList<Customer>ary =null;
    	
    	try {
			Statement state =con.createStatement();
			ResultSet rs= state.executeQuery(sql);
			ary= new ArrayList<Customer>();
			while(rs.next()) {
				Customer one=new Customer();
				one.setCustomerName(rs.getString("customerSei"), rs.getString("customerMei"));
				one.setCustomerNameKana(rs.getString("stomerSeiKana"), rs.getString("customerMeiKana"));
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
