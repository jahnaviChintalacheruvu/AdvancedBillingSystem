package com.abs.app.service;

import java.util.List;

import com.abs.app.model.Customer;
import com.abs.app.model.Employee;

public interface CustomerService {

	List<Customer> searchCustomer(String search);

	void deleteCustomer(Long id);

	void saveCustomer(Customer customer);

	List<Customer> getAllCustomers();

}
