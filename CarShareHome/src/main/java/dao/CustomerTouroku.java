package dao;

import model.Customer;
import java.sql.*;
import java.util.ArrayList;

public class CustomerTouroku implements UserDao {
    private Connection con;

    public CustomerTouroku() {
        String jdbcUrl = "jdbc:mysql://10.64.144.5:3306/23jya01"; // 修正
        String dbUser = "23jya01";
        String dbPassword = "23jya01";
        
        try {
            con = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword); // conをインスタンス変数に保存
            System.out.println("データベースに接続成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("接続失敗！");
        }
    }

    public void connectionClose() {
        try {
            if (con != null) {
                con.close();
                System.out.println("データベース接続を閉じました。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) {
        if (con == null) {
            System.out.println("データベース接続が確立されていません。");
            return;
        }

        String sql = "INSERT INTO customers (customer_name, gender, customer_password, tell_number, fixed_call, e_mail, birth_date, license_number, license_date, post_code, customer_address, omote_jpg, ura_jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCustomerName());
            pstmt.setString(2, customer.getCustomerKana());
            pstmt.setString(3, customer.getGender());
            pstmt.setString(4, customer.getCustomerPassword());
            pstmt.setString(5, customer.gettellNumber());
            pstmt.setString(6, customer.getFixedCall());
            pstmt.setString(7, customer.getEmail());
            pstmt.setDate(8, new java.sql.Date(customer.getBirthDate().getTime()));
            pstmt.setString(9, customer.getLicenceNumber());
            pstmt.setDate(10, new java.sql.Date(customer.getLicenceDate().getTime()));
            pstmt.setString(11, customer.getPostCode());
            pstmt.setString(12, customer.getCustomerAddress());
            pstmt.setBlob(13, customer.getOmote());
            pstmt.setBlob(14, customer.getUra());

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
        if (con == null) {
            System.out.println("データベース接続が確立されていません。");
            return null;
        }

        Customer customer = null;
        String sql = "SELECT * FROM Customer WHERE customer_name = ?";
        try (PreparedStatement sa = con.prepareStatement(sql)) {
            sa.setString(1, customerName);
            ResultSet rs = sa.executeQuery();
            if (rs.next()) {
                customer = new Customer(
                    String.valueOf(rs.getInt("customer_Id")),
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

    public void updateUser(Customer customer) {
        if (con == null) {
            System.out.println("データベース接続が確立されていません。");
            return;
        }

        String sql = "UPDATE Customer SET customer_password = ? WHERE customer_Name = ?";
        try (PreparedStatement sa = con.prepareStatement(sql)) {
            sa.setString(1, customer.getCustomerPassword());
            sa.setString(2, customer.getCustomerName());
            sa.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Customer> findAll() {
        if (con == null) {
            System.out.println("データベース接続が確立されていません。");
            return new ArrayList<>();
        }

        String sql = "SELECT customer_name, customer_kana, customerPassword, tellNumber, email, birthDate, licenseNumber, licenseDate, customerAddress, omote_jpg, ura_jpg FROM Customer";
        ArrayList<Customer> ary = new ArrayList<>();
        
        try (Statement state = con.createStatement(); ResultSet rs = state.executeQuery(sql)) {
            while (rs.next()) {
                Customer one = new Customer();
                one.setCustomerName(rs.getString("customer_name"));
                one.setCustomerKana(rs.getString("customer_kana"));
                one.setCustomerPassword(rs.getString("customerPassword"));
                one.settellNumber(rs.getString("tellNumber"));
                one.setEmail(rs.getString("email"));
                one.setBirthDate(rs.getDate("birthDate"));
                one.setLicenceNumber(rs.getString("licenseNumber"));
                one.setLicenceDate(rs.getDate("licenseDate"));
                one.setCustomerAddress(rs.getString("customerAddress"));
                ary.add(one);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ary;
    }
}
