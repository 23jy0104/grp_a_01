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
	private Connection con;

	public CustomerTouroku() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8",
					"23jya01", "23jya01");
		} catch (ClassNotFoundException | SQLException e) {
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
		String sql = "INSERT INTO customer (customer_name, customer_kana, gender, customer_password, tell_number, fixed_call, e_mail, birth_date, license_number, license_date, post_code, customer_address, omote_jpg, ura_jpg) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, customer.getCustomerName());
			pstmt.setString(2, customer.getCustomerKana());
			pstmt.setString(3, customer.getGender());
			pstmt.setString(4, customer.getCustomerPassword());
			pstmt.setString(5, customer.getTellNumber());
			pstmt.setString(6, customer.getFixedCall());
			pstmt.setString(7, customer.getEmail());
			pstmt.setString(8, customer.getBirthDate()); // String型で直接設定
			pstmt.setString(9, customer.getLicenseNumber());
			pstmt.setString(10, customer.getLicenceDate()); // String型で直接設定
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
		Customer customer = null;
		String sql = "SELECT * FROM customer WHERE customer_name = ?";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, customerName);
			ResultSet rs = pstmt.executeQuery();
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
						rs.getString("birth_date"), // String型で取得
						rs.getString("license_number"),
						rs.getString("license_date"), // String型で取得
						rs.getString("post_code"),
						rs.getString("customer_address"),
						rs.getString("credit_id"),
						rs.getBlob("omote_jpg"),
						rs.getBlob("ura_jpg"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public void updateUser(Customer customer) {

        String sql = "UPDATE Customer SET customer_password = ? WHERE customer_name = ?";
        try (PreparedStatement sa = con.prepareStatement(sql)) {
            sa.setString(1, customer.getCustomerPassword());
            sa.setString(2, customer.getCustomerName());
            sa.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public ArrayList<Customer> findAll() {
        String sql = "SELECT * FROM customers";
        ArrayList<Customer> ary = new ArrayList<>();

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Customer one = new Customer();
                one.setCustomerId(String.valueOf(rs.getInt("customer_Id")));
                one.setCustomerName(rs.getString("customer_Name"));
                one.setCustomerKana(rs.getString("customer_kana"));
                one.setCustomerPassword(rs.getString("customer_password"));
                one.setTellNumber(rs.getString("tell_number"));
                one.setFixedCall(rs.getString("fixed_call"));
                one.setEmail(rs.getString("e_mail"));
                one.setBirthDate(rs.getString("birth_date")); // String型で設定
                one.setLicenceNumber(rs.getString("license_number"));
                one.setLicenceDate(rs.getString("license_date")); // String型で設定
                one.setPostCode(rs.getString("post_code"));
                one.setCustomerAddress(rs.getString("customer_address"));
                one.setOmote(rs.getBlob("omote_jpg"));
                one.setUra(rs.getBlob("ura_jpg"));
                ary.add(one);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ary;
    }
}
