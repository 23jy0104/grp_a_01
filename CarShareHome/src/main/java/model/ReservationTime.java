package model;

import java.io.Serializable;

public class ReservationTime implements Serializable {
private String startTime; // 開始時間
private String endTime;   // 終了時間
private String status;


public ReservationTime(String startTime, String endTime, String status) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.status = status;
}

public String getStartTime() {
    return startTime;
}

public void setStartTime(String startTime) {
    this.startTime = startTime;
}

public String getEndTime() {
    return endTime;
}

public void setEndTime(String endTime) {
    this.endTime = endTime;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}
}