package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reservation implements Serializable {
	private String reservationId;
	private Timestamp startDate;
	private Timestamp stopDate;
	private Customer customerID;
	private Timestamp finishId;
	private int price;
	private CarData carCode;
	
	public Reservation() {
		super();
	}

	public Reservation(String reservationId, Timestamp startDate, Timestamp stopDate, Customer customerID,
			Timestamp finishId, int price, CarData carCode) {
		super();
		this.reservationId = reservationId;
		this.startDate = startDate;
		this.stopDate = stopDate;
		this.customerID = customerID;
		this.finishId = finishId;
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

	public Customer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Customer customerID) {
		this.customerID = customerID;
	}

	public Timestamp getFinishId() {
		return finishId;
	}

	public void setFinishId(Timestamp finishId) {
		this.finishId = finishId;
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
	
	

}
