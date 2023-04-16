package com.abs.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.app.dao.CustomerRepo;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepo customerRepo;

	@Override
	public List<Customer> searchCustomer(String search) {
		
		List<Customer> customerList = customerRepo.findAll();
		return customerList.stream().filter(customer -> customer.getName().contains(search)).collect(Collectors.toList());
	}

	@Override
	public void deleteCustomer(Long id) {
		// TODO Auto-generated method stub
		
		customerRepo.delete(customerRepo.getCustomerById(id));
		
	}

	@Override
	public void saveCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerRepo.save(customer);
		
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerRepo.findAll();
	}

}
