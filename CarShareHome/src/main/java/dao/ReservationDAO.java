package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.CarData;
import model.Customer;
import model.HenkyakuData;
import model.Reservation;
import model.Station;

public class ReservationDAO {
    private Connection con = null;

    public ReservationDAO() {
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

    public List<Reservation> getAllReservations(String customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, c.customer_id, c.customer_name, s.station_name, s.station_id " +
                     "FROM reservation r " +
                     "JOIN customer c ON r.customer_id = c.customer_id " +
                     "JOIN keybox kb ON r.car_code = kb.car_code " +
                     "JOIN station s ON kb.station_id = s.station_id " +
                     "WHERE r.customer_id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customerId); // customerIdを設定
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String reservationId = rs.getString("reservation_id");
                    String startDate = rs.getString("start_date");
                    String stopDate = rs.getString("stop_date");
                    Station station = new Station(rs.getString("station_id"), rs.getString("station_name"));
                    Customer customer = new Customer(rs.getString("customer_id"));
                    customer.setCustomerName(rs.getString("customer_name")); // customerNameを設定
                    String finishDate = rs.getString("finish_date");
                    int price = rs.getInt("price");
                    CarData carData = new CarData(rs.getString("car_code"));

                    // Reservationオブジェクトを作成
                    Reservation reservation = new Reservation(
                        reservationId,
                        startDate,
                        stopDate,
                        customer,
                        station,
                        finishDate,
                        price,
                        carData
                    );

                    reservations.add(reservation);
                    System.out.println("予約ID: " + reservation.getReservationId() + ", 顧客名: " + customer.getCustomerName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("取得した予約数: " + reservations.size());
        return reservations;
    }
    
    public List<Reservation> getReservationsWithFinishDate(String customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, c.customer_id, c.customer_name, s.station_name, s.station_id " +
                     "FROM reservation r " +
                     "JOIN customer c ON r.customer_id = c.customer_id " +
                     "JOIN keybox kb ON r.car_code = kb.car_code " +
                     "JOIN station s ON kb.station_id = s.station_id " +
                     "WHERE r.customer_id = ? AND r.finish_date IS NOT NULL";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customerId); // customerIdを設定
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String reservationId = rs.getString("reservation_id");
                    String startDate = rs.getString("start_date");
                    String stopDate = rs.getString("stop_date");
                    Station station = new Station(rs.getString("station_id"), rs.getString("station_name"));
                    Customer customer = new Customer(rs.getString("customer_id"));
                    customer.setCustomerName(rs.getString("customer_name")); // customerNameを設定
                    String finishDate = rs.getString("finish_date");
                    int price = rs.getInt("price");
                    CarData carData = new CarData(rs.getString("car_code"));

                    // Reservationオブジェクトを作成
                    Reservation reservation = new Reservation(
                        reservationId,
                        startDate,
                        stopDate,
                        customer,
                        station,
                        finishDate,
                        price,
                        carData
                    );

                    reservations.add(reservation);
                    System.out.println("予約ID: " + reservation.getReservationId() + ", 顧客名: " + customer.getCustomerName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        System.out.println("取得した予約数: " + reservations.size());
        return reservations;
    }
    public HenkyakuData getUsage(String customerId) {
    	HenkyakuData henkyaku = null;
    	String sql = "SELECT s.station_name,m.model_name,car.number,r.start_date ,k.keybox_id ,k.station_id ,reservation_id"
	    			+ " FROM customer AS c"
	    			+ " INNER JOIN reservation AS r"
	    			+ " ON c.customer_id = r.customer_id"
	    			+ " INNER JOIN car_db AS car"
	    			+ " ON r.car_code = car.car_code"
	    			+ " INNER JOIN keybox AS k"
	    			+ " ON car.car_code = k.car_code"
	    			+ " INNER JOIN station AS s"
	    			+ " ON k.station_id = s.station_id"
	    			+ " INNER JOIN model AS m"
	    			+ " ON car.model_id = m.model_id"
	    			+ " WHERE c.customer_id = ?"
	    			+ " AND r.finish_date IS NULL;";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, customerId);
    		ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                henkyaku = new HenkyakuData();
                henkyaku.setStationName(rs.getString("station_name"));
                henkyaku.setNumber(rs.getString("number"));
                henkyaku.setCarName(rs.getString("model_name"));
                henkyaku.setStartDate(rs.getString("start_date"));
                henkyaku.setStationId(rs.getString("station_id"));
                henkyaku.setKeyboxId(rs.getString("keybox_id"));
                henkyaku.setReservationId(rs.getString("reservation_id"));
                
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return henkyaku;
    }
    public HenkyakuData getUsageById(String reservationId) {
    	HenkyakuData henkyaku = null;
    	String sql = "SELECT s.station_name,m.model_name,car.number,r.start_date ,k.keybox_id ,k.station_id ,reservation_id ,r.time_date ,r.finish_date"
	    			+ " FROM reservation AS r"
	    			+ " INNER JOIN car_db AS car"
	    			+ " ON r.car_code = car.car_code"
	    			+ " INNER JOIN keybox AS k"
	    			+ " ON car.car_code = k.car_code"
	    			+ " INNER JOIN station AS s"
	    			+ " ON k.station_id = s.station_id"
	    			+ " INNER JOIN model AS m"
	    			+ " ON car.model_id = m.model_id"
	    			+ " WHERE r.reservation_id = ?";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, reservationId);
    		ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                henkyaku = new HenkyakuData();
                henkyaku.setStationName(rs.getString("station_name"));
                henkyaku.setNumber(rs.getString("number"));
                henkyaku.setCarName(rs.getString("model_name"));
                henkyaku.setStartDate(rs.getString("start_date"));
                henkyaku.setStationId(rs.getString("station_id"));
                henkyaku.setKeyboxId(rs.getString("keybox_id"));
                henkyaku.setReservationId(rs.getString("reservation_id"));
                henkyaku.setTimeDate(rs.getString("time_date"));
                henkyaku.setFinishDate(rs.getString("finish_date"));
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return henkyaku;
    }
    
