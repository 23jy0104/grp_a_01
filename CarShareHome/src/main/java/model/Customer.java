package model;
import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable{
	private String customerId;
	private String customerName;
	private String customerNameKana;
	private String customerPassword;
	private String phoneNumber;
	private String email;
	private Date birthDate;
	private String licenseNumber;
	private Date licenceDate;
	private String customerAddress;
	private String creditId;
	private String omote;
	private String ura;
	
	public Customer() {
		super();
	}

	public Customer(String customerId, String customerName, String customerNameKana,String customerPassword, String phoneNumber, String email,
			Date birthDate, String licenceNumber, Date licenceDate, String customerAddress, String creditId,
			String omote, String ura) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerNameKana=customerNameKana;
		this.customerPassword = customerPassword;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.birthDate = birthDate;
		this.licenseNumber = licenceNumber;
		this.licenceDate = licenceDate;
		this.customerAddress = customerAddress;
		this.creditId = creditId;
		this.omote = omote;
		this.ura = ura;
	}
	
	

	public String getCustomerNameKana() {
		return customerNameKana;
	}

	public void setCustomerNameKana(String customerNameKana) {
		this.customerNameKana = customerNameKana;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public void setBirthDate(Date birthDate) {
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

	public void setLicenceDate(Date licenceDate) {
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

	public String getOmote() {
		return omote;
	}

	public void setOmote(String omote) {
		this.omote = omote;
	}

	public String getUra() {
		return ura;
	}

	public void setUra(String ura) {
		this.ura = ura;
	}
	
}
