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

    // 顧客を追加するメソッド
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customer (customer_Name, customer_Password, tell_Number, e_Mail, birth_Date, license_Number, license_Date, customer_Address, omote_Jpg, ura_Jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getCustomerPassword());
            pstmt.setString(3, customer.getPhoneNumber());
            pstmt.setString(4, customer.getEmail());
            pstmt.setDate(5, new java.sql.Date(customer.getBirthDate().getTime()));
            pstmt.setString(6, customer.getLicenceNumber());
            pstmt.setDate(7, new java.sql.Date(customer.getLicenceDate().getTime()));
            pstmt.setString(8, customer.getCustomerAddress());
            // omote_Jpgとura_Jpgの値を設定（必要に応じて）
            pstmt.setString(9, null); // 例: 画像のパスを設定
            pstmt.setString(10, null); // 例: 画像のパスを設定

            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                System.out.println("顧客が正常に追加されました。");
            } else {
                System.out.println("顧客の追加に失敗しました。");
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
                cus.setCustomerName(rs.getString("customerSei"), rs.getString("customerMei")); // 姓と名を設定
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
