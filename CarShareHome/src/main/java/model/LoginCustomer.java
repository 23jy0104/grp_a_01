package model;

public class LoginCustomer {
 private String email;
 private String password;
 private boolean login_flag;

 
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public boolean isLogin_flag() {
	return login_flag;
}
public void setLogin_flag(boolean login_flag) {
	this.login_flag = login_flag;
}
 
}
