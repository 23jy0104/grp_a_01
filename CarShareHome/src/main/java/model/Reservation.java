package model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Reservation implements Serializable {
	
    private String reservationId;
    private String startDate;
    private String stopDate;
    private Customer customerId;
    private String finishDate;
    private int price;
    private CarData carCode;
    private Customer customer;
    private Station station;
    private String number;
    private String reservationTime;
    private String modelName;

    
    
	public Reservation() {
		super();
	}
	public Reservation(String reservationId, String startDate, String stopDate, Customer customerId,
			String finishId, int price, CarData carCode) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.customerId = customerId;
		this.finishDate = finishId;
		this.price = price;
		this.carCode = carCode;
	}
	public Reservation(String reservationId, String startDate, String stopDate, Customer customer,
            Station station, String finishDate, int price, CarData carCode) {
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.customer = customer;
		this.station = station;
		this.finishDate = finishDate;
		this.price = price;
		this.carCode = carCode;
	}
	
	

	public Reservation(String reservationId, String startDate, String stopDate, Customer customerId, int price,
			CarData carCode, Station station, String number, String reservationTime, String modelName) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.customerId = customerId;
		this.price = price;
		this.carCode = carCode;
		this.station = station;
		this.number = number;
		this.reservationTime =reservationTime;
		this.modelName=modelName;
	}
	public Reservation(String reservationId, String startDate, String stopDate, Customer customerId, int price,
			Customer customer, Station station) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.customerId = customerId;
		this.price = price;
		this.customer = customer;
		this.station = station;
	}
	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishId(String finishDate) {
		this.finishDate = finishDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public CarData getCarCode() {
		return carCode;
	}

	public void setCarCode(CarData carCode) {
		this.carCode = carCode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getReservationTime() {
		return reservationTime;
	}
	public void setReservationTime(String reservationTime) {
		this.reservationTime = reservationTime;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}
	
	public static class ReservationManager {
	    private static List<Reservation> reservations;

	    public ReservationManager(List<Reservation> reservations) {
	        setReservations(reservations);
	    }

	    public static boolean isTimeSlotAvailableForOtherCustomers(String customerId, String carCode, String startDateTime, String endDateTime) {
	        String sql = "SELECT * FROM Reservation WHERE car_code = ? AND customer_id != ? AND "
	                   + "(start_date < ? AND stop_date > ?);";

	        // SQLを実行して予約を取得する処理を追加
	        List<Reservation> reservations = executeQuery(sql, carCode, customerId, endDateTime, startDateTime);
	        
	        // 予約時間の重複チェック
	        return reservations.isEmpty(); // 予約がなければ重複していない
	    }

	    private static List<Reservation> executeQuery(String sql, String carCode, String customerId, String endDateTime,
	                                                  String startDateTime) {
	        List<Reservation> reservations = new ArrayList<>();
	        String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
	        String user = "23jya01";
	        String pass = "23jya01";
	        
	        try (Connection connection = DriverManager.getConnection(url, user, pass);
	             PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setString(1, carCode);
	            pstmt.setString(2, customerId);
	            pstmt.setString(3, endDateTime);
	            pstmt.setString(4, startDateTime);
	            
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    Reservation reservation = new Reservation();
	                    reservation.setCustomerId(new Customer(rs.getString("customer_id")));
	                    reservation.setCarCode(new CarData(rs.getString("car_code")));
	                    reservation.setStartDate(rs.getString("start_date"));
	                    reservation.setStopDate(rs.getString("stop_date"));
	                    reservations.add(reservation);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); // エラーハンドリング
	        }
	        
	        return reservations;
	    }

	    public static void changeReservation(String reservationId, String startDateTime, String endDateTime, int price) {
	        String url = "jdbc:mysql://10.64.144.5:3306/23jya01";
	        String user = "23jya01";
	        String pass = "23jya01";			
	        
	        String sql = "UPDATE reservation SET start_date = ?, stop_date = ?, price = ? WHERE reservation_id = ?";    	
	        
	        try (Connection con = DriverManager.getConnection(url, user, pass);
	             PreparedStatement statement = con.prepareStatement(sql)) {
	            statement.setString(1, startDateTime);
	            statement.setString(2, endDateTime);
	            statement.setInt(3, price);
	            statement.setString(4, reservationId);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace(); // エラーハンドリング
	        }
	    }

	    public static List<Reservation> getReservations() {
	        return reservations;
	    }

	    public static void setReservations(List<Reservation> reservations) {
	        ReservationManager.reservations = reservations;
	    }
	}
	
	

}
