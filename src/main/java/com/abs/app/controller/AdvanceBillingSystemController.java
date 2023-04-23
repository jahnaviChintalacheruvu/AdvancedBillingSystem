package com.abs.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abs.app.model.Admin;
import com.abs.app.model.Customer;
import com.abs.app.model.Email;
import com.abs.app.model.Employee;
import com.abs.app.service.AdminService;
import com.abs.app.service.CustomerService;
import com.abs.app.service.EmployeeService;
import com.abs.app.service.MessageService;



@Controller
public class AdvanceBillingSystemController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustomerService customerService;


	
	@GetMapping("/")
	public String getHome(Model model) {
		return "index";
	}

	@GetMapping("/register")
	public String register(Model model) {

		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		return "home/register";
	}

	@PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin,Model model) {
		System.out.println("save --Admin");

		Admin existingAdmin = adminService.findAdmin(admin.getEmail());

		if (existingAdmin != null) {
			model.addAttribute("errormsg", "Email already exists");
			return "home/error";
		}

		Admin existingAdminname = adminService.findAdminByAdminname(admin.getAdminname());

		if (existingAdminname != null) {
			model.addAttribute("errormsg", "Username already exists");
			return "home/error";
		}

		int output = adminService.saveAdmin(admin);
		
		if (output > 0) {
			return "redirect:/login";
		} else {
			model.addAttribute("errormsg", "Account creation failed");
			return "home/error";
		}
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model,  HttpSession session, HttpServletRequest request)
	{	
		request.getSession().invalidate();
		Admin adminmodel = new Admin();
		model.addAttribute("admin", adminmodel);
		return "home/login";
	}
	
	@PostMapping("/authenticateLogin")
	public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("role") String role, RedirectAttributes attributes,HttpServletRequest request,HttpServletResponse response, Model model)
	{
		System.out.println("login**************************************** ");
		if(role.equals("employee")) {
			Employee emp = new Employee();
			
			emp.setEmail(email);
			emp.setPassword(password);
			
			Employee employee = employeeService.authenticateEmployee(emp);
			
			if(employee != null)
			{
				@SuppressWarnings("unchecked")
				List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
				if (messages == null) {
					messages = new ArrayList<>();
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				}
				
					String empMail=employee.getEmail();
					messages.add(empMail);
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
					return "redirect:/employee";
				
			}
			else {
				model.addAttribute("errormsg", "Login Failed. Invalid Credentials. Please try again.");
				return "home/error";
			}
		}
		else if(role.equals("customer")) {
			Customer cus = new Customer();
			
			cus.setEmail(email);
			cus.setPassword(password);
			
			Customer customer = customerService.authenticateCustomer(cus);
			
			if(customer != null)
			{
				@SuppressWarnings("unchecked")
				List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
				if (messages == null) {
					messages = new ArrayList<>();
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				}
				
					String adminemail=customer.getEmail();
					messages.add(adminemail);
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
					return "redirect:/customer";
				
			}
			else {
				model.addAttribute("errormsg", "Login Failed. Invalid Credentials. Please try again.");
				return "home/error";
			}
		}
		else if(role.equals("admin")) {
			Admin admin = new Admin();
			
			admin.setEmail(email);
			admin.setPassword(password);
			
			Admin  adminModel = adminService.authenticateAdmin(admin);
			if(adminModel != null)
			{
				@SuppressWarnings("unchecked")
				List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
				if (messages == null) {
					messages = new ArrayList<>();
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
				}
				
					String adminemail=adminModel.getEmail();
					messages.add(adminemail);
					request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
					return "redirect:/admin";
				
			}
			else {
				model.addAttribute("errormsg", "Login Failed. Invalid Credentials. Please try again.");
				return "home/error";
			}
		}
		return null;
		
		
		
		
	}
	
	@GetMapping("/forgotUsername")
	public String getForgotUsernamePage(Model model)
	{
		Admin adminmodel = new Admin();
		model.addAttribute("admin", adminmodel);
		return "home/forgotusername";
	}
	
	@GetMapping("/forgotPassword")
	public String getForgotPasswordPage(Model model)
	{
		Admin adminmodel = new Admin();
		model.addAttribute("admin", adminmodel);
		return "home/forgotpassword";
	}
	
	
	@PostMapping("/sendMail")
	public String sendMail(@ModelAttribute("admin") Admin admin, Model model)
	{	
		int output = 0;
		System.out.println("save===usernew password");
		System.out.println("userModel#########"+admin.toString());
		Admin userModel=adminService.findAdmin(admin.getEmail());
		
		if(userModel == null) {
			model.addAttribute("errormsg", "Email Id doesnot exist in our database");
			return "home/error";
		}
		
		Email emailmodel = new Email();
		emailmodel.setMsgBody("Your Username is "+ userModel.getAdminname());
		emailmodel.setRecipient(userModel.getEmail());
		emailmodel.setSubject("Username Recovery from Advanced Billing System");
		System.out.println("------------------body"+ emailmodel.getMsgBody()+"======="+ emailmodel.getRecipient());
		output = messageService.sendSimpleMail(emailmodel);
		
		System.out.println("------------------"+ output);
		if(output !=1) {
			model.addAttribute("errmsg", "User Email address not found.");
		}
		return "redirect:/login";
	}
	
	@PostMapping("/validateForgotPassword")
	public String validatePassword(@ModelAttribute("admin") Admin admin, @RequestParam("securityQuestion") String securityQuestion,
			 @RequestParam("securityAnswer") String securityAnswer,
			Model model,RedirectAttributes redirectAttrs)
	{
		System.out.println("forgot password**************************************** ");
		int output = adminService.validatePassword(admin, securityQuestion, securityAnswer);
		
		if(output == 1)
		{
			
			return "home/changepassword";
		}
		else if(output == 0) {
			model.addAttribute("errormsg", "Invalid User Email");
			return "home/error";
		}
		else if(output == 2) {
			model.addAttribute("errormsg", "Invalid Security Question or Answer");
			return "home/error";
		}
		else {
			model.addAttribute("errormsg", "Password cannot be changed. Invalid credentials.");
			return "home/error";
		}
		
		
	}
	
	@GetMapping("/changePassword")
	public String getChangePasswordPage(Model model)
	{
		Admin adminmodel = new Admin();
		model.addAttribute("admin", adminmodel);
		return "home/changepassword";
	}
	
	@PostMapping("/saveNewPassword")
	public String saveNewPassword(@ModelAttribute("admin") Admin admin, HttpServletRequest request, @Param("confirmPassword") String confirmPassword, Model model)
	{
		if(confirmPassword.equals(admin.getPassword())) {
			
			adminService.saveNewPassword(admin);
		}
		else {
			model.addAttribute("errormsg", "Passwords donot match");
			return "home/error";
		}
		System.out.println("save===usernew password");
		System.out.println("userModel#########"+admin.toString());
		 request.getSession().invalidate();
		return "redirect:/login";
	}
	
	@RequestMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
	
	@RequestMapping("/profile")
    public String viewProfile(HttpSession session, Model model) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
		Admin admin = adminService.findAdmin(messages.get(0));
		Employee employee = employeeService.findEmployee(messages.get(0));
		Customer customer = customerService.findCustomer(messages.get(0));
		if(admin != null) {
			model.addAttribute("admin", admin);
		}
		else if(employee!=null) {
			model.addAttribute("admin", employee);
		}
		else if(customer!=null) {
			model.addAttribute("admin", customer);
		}
        return "home/profile";
    }
	
	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("admin") Admin admin, Model model)
	{
		System.out.println("save===user");
		int output =adminService.saveAdmin(admin);
		if(output>0) {
			return "redirect:/profile";
		}
		
		else {
			model.addAttribute("errormsg", "Operation failed. Please try again");
			return "home/error";
		}
		
	}
	
	@PostMapping("/deleteProfile/{id}")
	public String deleteProfile(@PathVariable(name="id") Long id,HttpServletRequest request, Model model)
	{
		adminService.deleteAdmin(id);
		 request.getSession().invalidate();
		 model.addAttribute("errormsg", "Your Account Deleted Successfully");
			return "home/error";
	}
	
	
	@GetMapping("/resetPassword")
	public String resetPassword(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
		Admin admindata = adminService.findAdmin(messages.get(0));
		
		model.addAttribute("admin", admindata);
		
		return "home/resetpassword";
		
		
		
	}

}
