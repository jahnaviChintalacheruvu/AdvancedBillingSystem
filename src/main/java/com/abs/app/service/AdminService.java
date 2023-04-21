package com.abs.app.service;

import java.util.List;

import com.abs.app.model.Admin;
import com.abs.app.model.Employee;
import com.abs.app.model.Inventory;
import com.abs.app.model.Shift;

public interface AdminService {
	
	int saveAdmin(Admin admin);
	
	Admin findAdmin(String email);
	
	Admin authenticateAdmin(Admin admin);
	
	Admin findAdminByAdminname(String adminname);
	
	int validatePassword(Admin adminmodel, String securityQuestion, String securityAnswer);
	
	void saveNewPassword(Admin adminmodel);
	
	void deleteAdmin(Long id);

	List<Shift> getAllShifts();

	void saveShift(Shift shift);

	Shift getShiftById(Long id);

	void updateShift(Shift shift);

	void deleteShift(Long id);

	List<Inventory> getAllInventories();

	void saveInventory(Inventory inventory);


}
