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
            pstmt.setString(4, customer.getPhoneNumber());
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
                cus.setPhoneNumber(rs.getString("phoneNumber"));
                cus.setEmail(rs.getString("email"));
                cus.setBirthDate(rs.getDate("birthDate"));
                cs.add(cus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cs;
    }
}
