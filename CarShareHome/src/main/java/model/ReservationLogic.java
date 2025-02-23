package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationLogic {

    private static final String DB_URL = "jdbc:mysql://10.64.144.5:3306/23jya01?useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "23jya01";
    private static final String DB_PASS = "23jya01";

    public List<ReservationTime> generateAvailableSlots(String selectedDate) {
        List<ReservationTime> availableSlots = new ArrayList<>();

        // 例: 9:00から21:00までの1時間単位のスロットを作成
        for (int hour = 0; hour <= 23; hour++) {
            String startTime = String.format("%02d:00", hour);
            String endTime = String.format("%02d:00", hour + 1);
            String status = "available"; // 初期状態は「予約可能」

            // 予約状況をチェック
            if (isReserved(selectedDate, hour)) {
                status = "reserved"; // 予約されている場合は「予約済み」
            }

            availableSlots.add(new ReservationTime(startTime, endTime, status)); // ReservationTimeオブジェクトを追加
        }

        return availableSlots; // リストを返す
    }

    private boolean isReserved(String selectedDate, int hour) {
        boolean reserved = false;
        String startDateTime = selectedDate + String.format(" %02d:00:00", hour);
        String endDateTime = selectedDate + String.format(" %02d:59:59", hour);

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = con.prepareStatement(
                     "SELECT COUNT(*) FROM Reservation WHERE start_date <= ? AND stop_date >= ?")) {
            pstmt.setString(1, endDateTime);
            pstmt.setString(2, startDateTime);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                reserved = true; // 予約が存在する場合
            }
        } catch (SQLException e) {
            e.printStackTrace(); // エラーログを出力
        }

        return reserved;
    }
}
