package model;

import java.io.Serializable;

public class KeyBox implements Serializable{
	private String keyboxId;
	private String stationId;
	private String carCode;
	private String failure_availability;
	private String keyboxStatus;
	
	public KeyBox() {
		super();
	}

	public KeyBox(String keyboxId, String stationId, String carCode, String failure_availability) {
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

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public String getFailure_availability() {
		return failure_availability;
	}

	public void setFailure_availability(String failure_availability) {
		this.failure_availability = failure_availability;
	}

	public String getKeyboxStatus() {
		return keyboxStatus;
	}

	public void setKeyboxStatus(String keyboxStatus) {
		this.keyboxStatus = keyboxStatus;
	}
	
}
