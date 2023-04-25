package com.abs.app.service;

import java.util.List;

import com.abs.app.model.Employee;
import com.abs.app.model.Shift;

public interface EmployeeService {

	List<Employee> getAllEmployees();

	Employee getEmployeeById(Long id);

	void updateEmployee(Employee employee);

	void deleteEmployee(Long id);

	List<Employee> searchEmployee(String search);

	void saveEmployee(Employee employee);

	Employee authenticateEmployee(Employee emp);

	Employee findEmployee(String email);


}
