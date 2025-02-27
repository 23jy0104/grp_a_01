package dao;

import model.Customer;

public interface UserDao {
	Customer getUserByUsername(String customerName);
	Customer getUserByCustomerId(String customerId);
    void updateUser(Customer customer);
}