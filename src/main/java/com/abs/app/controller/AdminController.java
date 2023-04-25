package com.abs.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abs.app.model.Admin;
import com.abs.app.model.Bill;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;
import com.abs.app.model.Inventory;
import com.abs.app.model.Reward;
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
	public String employees(Model model, HttpSession session) {

		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("employees", employees);
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		
		
		model.addAttribute("role", adminService.findRole(messages.get(0)));
		return "admin/employees";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model, HttpSession session)
	{
		System.out.println("Employee Created");

		employeeService.saveEmployee(employee);
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
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
		
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		return "admin/updateemployee";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(@ModelAttribute("employee") Employee employee, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		employeeService.updateEmployee(employee);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(name="id") Long id, Model model, HttpSession session)
	{
		employeeService.deleteEmployee(id);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/searchEmployee")
	public String searchEmployee(Model model, HttpSession session, @RequestParam("search") String search ) {
		
        List<Employee> employees = employeeService.searchEmployee(search);
        model.addAttribute("employees", employees);
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		return "admin/employees";
	}
	
	@GetMapping("/customers")
	public String customers(Model model,  HttpSession session) {

		List<Customer> customers = customerService.getAllCustomers();
		model.addAttribute("customers", customers);
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		System.out.println("Rolee--->"+role);
		return "admin/customers";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer, Model model, HttpSession session)
	{
		System.out.println("Customer Created");
		customer.setRole("customer");
		customerService.saveCustomer(customer);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	
	
	
	@PostMapping("/deleteCustomer/{id}")
	public String deleteCustomer(@PathVariable(name="id") Long id, Model model, HttpSession session)
	{
		customerService.deleteCustomer(id);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/searchCustomer")
	public String searchCustomer(Model model, HttpSession session, @RequestParam("search") String search ) {
		
        List<Customer> customers = customerService.searchCustomer(search);
        model.addAttribute("customers", customers);
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		return "admin/customers";
	}
	
	@GetMapping("/shifts")
	public String shifts(Model model, HttpSession session) {

		List<Shift> shifts = adminService.getAllShifts();
		model.addAttribute("shifts", shifts);
		Shift shift = new Shift();
		model.addAttribute("shift", shift);
	List<Employee> employees = employeeService.getAllEmployees();
		
		model.addAttribute("employees", employees);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		return "admin/shifts";
	}

	
	@PostMapping("/saveShift")
	public String saveShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("Shift Created");

		adminService.saveShift(shift);
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/editShift/{id}")
	public String editShift(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		Shift shift = adminService.getShiftById(id);
		model.addAttribute("shift", shift);	
		List<Employee> employees = employeeService.getAllEmployees();
		
		model.addAttribute("employees", employees);
		System.out.println(shift);
		
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		
		return "admin/updateshift";
	}
	
	@PostMapping("/updateShift")
	public String updateShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		
		adminService.updateShift(shift);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/deleteShift/{id}")
	public String deleteShift(@PathVariable(name="id") Long id,  HttpSession session, Model model)
	{
		adminService.deleteShift(id);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/inventory")
	public String inventory(Model model, HttpSession session) {

		List<Inventory> inventories = adminService.getAllInventories();
	
		model.addAttribute("inventories", inventories);
		Inventory inventory = new Inventory();
		model.addAttribute("inventory", inventory);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		
		return "admin/inventories";
	}
	
	@PostMapping("/saveInventory")
	public String saveInventory(@ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session)
	{
		System.out.println("Inventory Created");

		adminService.saveInventory(inventory);
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/editInventory/{id}")
	public String editInventory(Model model, HttpSession session, @PathVariable(name="id") Long id) {
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		Inventory inventory = adminService.getInventoryById(id);
		model.addAttribute("inventory", inventory);	

		System.out.println(inventory);
		
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		
		return "admin/updateinventory";
	}
	
	@PostMapping("/updateInventory")
	public String updateShift(@ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		
		adminService.updateInventory(inventory);
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
		return "redirect:/admin";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/makebilling")
	public String makebilling(Model model, HttpSession session) {

		List<Inventory> inventories = adminService.getAllInventories();
	

		List<String> catList= new ArrayList<String>();
		catList.add("toothpaste");
		catList.add("brush");
		catList.add("soap");
		catList.add("shampoo");
		catList.add("handwash");
		catList.add("oil");
		catList.add("vegetables");
		catList.add("biscuits");
		catList.add("chocolates");
		catList.add("chips");
		catList.add("pickels");
		catList.add("cooldrinks");
		catList.add("waterbottles");
		
		
		HashMap<String, List<String>> itemMap = new HashMap<>();
		
		
		for(int i=0;i<catList.size();i++)
		{
			
			String category = catList.get(i);
			
			List<String> items = inventories.stream()
									.filter(it -> it.getCategory().equals(category))
									.map(it-> it.getName()+","+it.getPrice())
									.collect(Collectors.toList());
			
			
			
			itemMap.put(category, items);
			
			
			
		}
		
		model.addAttribute("categories", catList);
		model.addAttribute("itemMap", itemMap);
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		model.addAttribute("role", role);
	
		
		return "admin/makebilling";
	}
	
	 @RequestMapping(value="/updateInv", method = RequestMethod.POST)
	 public String updateInv( @RequestParam(value = "quantity[]") List<Integer> quantity, @RequestParam(value = "items[]") List<String> items,
			 @RequestParam(value = "customerMail") String customerMail,  @RequestParam(value = "cost") int cost, Model model, HttpSession session) {	

			List<Inventory> inventories = adminService.getAllInventories();
		
		 for(int i=0;i<quantity.size();i++)
		 {
			 for(int j=0;j<inventories.size();j++)
			 {
				 if(inventories.get(j).getName().equals(items.get(i))) {
					 
					 Inventory inv = inventories.get(j);
					int qu = Integer.parseInt(inv.getQuantity()) - quantity.get(i);
					inv.setQuantity(String.valueOf(qu));
					 
					 adminService.saveInventory(inv);
				 }
			 }
			
		 }
		 
		 Bill bill = new Bill();
		 
		 bill.setCustomerMail(customerMail);
		 bill.setNoOfItems(String.valueOf(items.size()));
		 bill.setTotalCost(String.valueOf(cost));
		 
		 Reward reward = new Reward();
		 
		 reward.setCustomerMail(customerMail);
		 reward.setPoints(String.valueOf(cost/10));
		 
		 adminService.saveBill(bill);
		 adminService.saveReward(reward);
			@SuppressWarnings("unchecked")
	        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

			if(messages == null) {
				model.addAttribute("errormsg", "Session Expired. Please Login Again");
				return "home/error";
			}
	        model.addAttribute("sessionMessages", messages);
			String role = adminService.findRole(messages.get(0));
			
			model.addAttribute("role", role);
			if(role.equals("admin")) {
			
			return "redirect:/admin";
			}
			else{
				return "redirect:/employee";
			}
		}

	
	
}
