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
    
    public ArrayList<Customer> getCustomersWithoutManager() {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT customerId, customerName, customerKana, gender, customerPassword, tellNumber, fixedCall, email, birthDate, licenseNumber, licenceDate, postCode, customerAddress, creditId, omote, ura FROM customer WHERE manager_check IS NULL";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customerId"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setCustomerKana(rs.getString("customerKana"));
                customer.setGender(rs.getString("gender"));
                customer.setCustomerPassword(rs.getString("customerPassword"));
                customer.setTellNumber(rs.getString("tellNumber"));
                customer.setFixedCall(rs.getString("fixedCall"));
                customer.setEmail(rs.getString("email"));
                customer.setBirthDate(rs.getString("birthDate"));
                customer.setLicenseNumber(rs.getString("licenseNumber"));
                customer.setLicenceDate(rs.getString("licenceDate"));
                customer.setPostCode(rs.getString("postCode"));
                customer.setCustomerAddress(rs.getString("customerAddress"));
                customer.setCreditId(rs.getString("creditId"));
                customer.setOmote(rs.getBlob("omote"));
                customer.setUra(rs.getBlob("ura"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーの詳細を表示
        }
        return customers;
    }
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, customer_name, e_mail FROM customer WHERE manager_check IS NULL"; // 必要なカラムを選択

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getString("customer_id")); // カラム名が正しいか確認
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setEmail(rs.getString("e_mail"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーの詳細を表示
        }
        return customers;
    }
    public Customer getCustomerById(String customerId) {
        Customer customer = null;
        String sql = "SELECT customer_name, tell_number, e_mail, license_number, license_date, " +
                     "post_code, customer_address, omote_jpg, ura_jpg FROM customer WHERE customer_id = ?"; // 修正したカラム名を使用

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(customerId); // IDを設定
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setTellNumber(rs.getString("tell_number"));
                customer.setEmail(rs.getString("e_mail"));
                customer.setLicenseNumber(rs.getString("license_number"));
                customer.setLicenceDate(rs.getString("license_date"));
                customer.setPostCode(rs.getString("post_code"));
                customer.setCustomerAddress(rs.getString("customer_address"));
                customer.setOmote(rs.getBlob("omote_jpg")); // 画像のパスを取得
                customer.setUra(rs.getBlob("ura_jpg")); // 画像のパスを取得
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーメッセージを表示
        }
        return customer;
    }
}
