package dao;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBconfig;
import model.LoginCustomer;

public class LoginSql {
  public LoginCustomer check(String email,String password) throws FileNotFoundException{
	  
	  DBconfig db_info =new DBconfig();
	  String url =db_info.getDBinfo().get("url");
	  String user = db_info.getDBinfo().get("user");
	  String pass =db_info.getDBinfo().get("password");
	  
	  String login_sql ="select * from LoginCustomer where email=? and password =?";
	  
	  LoginCustomer login = new LoginCustomer();
	  
	  try {
		Connection con =DriverManager.getConnection(url, user, pass);
		  PreparedStatement stmt =con.prepareStatement(login_sql);
		  
		  stmt.setString(1, login.getEmail());
		  stmt.setString(2, login.getPassword());
		  
		  ResultSet rs= stmt.executeQuery();
		  
		  if(rs.next()) {
			  login.setEmail(rs.getString("email"));
			  login.setPassword(rs.getString("password"));
			  login.setLogin_flag(true);
		  }else {
			  login.setLogin_flag(false);
		  }
	} catch (SQLException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
	  return login;
  }
}
