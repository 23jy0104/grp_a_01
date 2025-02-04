package model;
import java.sql.Connection;
import java.sql.DriverManager;
public class DataBaseUtil {

	public class DatabaseUtil {
	    private static final String URL = "jdbc:mysql://10.64.144.5:3306/23jya01"; // データベースのURL
	    private static final String USER = "23jya01"; // データベースのユーザー名
	    private static final String PASSWORD = "23jya01"; // データベースのパスワード

	    public static Connection getConnection() throws Exception {
	        return DriverManager.getConnection(URL, USER, PASSWORD);
	    }
	}

	public static Connection getConnection() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}



