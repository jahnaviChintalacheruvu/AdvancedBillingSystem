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

public Customer authenticateCustomer(Customer customer) {
		
		
		
		List<Customer> customers = customerRepo.findAll();
		List<Customer> veifiedCustomer = customers.stream().filter(n -> (n.getEmail().equals(customer.getEmail()) || n.getName().equals(customer.getEmail())) && n.getPassword().equals(customer.getPassword())).collect(Collectors.toList());
		
		if(veifiedCustomer.size() ==1) {
			return veifiedCustomer.get(0);
		}
		else {
			return null;
		}
			
	}

@Override
public Customer findCustomer(String email) {
	// TODO Auto-generated method stub
	return customerRepo.findbyEmail(email);
}

}
