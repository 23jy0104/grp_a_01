package model;

import java.io.Serializable;

public class ReservationTime implements Serializable {
private String startDateTime; // 開始時間
private String endDateTime;   // 終了時間
private String status;
private String modelName;
private String carImage;
private String carCode;


public ReservationTime(String startDateTime, String endDateTime, String status) {
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.status = status;
}



public ReservationTime(String startDateTime, String endDateTime, String status,String modelName,String carImage,
						String carCode){
	this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.status = status;
    this.modelName=modelName;
    this.carImage =carImage;
    this.carCode =carCode;
	
}



public String getStartDateTime() {
    return startDateTime;
}

public void setStartDateTime(String startDateTime) {
    this.startDateTime = startDateTime;
}

public String getEndDateTime() {
    return endDateTime;
}

public void setEndDateTime(String endDateTime) {
    this.endDateTime = endDateTime;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}



public String getModelName() {
	return modelName;
}



public void setModelName(String modelName) {
	this.modelName = modelName;
}



public String getCarImage() {
	return carImage;
}



public void setCarImage(String carImage) {
	this.carImage = carImage;
}



public String getCarCode() {
	return carCode;
}



public void setCarCode(String carCode) {
	this.carCode = carCode;
}


}
