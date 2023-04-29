package com.abs.app.controller;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
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
		List<Employee> employees = null;
		
		if(role.equals("admin")) {
			
			employees = employeeService.getAllEmployees(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			employees = employeeService.getAllEmployees(adminEmail);
		}
		

		model.addAttribute("employees", employees);
		
		return "admin/employees";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, Model model, HttpSession session)
	{
		System.out.println("Employee Created");

	
		
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
			
			employee.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			employee.setAdminEmail(adminEmail);
		}
		
		
		employeeService.saveEmployee(employee);
		if(role.equals("admin")) {
		
		return "redirect:/employees";
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
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		if(role.equals("admin")) {
			
			employee.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			employee.setAdminEmail(adminEmail);
		}
		employeeService.updateEmployee(employee);
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
			return "redirect:/employees";
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
		
			return "redirect:/employees";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/searchEmployee")
	public String searchEmployee(Model model, HttpSession session, @RequestParam("search") String search ) {
		
        
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
		String adminEmail = "";
		if(role.equals("admin")) {
			
			adminEmail = messages.get(0);
		}else {
			adminEmail = employeeService.findAdminEmail(messages.get(0));
			
		}
		
		List<Employee> employees = employeeService.searchEmployee(search, adminEmail);
        model.addAttribute("employees", employees);
		
		model.addAttribute("role", role);
		return "admin/employees";
	}
	
	@GetMapping("/customers")
	public String customers(Model model,  HttpSession session) {

		
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
		List<Customer> customers = null;
		
		if(role.equals("admin")) {
			customers = customerService.getAllCustomers(messages.get(0));
		}
		else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			
			customers = customerService.getAllCustomers(adminEmail);
		}
		model.addAttribute("customers", customers);
		
		System.out.println("Rolee--->"+role);
		return "admin/customers";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer, Model model, HttpSession session)
	{
		System.out.println("Customer Created");
		customer.setRole("customer");
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		

		if(role.equals("admin")) {
			
			customer.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			customer.setAdminEmail(adminEmail);
		}
		customerService.saveCustomer(customer);
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
			return "redirect:/customers";
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
		
			return "redirect:/customers";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/searchCustomer")
	public String searchCustomer(Model model, HttpSession session, @RequestParam("search") String search ) {
		
   
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
		String adminEmail = "";
		if(role.equals("admin")) {
			
			adminEmail = messages.get(0);
		}else {
			adminEmail = employeeService.findAdminEmail(messages.get(0));
			
		}
	     List<Customer> customers = customerService.searchCustomer(search, adminEmail);
	        model.addAttribute("customers", customers);
		model.addAttribute("role", role);
		return "admin/customers";
	}
	
	@GetMapping("/shifts")
	public String shifts(Model model, HttpSession session) {

		List<Shift> shifts = adminService.getAllShifts();
		model.addAttribute("shifts", shifts);
		Shift shift = new Shift();
		model.addAttribute("shift", shift);

		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		List<Employee> employees = null;
		
		if(role.equals("admin")) {
			
			employees = employeeService.getAllEmployees(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			employees = employeeService.getAllEmployees(adminEmail);
		}
		model.addAttribute("employees", employees);
		model.addAttribute("role", role);
		return "admin/shifts";
	}

	
	@PostMapping("/saveShift")
	public String saveShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("Shift Created");

	
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		if(role.equals("admin")) {
			
			shift.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			shift.setAdminEmail(adminEmail);
		}
		
		adminService.saveShift(shift);
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
			return "redirect:/shifts";
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
	
		System.out.println(shift);
		
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		List<Employee> employees = null;
		
		if(role.equals("admin")) {
			
			employees = employeeService.getAllEmployees(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			employees = employeeService.getAllEmployees(adminEmail);
		}
		model.addAttribute("employees", employees);
		model.addAttribute("role", role);
		
		return "admin/updateshift";
	}
	
	@PostMapping("/updateShift")
	public String updateShift(@ModelAttribute("shift") Shift shift, Model model, HttpSession session)
	{
		System.out.println("employee updated");
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		if(role.equals("admin")) {
			
			shift.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			shift.setAdminEmail(adminEmail);
		}
		adminService.updateShift(shift);
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
			return "redirect:/shifts";
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
		
			return "redirect:/shifts";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/inventory")
	public String inventory(Model model, HttpSession session) {

		
	
		
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
		
		List<Inventory> inventories = null;
		
		if(role.equals("admin")) {
			
			inventories = adminService.getAllInventories(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			inventories = adminService.getAllInventories(adminEmail);
		}
		model.addAttribute("inventories", inventories);
		
		model.addAttribute("role", role);
		
		return "admin/inventories";
	}
	
	@PostMapping("/saveInventory")
	public String saveInventory(@ModelAttribute("inventory") Inventory inventory, Model model, HttpSession session)
	{
		System.out.println("Inventory Created");

		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		if(role.equals("admin")) {
			
			inventory.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			inventory.setAdminEmail(adminEmail);
		}
		adminService.saveInventory(inventory);
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
			return "redirect:/inventory";
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
		
		
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);
		String role = adminService.findRole(messages.get(0));
		
		if(role.equals("admin")) {
			
			inventory.setAdminEmail(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			inventory.setAdminEmail(adminEmail);
		}
		adminService.updateInventory(inventory);
		
		model.addAttribute("role", role);
		if(role.equals("admin")) {
		
			return "redirect:/inventory";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@PostMapping("/deleteInventory/{id}")
	public String deleteInventory(@PathVariable(name="id") Long id,  HttpSession session, Model model)
	{
		adminService.deleteInventory(id);
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
		
			return "redirect:/inventory";
		}
		else{
			return "redirect:/employee";
		}
	}
	
	@GetMapping("/makebilling")
	public String makebilling(Model model, HttpSession session) {

			List<Inventory> inventories = null;
			
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
			
			inventories = adminService.getAllInventories(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			inventories = adminService.getAllInventories(adminEmail);
		}
	

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
			 @RequestParam(value = "customerMail") String customerMail,  @RequestParam(value = "cost") int cost, Model model, HttpSession session) {	

		 List<Inventory> inventories = null;
			
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
			
			inventories = adminService.getAllInventories(messages.get(0));
		}else {
			String adminEmail = employeeService.findAdminEmail(messages.get(0));
			inventories = adminService.getAllInventories(adminEmail);
		}
		
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
		 String adminEmail = "";
			if(role.equals("admin")) {
				
				adminEmail = messages.get(0);
			}else {
				adminEmail = employeeService.findAdminEmail(messages.get(0));
				
			}
		 
		 Bill bill = new Bill();
		 
		 bill.setCustomerMail(customerMail);
		 bill.setNoOfItems(String.valueOf(items.size()));
		 bill.setTotalCost(String.valueOf(cost));
		 bill.setAdminEmail(adminEmail);
		 Date date = new Date();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
	       String str = formatter.format(date);
		 bill.setTimestamp(str);
		 
		 Reward reward = new Reward();
		 
		 reward.setCustomerMail(customerMail);
		 reward.setPoints(String.valueOf(cost/10));
		 
		 adminService.saveBill(bill);
		 adminService.saveReward(reward);
			
			
			model.addAttribute("role", role);
			if(role.equals("admin")) {
			
				return "redirect:/makebilling";
			}
			else{
				return "redirect:/employee";
			}
		}
	 
	 @GetMapping("/showbills")
		public String showbills(Model model, HttpSession session) {

			
			@SuppressWarnings("unchecked")
	        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

			if(messages == null) {
				model.addAttribute("errormsg", "Session Expired. Please Login Again");
				return "home/error";
			}
	        model.addAttribute("sessionMessages", messages);
			String role = adminService.findRole(messages.get(0));
			
			List<Bill> bills = null;
			
			
			if(role.equals("admin")) {
				
				bills = adminService.getAllTodayBills(messages.get(0));
			}else {
				String adminEmail = employeeService.findAdminEmail(messages.get(0));
				bills = adminService.getAllTodayBills(adminEmail);
			}
			model.addAttribute("bills", bills);
			
			model.addAttribute("role", role);
			
			return "admin/showbills";
		}
	 
	 @GetMapping("/batchClose")
		public String batchClose(Model model, HttpSession session) {

			
			@SuppressWarnings("unchecked")
	        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

			if(messages == null) {
				model.addAttribute("errormsg", "Session Expired. Please Login Again");
				return "home/error";
			}
	        model.addAttribute("sessionMessages", messages);
			String role = adminService.findRole(messages.get(0));
			
			
			
			List<Bill> bills = null;
			
			
			if(role.equals("admin")) {
				adminService.removeTodayBills(messages.get(0));
				
				bills = adminService.getAllTodayBills(messages.get(0));
			}else {
				String adminEmail = employeeService.findAdminEmail(messages.get(0));
				adminService.removeTodayBills(adminEmail);
				bills = adminService.getAllTodayBills(adminEmail);
			}
			model.addAttribute("bills", bills);
			
			model.addAttribute("role", role);
			
			return "admin/showbills";
		}

	
	
}
