package com.abs.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.app.dao.BillRepo;
import com.abs.app.dao.CustomerRepo;
import com.abs.app.dao.RewardRepo;
import com.abs.app.model.Bill;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;
import com.abs.app.model.Reward;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private RewardRepo rewardRepo;

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

@Override
public List<Bill> getAllBills(String email) {
	// TODO Auto-generated method stub
	return billRepo.findAll().stream().filter(bill -> bill.getCustomerMail().equals(email)).collect(Collectors.toList());
}

@Override
public List<Reward> getAllRewads(String email) {
	// TODO Auto-generated method stub
	return rewardRepo.findAll().stream().filter(re-> re.getCustomerMail().equals(email)).collect(Collectors.toList());
}

@Override
public List<Bill> searchBill(String search) {
	// TODO Auto-generated method stub
	List<Bill> billList = billRepo.findAll();
	
	
	
	return billList.stream().filter(emp -> emp.getTotalCost().equals(search) || emp.getNoOfItems().equals(search)).collect(Collectors.toList());
}

}
