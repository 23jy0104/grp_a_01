package model;


import java.sql.Timestamp;

public class ReservationData {
    private String carCode; // 車両コード
    private Timestamp startDate; // 予約開始日時
    private Timestamp stopDate; // 予約終了日時

    // コンストラクタ
    public ReservationData() {}

    // GetterとSetter
    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
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
}
