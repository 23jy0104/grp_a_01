package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import model.LoginCustomer;

public class LoginDao {
	private final String dbUrl="jdbc:mysql://10.64.144.5:3306/23jya01";
	private final String dbId="23jya01";
	private final String dbPassword="23jya01";
	
	public List<LoginCustomer> find(){
		List<LoginCustomer> list =new ArrayList<>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		try(Connection con =DriverManager.getConnection(dbUrl, dbId, dbPassword)){
			String sql= "select * from LoginCustomer where email=? and password=?";
			
		}
		
	}
}
