package model;

import java.io.Serializable;

public class KeyBox implements Serializable{
	private String keyboxId;
	private Station stationId;
	private CarData carCode;
	private String failure_availability;
	
	public KeyBox() {
		super();
	}

	public KeyBox(String keyboxId, Station stationId, CarData carCode, String failure_availability) {
		super();
		this.keyboxId = keyboxId;
		this.stationId = stationId;
		this.carCode = carCode;
		this.failure_availability = failure_availability;
	}

	public String getKeyboxId() {
		return keyboxId;
	}

	public void setKeyboxId(String keyboxId) {
		this.keyboxId = keyboxId;
	}

	public Station getStationId() {
		return stationId;
	}

	public void setStationId(Station stationId) {
		this.stationId = stationId;
	}

	public CarData getCarCode() {
		return carCode;
	}

	public void setCarCode(CarData carCode) {
		this.carCode = carCode;
	}

	public String getFailure_availability() {
		return failure_availability;
	}

	public void setFailure_availability(String failure_availability) {
		this.failure_availability = failure_availability;
	}
	
	
}
