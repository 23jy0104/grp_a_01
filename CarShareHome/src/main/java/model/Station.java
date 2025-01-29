package model;

import java.io.Serializable;

public class Station implements Serializable{
	private String stationId;
	private String stationName;
	private String stationAddress;
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	public Station() {
		super();
	}
	public Station(String stationId,String stationName, String stationAddress) {
		super();
		this.stationId=stationId;
		this.stationName = stationName;
		this.stationAddress = stationAddress;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationAddress() {
		return stationAddress;
	}
	public void setStationAddress(String stationAddress) {
		this.stationAddress = stationAddress;
	}
}

