package com.abs.app.service;

import java.util.List;

import com.abs.app.model.Bill;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;
import com.abs.app.model.Reward;

public interface CustomerService {

	List<Customer> searchCustomer(String search);

	void deleteCustomer(Long id);

	void saveCustomer(Customer customer);

	List<Customer> getAllCustomers();

	Customer authenticateCustomer(Customer cus);

	Customer findCustomer(String email);

	List<Bill> getAllBills(String email);

	List<Reward> getAllRewads(String email);

	List<Bill> searchBill(String search);

}
