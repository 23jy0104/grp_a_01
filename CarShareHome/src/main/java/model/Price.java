package model;

import java.io.Serializable;

public class Price implements Serializable{
	private String priceId;
	private int discount;
	private int time;
	
	public Price() {
		super();
	}

	public Price(String priceId, int discount, int time) {
		super();
		this.priceId = priceId;
		this.discount = discount;
		this.time = time;
	}

	public String getPriceId() {
		return priceId;
	}

	public void setPriceId(String priceId) {
		this.priceId = priceId;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
}