    public boolean setReservation(String startDate ,String endDate ,String customerId ,Integer totalCost ,String carCode) {
    		boolean yoyaku = false;
    	String sql = "INSERT INTO reservation(start_date,stop_date,customer_id,time_date,finish_date,price,car_code)"
    			+ "VALUE"
    			+ "(?,?,?,null,null,?,?);";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, startDate);
    		pstmt.setString(2, endDate);
    		pstmt.setString(3, customerId);
    		pstmt.setInt(4, totalCost);
    		pstmt.setString(5, carCode);
    		pstmt.executeUpdate();
        	yoyaku = true;
        	System.out.println("予約が追加されました。");
    	}catch(SQLException e) {
    		e.printStackTrace();
    		System.out.println("予約が追加できませんでした。");
    	}
    	return yoyaku;
    }
    public List<Reservation> Reservationkakunin(String customerId) {
    	
    	 List<Reservation> yoyakuzumi = new ArrayList<>();
    	String sql ="SELECT reservation_id, start_date, stop_date, station_name, model_name, number, reservation_time, price,s.station_id,r.customer_id,r.car_code "
    	           + "FROM reservation r "
    	           + "INNER JOIN car_db car ON car.car_code = r.car_code "
    	           + "INNER JOIN keybox k ON k.car_code = r.car_code "
    	           + "INNER JOIN station s ON s.station_id = k.station_id "
    	           + "INNER JOIN model m ON m.model_id = car.model_id "
    	           + "WHERE customer_id = ? AND time_date IS NULL AND finish_date IS NULL";

    	try {
			PreparedStatement pstmt = con.prepareStatement(sql) ;
			pstmt.setString(1, customerId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String reservationId=rs.getString("reservation_id");
				String startDate=rs.getString("start_date");
				String stopDate=rs.getString("stop_date");
				Station station = new Station(rs.getString("station_name"));
				String modelName=rs.getString("model_name");				
				String number=rs.getString("number");
				String reservationTime=rs.getString("reservation_time");
				int price=rs.getInt("price");
				Customer customer = new Customer(rs.getString("customer_id"));
				CarData carData = new CarData(rs.getString("car_code"));
				Reservation list =new Reservation(
						reservationId,
						startDate,
						stopDate,
						customer,
						price,
						carData,
						station,
						number,
						reservationTime,
						modelName
						);
				yoyakuzumi.add(list);
			}
			
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	return yoyakuzumi;
    
    }
    
    public String getLastReservationId(String customerId ,String carCode) {
    	String sql = "SELECT MAX(reservation_id) AS reservation_id FROM reservation WHERE customer_id = ? AND car_code = ? AND time_date IS NULL AND finish_date IS NULL;";
    	String reservationId = null;
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, customerId);
    		pstmt.setString(2, carCode);
    		ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                reservationId = rs.getString("reservation_id");
                
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return reservationId;
    }
    public boolean setTimeDate(String reservationId) {
    	boolean time = false;
    	String sql = "UPDATE reservation SET time_date = CURRENT_TIME WHERE station_id = ?;";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, reservationId);
         time = true;
        	System.out.println("利用が開始されました。");
    	}catch(SQLException e) {
    		e.printStackTrace();
    		System.out.println("利用が開始されていません");
    	}
    	return time;
    }
    
    public boolean setFinishDate(String reservationId) {
    	boolean time = false;
    	String sql = "UPDATE reservation SET finish_date = CURRENT_TIME WHERE station_id = ?;";
    	try(PreparedStatement pstmt = con.prepareStatement(sql)) {
    		pstmt.setString(1, reservationId);
         time = true;
        	System.out.println("利用が終了ました。");
    	}catch(SQLException e) {
    		e.printStackTrace();
    		System.out.println("利用が終了していません");
    	}
    	return time;
    }
}
