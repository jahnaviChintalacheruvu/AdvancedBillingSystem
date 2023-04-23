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
public class EmployeeController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CustomerService customerService;

	
	@GetMapping("/employee")
	public String getAdminWelcomePage(@ModelAttribute("employee") Employee employee, Model model, HttpSession session)
	{
		@SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if(messages == null) {
			model.addAttribute("errormsg", "Session Expired. Please Login Again");
			return "home/error";
		}
        model.addAttribute("sessionMessages", messages);

		return "employee/welcomeemployee";
	}
	
}
