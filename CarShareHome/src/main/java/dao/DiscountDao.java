package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Discount;

public class DiscountDao {
	
	private Connection con;
	
	public DiscountDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8", "23jya01", "23jya01");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public List<Discount> getDiscounts() {
    	List<Discount> discounts = new ArrayList<>();
    	String sql = "SELECT price_id, discount_price, time FROM discount";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
        	while (rs.next()) {
                String priceId = rs.getString("price_id");
                int discountPrice = rs.getInt("discount_price");
                int time = rs.getInt("time");
                discounts.add(new Discount(priceId, discountPrice, time));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
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
}
