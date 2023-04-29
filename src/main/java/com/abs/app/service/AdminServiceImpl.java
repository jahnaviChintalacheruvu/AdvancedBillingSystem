package com.abs.app.service;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abs.app.dao.AdminRepo;
import com.abs.app.dao.BillRepo;
import com.abs.app.dao.EmployeeRepo;
import com.abs.app.dao.InventoryRepo;
import com.abs.app.dao.RewardRepo;
import com.abs.app.dao.ShiftRepo;
import com.abs.app.model.Admin;
import com.abs.app.model.Bill;
import com.abs.app.model.Employee;
import com.abs.app.model.Inventory;
import com.abs.app.model.Reward;
import com.abs.app.model.Shift;


@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private ShiftRepo shiftRepo;
	
	@Autowired
	private InventoryRepo inventoryRepo;
	
	@Autowired
	private BillRepo billRepo;
	
	@Autowired
	private RewardRepo rewardRepo;
	
	
	
	public int saveAdmin(Admin admin) {
		adminRepo.save(admin);
		if(adminRepo.save(admin)!=null) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public Admin findAdmin(String email) {
		List<Admin> admin = adminRepo.findAll();
		System.out.println("----"+admin.size());
		if(admin.size() == 0) {
			return null;
		}
		List<Admin> veifiedAdmin = admin.stream().filter(n -> n.getEmail().equals(email)).collect(Collectors.toList());
		if(veifiedAdmin.size() > 0) {
			return veifiedAdmin.get(0);
		}
		else {
			return null;
		}
		
	}
	
	public Admin authenticateAdmin(Admin admin) {
		
		
		
		List<Admin> admins = adminRepo.findAll();
		List<Admin> veifiedAdmin = admins.stream().filter(n -> (n.getEmail().equals(admin.getEmail()) || n.getAdminname().equals(admin.getEmail())) && n.getPassword().equals(admin.getPassword())).collect(Collectors.toList());
		
		if(veifiedAdmin.size() ==1) {
			return veifiedAdmin.get(0);
		}
		else {
			return null;
		}
			
	}
	
	public Admin findAdminByAdminname(String adminname) {
		
		List<Admin> admins = adminRepo.findAll();
		List<Admin> veifiedAdmin = admins.stream().filter(n -> n.getAdminname().equals(adminname)).collect(Collectors.toList());
		if(veifiedAdmin.size() > 0) {
			return veifiedAdmin.get(0);
		}
		else {
			return null;
		}
		
	}
	
	public int validatePassword(Admin adminmodel, String securityQuestion, String securityAnswer) {
		List<Admin> admins = adminRepo.findAll();
		List<Admin> verifiedAdmin = admins.stream().filter(n -> n.getEmail().equals(adminmodel.getEmail())).collect(Collectors.toList());
		if(verifiedAdmin.size() ==1) {
			List<Admin> adminSecurities = adminRepo.findAll();
			
			List<Admin> securedAdmin = adminSecurities.stream().filter(security -> security.getSecurityQuestion().equals(securityQuestion) && security.getSecurityAnswer().equals(securityAnswer)
					
					).collect(Collectors.toList());
			if(securedAdmin.size() == 1) {
				return 1;
			}
			else {
				return 2;
			}
		}
		else {
			return 0;
		}
	}
	
	public void saveNewPassword(Admin adminmodel) {
			
			Admin admin = adminRepo.findbyEmail(adminmodel.getEmail());
			System.out.println("user#########"+admin.toString());
			admin.setPassword(adminmodel.getPassword());
			adminRepo.save(admin);
	}
	
	public void deleteAdmin(Long id) {
			
			adminRepo.deleteById(id);
			
	}

	@Override
	public List<Shift> getAllShifts() {
		// TODO Auto-generated method stub
		
		return shiftRepo.findAll();
	}

	@Override
	public void saveShift(Shift shift) {
		// TODO Auto-generated method stub
		shiftRepo.save(shift);
		
	}

	@Override
	public Shift getShiftById(Long id) {
		// TODO Auto-generated method stub
		return shiftRepo.findShiftById(id);
	}

	@Override
	public void updateShift(Shift shift) {
		shiftRepo.save(shift);
		
	}

	@Override
	public void deleteShift(Long id) {
		// TODO Auto-generated method stub
		shiftRepo.delete(shiftRepo.findShiftById(id));
		
	}

	@Override
	public List<Inventory> getAllInventories(String email) {
		// TODO Auto-generated method stub
		return inventoryRepo.findAll().stream().filter(inv -> inv.getAdminEmail().equals(email) && Integer.parseInt(inv.getQuantity()) > 0).collect(Collectors.toList());
	}

	@Override
	public void saveInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		inventoryRepo.save(inventory);
		
	}

	@Override
	public Inventory getInventoryById(Long id) {
		// TODO Auto-generated method stub
		return inventoryRepo.findInventoryById(id);
	}

	@Override
	public void updateInventory(Inventory inventory) {
		// TODO Auto-generated method stub
		inventoryRepo.save(inventory);
		
	}

	@Override
	public void saveBill(Bill bill) {
		// TODO Auto-generated method stub
		billRepo.save(bill);
		
	}

	@Override
	public void saveReward(Reward reward) {
		// TODO Auto-generated method stub
		rewardRepo.save(reward);
		
	}

	@Override
	public String findRole(String email) {
		// TODO Auto-generated method stub
		
		Admin admin = adminRepo.findbyEmail(email);
		Employee emp = employeeRepo.findbyEmail(email);
		if(admin != null) {
			return "admin";
		}
		else if(emp != null) {
			return emp.getRole();
		}else {
			return "customer";
		}
		
	}

	@Override
	public void deleteInventory(Long id) {
		// TODO Auto-generated method stub
		inventoryRepo.delete(inventoryRepo.findInventoryById(id));
		
	}

	@Override
	public List<Bill> getAllTodayBills(String adminEmail) {
		// TODO Auto-generated method stub
		Date date = new Date();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
	       String str = formatter.format(date);
		return billRepo.findAll().stream().filter(b -> b.getAdminEmail().equals(adminEmail) && b.getTimestamp().equals(str)).collect(Collectors.toList());
	}

	@Override
	public void removeTodayBills(String email) {
		// TODO Auto-generated method stub
		Date date = new Date();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
	       String str = formatter.format(date);
		billRepo.deleteTodayBills(email,str);
		
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public List<Shift> findShiftTime(String email) {
		// TODO Auto-generated method stub
		Employee emp = employeeRepo.findbyEmail(email);
		return shiftRepo.findAll().stream().filter(sh ->sh.getEmpId().equals(emp.getId().toString())).collect(Collectors.toList());
	}

}
