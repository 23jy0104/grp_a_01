package model;

import java.io.Serializable;

public class Station implements Serializable{
	private String stationId;
	private String stationName;
	private String stationAddress;
	private String stationData;
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	public Station() {
		super();
	}
	public Station(String stationId, String stationName) {
        this.stationId = stationId;
        this.stationName = stationName;
    }
	public Station(String stationId,String stationName, String stationAddress,String stationData) {
		super();
		this.stationId=stationId;
		this.stationName = stationName;
		this.stationAddress = stationAddress;
		this.stationData=stationData;
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
	public String getStationData() {
		return stationData;
	}
	public void setStationData(String stationData) {
		this.stationData = stationData;
	}
	
}

