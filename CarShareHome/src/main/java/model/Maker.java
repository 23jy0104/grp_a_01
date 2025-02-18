package model;
import java.io.Serializable;


public class Maker implements Serializable{
	private String makerId;
	private  String makerName;
	
	public Maker() {
		super();
	}
	
	public Maker(String makerID, String makerName) {
		super();
		this.makerId = makerID;
		this.makerName = makerName;
	}

	public String getMakerID() {
		return makerId;
	}
	public void setMakerID(String makerID) {
		this.makerId = makerID;
	}
	public String getMekerName() {
		return makerName;
	}
	public void setMekerName(String makerName) {
		this.makerName = makerName;
	}
	
}
