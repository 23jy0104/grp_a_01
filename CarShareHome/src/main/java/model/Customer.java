package model;
import java.io.Serializable;
import java.util.Date;

public class Customer implements Serializable{
	private String customerId;
	private String customerSei;
	private String customerMei;
	private String customerSeiKana;
	private String customerMeiKana;
	private String customerPassword;
	private String tellNumber;
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
	public Customer(String customerPassword, String email) {
		super();
		this.customerPassword = customerPassword;
		this.email = email;
	}

	public Customer(String customerId, String customerSei, String customerMei, String customerSeiKana,
			String customerMeiKana, String customerPassword, String tellNumber, String email, Date birthDate,
			String licenseNumber, Date licenceDate, String customerAddress, String creditId, String omote, String ura) {
		super();
		this.customerId = customerId;
		this.customerSei = customerSei;
		this.customerMei = customerMei;
		this.customerSeiKana = customerSeiKana;
		this.customerMeiKana = customerMeiKana;
		this.customerPassword = customerPassword;
		this.tellNumber = tellNumber;
		this.email = email;
		this.birthDate = birthDate;
		this.licenseNumber = licenseNumber;
		this.licenceDate = licenceDate;
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

	public void setPhoneNumber(String tellNumber) {
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

	public String getCustomerSei() {
		return customerSei;
	}

	public void setCustomerSei(String customerSei) {
		this.customerSei = customerSei;
	}

	public String getCustomerMei() {
		return customerMei;
	}

	public void setCustomerMei(String customerMei) {
		this.customerMei = customerMei;
	}

	public String getCustomerSeiKana() {
		return customerSeiKana;
	}

	public void setCustomerSeiKana(String customerSeiKana) {
		this.customerSeiKana = customerSeiKana;
	}

	public String getCustomerMeiKana() {
		return customerMeiKana;
	}

	public void setCustomerMeiKana(String customerMeiKana) {
		this.customerMeiKana = customerMeiKana;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	public String getCustomerName() {
		return customerSei +""+ customerMei;
	}
	public String getCustomerNameKana() {
		return customerSeiKana +""+customerMeiKana;
	}
	public void setCustomerName(String customerSei, String customerMei) {
        this.customerSei = customerSei;
        this.customerMei = customerMei;
    }
	public void setCustomerNameKana(String customerSeiKana,String customerMeiKana) {
		this.customerSeiKana = customerSeiKana;
        this.customerMeiKana = customerMeiKana;
	}
}
