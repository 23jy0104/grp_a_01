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

public class ReservationDAO {
    private Connection con = null;

    public ReservationDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("JDBC Driver loaded."); // ドライバ読み込みログ
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://10.64.144.5:3306/23jya01?characterEncoding=UTF-8", "23jya01", "23jya01");
            System.out.println("Database connection established."); // データベース接続成功ログ
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void connectionClose() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Database connection closed."); // 接続終了ログ
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
                    String customerIdFromDb = rs.getString("customer_id");
                    String customerName = rs.getString("customer_name");

                    // Customerオブジェクトを作成
                    Customer customer = new Customer(customerIdFromDb);
                    customer.setCustomerName(customerName); // customerNameを後から設定

                    // Reservationオブジェクトを作成
                    Reservation reservation = new Reservation(
                        rs.getString("reservation_id"),
                        rs.getTimestamp("start_date"),
                        rs.getTimestamp("stop_date"),
                        new Station(rs.getString("station_id"), rs.getString("station_name")),
                        customer,
                        rs.getTimestamp("finish_date"),
                        rs.getInt("price"),
                        new CarData(rs.getString("car_code"))
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


}
