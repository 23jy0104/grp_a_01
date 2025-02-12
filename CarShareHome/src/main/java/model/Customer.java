package model;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class Customer implements Serializable{
	private String customerId;
	private String customerName;
	private String customerKana;
	private String gender;
	private String customerPassword;
	private String tellNumber;
	private String fixedCall;
	private String email;
	private java.sql.Date birthDate;
	private String licenseNumber;
	private java.sql.Date licenceDate;
	private String postCode;
	private String customerAddress;
	private String creditId;
	private Blob omote;
	private Blob ura;
	
	public Customer() {
		super();
	}
	public Customer(String customerPassword, String email) {
		super();
		this.customerPassword = customerPassword;
		this.email = email;
	}

	public Customer(String customerId, String customerName, String customerKana, String gender, String customerPassword, String tellNumber, String fixedCall,String email, java.sql.Date birthDate,
			String licenseNumber, java.sql.Date licenceDate,String postCode, String customerAddress, String creditId, Blob omote, Blob ura) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerKana = customerKana;
		this.gender =gender;
		this.customerPassword = customerPassword;
		this.tellNumber = tellNumber;
		this.email = email;
		this.birthDate = birthDate;
		this.licenseNumber = licenseNumber;
		this.licenceDate = licenceDate;
		this.postCode =postCode;
		this.customerAddress = customerAddress;
		this.creditId = creditId;
		this.omote = omote;
		this.ura = ura;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String gettellNumber() {
		return tellNumber;
	}

	public void settellNumber(String tellNumber) {
		this.tellNumber = tellNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(java.sql.Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getLicenceNumber() {
		return licenseNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenseNumber = licenceNumber;
	}

	public Date getLicenceDate() {
		return licenceDate;
	}

	public void setLicenceDate(java.sql.Date licenceDate) {
		this.licenceDate = licenceDate;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCreditId() {
		return creditId;
	}

	public void setCreditId(String creditId) {
		this.creditId = creditId;
	}

	public Blob getOmote() {
		return omote;
	}

	public void setOmote(Blob omote) {
		this.omote = omote;
	}

	public Blob getUra() {
		return ura;
	}

	public void setUra(Blob ura) {
		this.ura = ura;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTellNumber() {
		return tellNumber;
	}
	public void setTellNumber(String tellNumber) {
		this.tellNumber = tellNumber;
	}
	public String getFixedCall() {
		return fixedCall;
	}
	public void setFixedCall(String fixedCall) {
		this.fixedCall = fixedCall;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerKana() {
		return customerKana;
	}
	public void setCustomerKana(String customerKana) {
		this.customerKana = customerKana;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
}
