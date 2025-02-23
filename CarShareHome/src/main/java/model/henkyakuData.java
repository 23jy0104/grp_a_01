package model;

public class henkyakuData {
	private String stationName;
    private String carName;
    private String number;
    private String startDate;
    
	public henkyakuData(String stationName, String carName, String number, String startDate) {
		super();
		this.stationName = stationName;
		this.carName = carName;
		this.number = number;
		this.startDate = startDate;
	}
	
	public henkyakuData() {
		super();
	}

	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getCarName() {
		return carName;
	}
	public void setCarName(String carName) {
		this.carName = carName;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
