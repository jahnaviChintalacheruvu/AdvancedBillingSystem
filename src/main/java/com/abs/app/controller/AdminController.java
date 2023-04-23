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
	public String employees(Model model) {

		List<Employee> employees = employeeService.getAllEmployees();
		model.addAttribute("employees", employees);
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "admin/employees";
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
		Shift shift = new Shift();
		model.addAttribute("shift", shift);
	List<Employee> employees = employeeService.getAllEmployees();
		
		model.addAttribute("employees", employees);
		return "admin/shifts";
	}

	
	@PostMapping("/saveShift")
	public String saveShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("Shift Created");

		adminService.saveShift(shift);
		
		return "redirect:/admin";
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
		
		return "admin/updateshift";
	}
	
	@PostMapping("/updateShift")
	public String updateShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		
		adminService.updateShift(shift);
		return "redirect:/admin";
	}
	
	@PostMapping("/deleteShift/{id}")
	public String deleteShift(@PathVariable(name="id") Long id)
	{
		adminService.deleteShift(id);
		return "redirect:/admin";
	}
	
	@GetMapping("/inventory")
	public String inventory(Model model) {

		List<Inventory> inventories = adminService.getAllInventories();
	
		model.addAttribute("inventories", inventories);
		Inventory inventory = new Inventory();
		model.addAttribute("inventory", inventory);
	
		
		return "admin/inventories";
	}
	
	@PostMapping("/saveInventory")
	public String saveInventory(@ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session)
	{
		System.out.println("Inventory Created");

		adminService.saveInventory(inventory);
		
		return "redirect:/admin";
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
		
		return "admin/updateinventory";
	}
	
	@PostMapping("/updateInventory")
	public String updateShift(@ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		
		adminService.updateInventory(inventory);
		return "redirect:/admin";
	}
	
	@GetMapping("/makebilling")
	public String makebilling(Model model) {

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
	
		
		return "admin/makebilling";
	}
	
	 @RequestMapping(value="/updateInv", method = RequestMethod.POST)
	 public String updateInv( @RequestParam(value = "quantity[]") List<Integer> quantity, @RequestParam(value = "items[]") List<String> items,
			 @RequestParam(value = "customerMail") String customerMail,  @RequestParam(value = "cost") int cost) {	

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
		 

		 return "redirect:/admin";
		}

	
	
}
