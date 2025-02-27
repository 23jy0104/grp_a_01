package model;

public class ReturnKeybox extends KeyBox {
	public String getStatusColor() {
		String result = "";
		if(getKeyboxStatus().equals("1")) {
			result = "highlight";
		}else if(getKeyboxStatus().equals("2")) {
			result = "red";
		}
		return result;
	}
}
