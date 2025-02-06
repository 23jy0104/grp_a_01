package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CustomerLogin {

	public static void main(String[] args) {
		String jdbcUrl ="jdbc:mysdol://10.64.144.5:3306/23jya01";
		String dbUser ="23jya01";
		String dbPassword ="23jya01";
		
		try {
			Connection con =DriverManager.getConnection(jdbcUrl,dbUser,dbPassword);
			System.out.println("データベースに接続成功！");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("接続失敗！");
		}

	}

}
