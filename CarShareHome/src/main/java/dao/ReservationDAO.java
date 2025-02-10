package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Reservation;

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

    // 予約の作成
    public boolean createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (reservation_id, start_date, stop_date, customer_id, price, car_code, finish_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, reservation.getReservationId());
            pstmt.setTimestamp(2, reservation.getStartDate());
            pstmt.setTimestamp(3, reservation.getStopDate());
            pstmt.setString(4, reservation.getCustomerId());
            pstmt.setInt(5, reservation.getPrice());
            pstmt.setString(6, reservation.getCarCode());
            pstmt.setTimestamp(7, reservation.getFinishDate());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 予約の取得
    public Reservation getReservation(String reservationId) {
        String sql = "SELECT * FROM reservation WHERE reservation_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, reservationId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Reservation(
                    rs.getString("reservation_id"),
                    rs.getTimestamp("start_date"),
                    rs.getTimestamp("stop_date"),
                    rs.getString("customer_id"),
                    rs.getInt("price"),
                    rs.getString("car_code"),
                    rs.getTimestamp("finish_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 予約の更新
    public boolean updateReservation(Reservation reservation) {
        String sql = "UPDATE reservation SET start_date = ?, stop_date = ?, customer_id = ?, price = ?, car_code = ?, finish_date = ? WHERE reservation_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setTimestamp(1, reservation.getStartDate());
            pstmt.setTimestamp(2, reservation.getStopDate());
            pstmt.setString(3, reservation.getCustomerId());
            pstmt.setInt(4, reservation.getPrice());
            pstmt.setString(5, reservation.getCarCode());
            pstmt.setTimestamp(6, reservation.getFinishDate());
            pstmt.setString(7, reservation.getReservationId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 予約の削除
    public boolean deleteReservation(String reservationId) {
        String sql = "DELETE FROM reservation WHERE reservation_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, reservationId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // すべての予約を取得
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";
        try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                reservations.add(new Reservation(
                    rs.getString("reservation_id"),
                    rs.getTimestamp("start_date"),
                    rs.getTimestamp("stop_date"),
                    rs.getString("customer_id"),
                    rs.getInt("price"),
                    rs.getString("car_code"),
                    rs.getTimestamp("finish_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    // 過去の予約を取得（利用履歴）
    public List<Reservation> getPastReservations(String customerId) {
        List<Reservation> pastReservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE customer_id = ? AND finish_date IS NOT NULL";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pastReservations.add(new Reservation(
                    rs.getString("reservation_id"),
                    rs.getTimestamp("start_date"),
                    rs.getTimestamp("stop_date"),
                    rs.getString("customer_id"),
                    rs.getInt("price"),
                    rs.getString("car_code"),
                    rs.getTimestamp("finish_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pastReservations;
    }
}
