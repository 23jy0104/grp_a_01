package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDao {
    private Connection con = null;

    public ManagerDao() {
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

    public String getPasswordByUserId(String userId) {
        String sql = "SELECT manager_password FROM manager WHERE manager_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("manager_password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // マネージャー名を取得するメソッドを追加
    public String getManagerNameByUserId(String userId) {
        String sql = "SELECT manager_name FROM manager WHERE manager_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("manager_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // ユーザーが見つからない場合はnullを返す
    }
}
