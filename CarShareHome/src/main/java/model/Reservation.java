package model;

import java.io.Serializable;

public class Reservation implements Serializable {
	
    private String reservationId;
    private String startDate;
    private String stopDate;
    private String customerId;
    private String finishDate;
    private int price;
    private CarData carCode;
    private Customer customer;
    private Station station;

	public Reservation(String reservationId, String startDate, String stopDate, String customerId,
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
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
	
	

}
