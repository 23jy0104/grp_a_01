package model;

import java.sql.Timestamp;

public class Reservation {
    private String reservationId;  // 予約ID
    private Timestamp startDate;    // 開始日時
    private Timestamp stopDate;     // 終了日時
    private String customerId;       // 顧客ID
    private int price;               // 価格
    private String carCode;          // 車コード
    private Timestamp finishDate;    // 完了日時（過去の履歴用）

    // コンストラクタ
    public Reservation(String reservationId, Timestamp startDate, Timestamp stopDate, String customerId, int price, String carCode, Timestamp finishDate) {
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.customerId = customerId;
        this.price = price;
        this.carCode = carCode;
        this.finishDate = finishDate;
    }

    // ゲッターとセッター
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getStopDate() {
        return stopDate;
    }

    public void setStopDate(Timestamp stopDate) {
        this.stopDate = stopDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }
}
