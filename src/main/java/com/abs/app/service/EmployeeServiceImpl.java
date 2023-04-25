package com.abs.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.app.dao.EmployeeRepo;
import com.abs.app.model.Admin;
import com.abs.app.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeRepo employeeRepo;

	@Override
	public List<Employee> getAllEmployees() {
		
		return employeeRepo.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeRepo.save(employee);
	}

	@Override
	public Employee getEmployeeById(Long id) {
		// TODO Auto-generated method stub
		return employeeRepo.getEmployeeById(id);
	}

	@Override
	public void updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeRepo.save(employee);
		
	}

	@Override
	public void deleteEmployee(Long id) {
		// TODO Auto-generated method stub
		employeeRepo.delete(employeeRepo.getEmployeeById(id));
		
	}

	@Override
	public List<Employee> searchEmployee(String search) {
		// TODO Auto-generated method stub
		List<Employee> empList = employeeRepo.findAll();
		
		
		
		return empList.stream().filter(emp -> emp.getName().contains(search)).collect(Collectors.toList());
	}

		public Employee authenticateEmployee(Employee employee) {
		
		
		
		List<Employee> employees = employeeRepo.findAll();
		List<Employee> veifiedEmployee = employees.stream().filter(n -> (n.getEmail().equals(employee.getEmail()) || n.getName().equals(employee.getEmail())) && n.getPassword().equals(employee.getPassword())).collect(Collectors.toList());
		
		if(veifiedEmployee.size() ==1) {
			return veifiedEmployee.get(0);
		}
		else {
			return null;
		}
			
	}
		
		public Employee findEmployee(String email) {
			
			return employeeRepo.findbyEmail(email);
		}
	
	
	
	

}
