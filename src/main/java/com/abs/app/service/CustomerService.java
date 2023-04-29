package com.abs.app.service;

import java.util.List;

import com.abs.app.model.Bill;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;
import com.abs.app.model.Reward;

public interface CustomerService {

	List<Customer> searchCustomer(String search, String email);

	void deleteCustomer(Long id);

	void saveCustomer(Customer customer);

	List<Customer> getAllCustomers(String email);

	Customer authenticateCustomer(Customer cus);

	Customer findCustomer(String email);

	List<Bill> getAllBills(String email);

	List<Reward> getAllRewads(String email);

	List<Bill> searchBill(String search);



}
