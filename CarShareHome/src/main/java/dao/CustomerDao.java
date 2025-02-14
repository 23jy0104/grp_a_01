package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Customer;

public class CustomerDao {
    private Connection con = null;

    public CustomerDao() {
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

    // 顧客を検索するメソッド
    public ArrayList<Customer> searchCustomer(String name, Date birthDate) {
        ArrayList<Customer> cs = new ArrayList<>();
        String sql = "SELECT customerName, customerId, phoneNumber, email, birthDate FROM customer WHERE customerName LIKE ? AND birthDate = ?";
        try (PreparedStatement state = con.prepareStatement(sql)) {
            state.setString(1, "%" + name + "%");
            state.setDate(2, new java.sql.Date(birthDate.getTime()));
            ResultSet rs = state.executeQuery();
            while (rs.next()) {
                Customer cus = new Customer();
                cus.setCustomerName(rs.getString("customerName")); // 姓と名を設定
                cus.setCustomerId(rs.getString("customerId"));

                cus.settellNumber(rs.getString("tellNumber"));

                cus.settellNumber(rs.getString("phoneNumber"));
                cus.setEmail(rs.getString("email"));
                cus.setBirthDate(rs.getString("birthDate"));
                cs.add(cus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cs;
    }
}
