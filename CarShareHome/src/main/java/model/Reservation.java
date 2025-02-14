package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reservation implements Serializable {
	
    private String reservationId;
    private Timestamp startDate;
    private Timestamp stopDate;
    private String customerId;
    private Timestamp finishDate;
    private int price;
    private CarData carCode;
    private Customer customer;
    private Station station;

    public Reservation() {
        super();
    }

    // 修正されたコンストラクタ
    public Reservation(String reservationId, Timestamp startDate, Timestamp stopDate, Station station,
                       Customer customer, Timestamp finishDate, int price, CarData carCode) {
        super();
        this.reservationId = reservationId;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.station = station; // ステーション情報を設定
        this.customer = customer; // 顧客情報を設定
        this.customerId = customer.getCustomerId(); // 顧客IDを設定
        this.finishDate = finishDate;
        this.price = price;
        this.carCode = carCode;
    }

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

    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
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
        this.customerId = customer.getCustomerId(); // 顧客IDを更新
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
