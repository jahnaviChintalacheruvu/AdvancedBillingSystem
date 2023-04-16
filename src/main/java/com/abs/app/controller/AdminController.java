package com.abs.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abs.app.model.Admin;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;
import com.abs.app.model.Shift;
import com.abs.app.service.AdminService;
import com.abs.app.service.CustomerService;
import com.abs.app.service.EmployeeService;


@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustomerService customerService;

	
	@GetMapping("/admin")
	public String getAdminWelcomePage(@ModelAttribute("admin") Admin admin, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);

		return "admin/welcomeadmin";
	}
	
	@GetMapping("/employees")
	public String employees(Model model) {

		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("employees", employees);
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "admin/employees";
	}
	
	@GetMapping("/createEmployee")
	public String createEmployee(Model model, HttpSession session) {
		
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		
		return "customer/createemployee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model, HttpSession session)
	{
		System.out.println("Employee Created");

		employeeService.saveEmployee(employee);
		
		return "redirect:/admin";
	}
	
	
	@GetMapping("/viewSingleEmployee/{id}")
	public String viewSingleEmployee(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		Employee employee = employeeService.getEmployeeById(id);
		
		
		model.addAttribute("employee", employee);
	
		return "customer/displaysingleemployee";
	}
	
	@GetMapping("/editEmployee/{id}")
	public String editEmployee(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);	
		return "admin/updateemployee";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(@ModelAttribute("employee") Employee employee, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		employeeService.updateEmployee(employee);
		return "redirect:/admin";
	}
	
	@PostMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(name="id") Long id)
	{
		employeeService.deleteEmployee(id);
		return "redirect:/admin";
	}
	
	@PostMapping("/searchEmployee")
	public String searchEmployee(Model model, HttpSession session, @RequestParam("search") String search ) {
		
        List<Employee> employees = employeeService.searchEmployee(search);
        model.addAttribute("employees", employees);
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
		return "admin/employees";
	}
	
	@GetMapping("/customers")
	public String customers(Model model) {

		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		return "admin/customers";
	}
	
	@GetMapping("/createCustomer")
	public String createCustomer(Model model, HttpSession session) {
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		
		return "admin/createcustomer";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer, Model model, HttpSession session)
	{
		System.out.println("Customer Created");

		customerService.saveCustomer(customer);
		
		return "redirect:/admin";
	}
	
	
	
	
	@PostMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable(name="id") Long id)
	{
		customerService.deleteCustomer(id);
		return "redirect:/admin";
	}
	
	@PostMapping("/searchCustomer")
	public String searchCustomer(Model model, HttpSession session, @RequestParam("search") String search ) {
		
        List<Customer> customers = customerService.searchCustomer(search);
        model.addAttribute("customers", customers);
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
		return "admin/customers";
	}
	
	@GetMapping("/shifts")
	public String shifts(Model model) {

		List<Shift> shifts = adminService.getAllShifts();
		model.addAttribute("shifts", shifts);
		System.out.println(shifts.get(0));
		Shift shift = new Shift();
		model.addAttribute("shift", shift);
	List<Employee> employees = employeeService.getAllEmployees();
		
		model.addAttribute("employees", employees);
		return "admin/shifts";
	}
	
	@GetMapping("/createShift")
	public String createShift(Model model, HttpSession session) {
		
		Shift shift = new Shift();
		model.addAttribute("shift", shift);
	
		return "admin/createshift";
	}
	
	@PostMapping("/saveShift")
	public String saveShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("Shift Created");

		adminService.saveShift(shift);
		
		return "redirect:/admin";
	}
	
	
}
