package dao;

import model.Customer;

public interface UserDao {
	Customer getUserByUsername(String customerName);
    void updateUser(Customer customer);
}