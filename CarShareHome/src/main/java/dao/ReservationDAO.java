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
import model.Reservation;
import model.Station;
import model.henkyakuData;

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
    public henkyakuData getUsage(String customerId) {
    	henkyakuData henkyaku = null;
    	String sql = "SELECT s.station_name,m.model_name,car.number,r.start_date"
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
                henkyaku = new henkyakuData();
                henkyaku.setStationName(rs.getString("station_name"));
                henkyaku.setNumber(rs.getString("number"));
                henkyaku.setCarName(rs.getString("model_name"));
                henkyaku.setStartDate(rs.getString("start_date"));
            }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return henkyaku;
    }
}
