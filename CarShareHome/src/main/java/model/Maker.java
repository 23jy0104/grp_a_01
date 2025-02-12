package model;
import java.io.Serializable;


public class Maker implements Serializable{
	private String makerId;
	private  String mekerName;
	
	public Maker() {
		super();
	}
	
	public Maker(String makerID, String mekerName) {
		super();
		this.makerId = makerID;
		this.mekerName = mekerName;
	}

	public String getMakerID() {
		return makerId;
	}
	public void setMakerID(String makerID) {
		this.makerId = makerID;
	}
	public String getMekerName() {
		return mekerName;
	}
	public void setMekerName(String mekerName) {
		this.mekerName = mekerName;
	}
	
}
