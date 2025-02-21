package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationManager {
    private Map<String, List<ReservationTime>> reservations = new HashMap<>();

    public ReservationManager() {
        // 初期データを追加する場合
        addReservation("2023-10-01", "10:00", "reserved");
        addReservation("2023-10-01", "11:00", "available");
    }

    public void addReservation(String date, String time, String status) {
        ReservationTime reservation = new ReservationTime(time, status);
        reservations.computeIfAbsent(date, k -> new ArrayList<>()).add(reservation);
    }

    public List<ReservationTime> getReservedSlots(String date) {
        return reservations.getOrDefault(date, new ArrayList<>());
    }
}
