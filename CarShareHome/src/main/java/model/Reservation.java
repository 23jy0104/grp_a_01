package model;

import java.io.Serializable;

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
	
	

}
