package model;

import java.io.Serializable;

public class Management implements Serializable{
	private String managementId;
	private String managementPassword;
	
	public Management() {
		super();
	}

	public Management(String managementId, String managementPassword) {
		super();
		this.managementId = managementId;
		this.managementPassword = managementPassword;
	}

	public String getManagementId() {
		return managementId;
	}

	public void setManagementId(String managementId) {
		this.managementId = managementId;
	}

	public String getManagementPassword() {
		return managementPassword;
	}

	public void setManagementPassword(String managementPassword) {
		this.managementPassword = managementPassword;
	}
	
	
	
}
