package com.abs.app.service;

import java.util.List;

import com.abs.app.model.Admin;

public interface AdminService {
	
	int saveAdmin(Admin admin);
	
	Admin findAdmin(String email);
	
	Admin authenticateAdmin(Admin admin);
	
	Admin findAdminByAdminname(String adminname);
	
	int validatePassword(Admin adminmodel, String securityQuestion, String securityAnswer);
	
	void saveNewPassword(Admin adminmodel);
	
	void deleteAdmin(Long id);


}
