package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
    private static final String user = "23jya01";
    private static final String pass = "23jya01";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

}
