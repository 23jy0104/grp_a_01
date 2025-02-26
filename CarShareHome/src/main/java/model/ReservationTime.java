package model;

import java.io.Serializable;

public class ReservationTime implements Serializable {
private String startDateTime; // 開始時間
private String endDateTime;   // 終了時間
private String status;


public ReservationTime(String startDateTime, String endDateTime, String status) {
    this.startDateTime = startDateTime;
    this.endDateTime = endDateTime;
    this.status = status;
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
}