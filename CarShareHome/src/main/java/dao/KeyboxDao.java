package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.KeyBox;
import model.ReturnKeybox;

public class KeyboxDao {
	private Connection con;

    public KeyboxDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8", "23jya01", "23jya01");
        } catch (ClassNotFoundException | SQLException e) {
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
    
    public List<ReturnKeybox> getYobiKeybox(String stationId) {
    	String sql = "SELECT *  FROM keybox WHERE car_code IS NULL AND station_id = ? AND failure_availability IS NULL";
    	List<ReturnKeybox>  resultList = new ArrayList<ReturnKeybox>();

    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, stationId);
    		ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	ReturnKeybox returnKeybox = new ReturnKeybox();
            	returnKeybox.setKeyboxId(rs.getString("keybox_Id"));
            	returnKeybox.setStationId(rs.getString("station_id"));
            	returnKeybox.setCarCode(rs.getString("car_code"));
            	returnKeybox.setFailure_availability(rs.getString("failure_availability"));
            	returnKeybox.setKeyboxStatus(rs.getString("keybox_status"));
            	
            	resultList.add(returnKeybox);
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return resultList;
    	
    }
    
    public boolean updateKeyboxStatus(String stationId ,String keyboxId , String status) {
    	String sql = "UPDATE keybox SET keybox_status = ? WHERE keybox_id = ? AND station_id = ?";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, status);
    		pstmt.setString(2, keyboxId);
    		pstmt.setString(3, stationId);
    		pstmt.executeUpdate();
    		return true;
    	}catch(SQLException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    public KeyBox getKeyboxByReservationId(String reservationId) {
    	String sql = "SELECT * FROM keybox k WHERE EXISTS(SELECT * FROM reservation r WHERE r.reservation_id = ? AND r.car_code = k.car_code )";
    	KeyBox keybox = null;
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, reservationId);
    		ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	keybox = new KeyBox();
            	keybox.setKeyboxId(rs.getString("keybox_id"));
            	keybox.setStationId(rs.getString("station_id"));
            	keybox.setCarCode(rs.getString("car_code"));
            	keybox.setFailure_availability(rs.getString("failure_availability"));
            	keybox.setKeyboxStatus(rs.getString("keybox_status"));
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return keybox;
    }
    
    public KeyBox getKeyboxById(String keyboxId ,String stationId) {
    	String sql = "SELECT * FROM keybox k WHERE keybox_id = ? AND station_id = ?";
    	KeyBox keybox = null;
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, keyboxId);
    		pstmt.setString(2, stationId);
    		ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	keybox = new KeyBox();
            	keybox.setKeyboxId(rs.getString("keybox_id"));
            	keybox.setStationId(rs.getString("station_id"));
            	keybox.setCarCode(rs.getString("car_code"));
            	keybox.setFailure_availability(rs.getString("failure_availability"));
            	keybox.setKeyboxStatus(rs.getString("keybox_status"));
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return keybox;
    }
    public boolean moveKeybox(KeyBox orgKeybox ,KeyBox yobiKeybox ,String reservarionId) throws SQLException {
    	String sql = "UPDATE keybox SET car_code = NULL ,failure_availability = '×' WHERE station_id = ? AND keybox_id = ?";
    	try {
    		con.setAutoCommit(false);
    		con.setSavepoint();
    		PreparedStatement pstmt = con.prepareStatement(sql);
    		pstmt.setString(1, orgKeybox.getStationId());
    		pstmt.setString(2, orgKeybox.getKeyboxId());
    		int i = pstmt.executeUpdate();
    		if(i == 0) {
    			con.rollback();
    			//todo エラー
    			return false;
    		}
        
    		sql = "UPDATE keybox SET car_code = ? ,keybox_status = '0' WHERE station_id = ? AND keybox_id = ? AND keybox_status = '2'";
    		pstmt = con.prepareStatement(sql);
    		pstmt.setString(1, orgKeybox.getCarCode());
    		pstmt.setString(2, yobiKeybox.getStationId());
    		pstmt.setString(3, yobiKeybox.getKeyboxId());
    		
    		i = pstmt.executeUpdate();
    		if(i == 0) {
    			con.rollback();
    			//todo エラー
    			return false;
    		}
    		sql = "UPDATE reservation SET finish_date = CURRENT_TIME WHERE reservation_id = ?";
    		
    		pstmt = con.prepareStatement(sql);
    		pstmt.setString(1, reservarionId);
    		
    		i = pstmt.executeUpdate();
    		if(i == 0) {
    			con.rollback();
    			//todo エラー
    			return false;
    		}
    		con.commit();
    	}catch(SQLException e) {
    		e.printStackTrace();
    		con.rollback();
    		//todo エラー
			return false;
    	} finally {
    		con.setAutoCommit(true);
    	}

    	return true;
    }
    
    public KeyBox getKeyboxOpenYobiBox(String stationId) {
    	String sql = "SELECT * FROM keybox k WHERE station_id = ? AND keybox_status = '1'";
    	KeyBox keybox = null;
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, stationId);
    		ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
            	keybox = new KeyBox();
            	keybox.setKeyboxId(rs.getString("keybox_id"));
            	keybox.setStationId(rs.getString("station_id"));
            	keybox.setCarCode(rs.getString("car_code"));
            	keybox.setFailure_availability(rs.getString("failure_availability"));
            	keybox.setKeyboxStatus(rs.getString("keybox_status"));
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return keybox;
    }
}
