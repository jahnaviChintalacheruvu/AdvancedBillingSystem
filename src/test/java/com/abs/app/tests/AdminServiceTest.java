package com.abs.app.tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.abs.app.dao.AdminRepo;
import com.abs.app.dao.EmployeeRepo;
import com.abs.app.dao.InventoryRepo;
import com.abs.app.dao.ShiftRepo;
import com.abs.app.model.Admin;
import com.abs.app.model.Employee;
import com.abs.app.model.Inventory;
import com.abs.app.model.Shift;
import com.abs.app.service.AdminService;
import com.abs.app.service.AdminServiceImpl;

import java.util.ArrayList;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminServiceImpl adminService;

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private ShiftRepo shiftRepo;

    @Mock
    private InventoryRepo inventoryRepo;

    
    @Test
    public void saveAdminTest() {

        Admin admin = new Admin();

        admin.setAdminname("admin");
        admin.setEmail("admin@gmail.com");
        admin.setFirstname("admin");
        admin.setLastname("admin");
        when(adminRepo.save(admin)).thenReturn(admin);

        assertEquals(1,adminService.saveAdmin(admin));

    }
    
    @Test
    public void saveAdminExceptionTest() {

        Admin admin = new Admin();

        admin.setAdminname("admin");
        admin.setEmail("admin@gmail.com");
        admin.setFirstname("admin");
        admin.setLastname("admin");
        when(adminRepo.save(admin)).thenReturn(null);

        assertEquals(0,adminService.saveAdmin(admin));

    }

    @Test
    public void authenticateAdminTest() {

    	 Admin admin = new Admin();

         admin.setAdminname("admin");
         admin.setEmail("admin@gmail.com");
         admin.setFirstname("admin");
         admin.setLastname("admin");
         admin.setPassword("password");
         List<Admin> admins = new ArrayList<>();
         admins.add(admin);
        when(adminRepo.findAll()).thenReturn(admins);
        assertEquals(admin, adminService.authenticateAdmin(admin));
    }
    

    @Test
    public void authenticateAdminNegativeTest() {

    	 Admin admin = new Admin();

         admin.setAdminname("admin");
         admin.setEmail("admin@gmail.com");
         admin.setFirstname("admin");
         admin.setLastname("admin");
         admin.setPassword("password");
         List<Admin> admins = new ArrayList<>();
         admins.add(admin);
         
         Admin admin2 = new Admin();

         admin.setAdminname("admin2");
         admin.setEmail("admin2@gmail.com");
         admin.setFirstname("admin2");
         admin.setLastname("admin2");
         admin.setPassword("password2");
        when(adminRepo.findAll()).thenReturn(admins);
        assertEquals(null, adminService.authenticateAdmin(admin2));
    }

    @Test
    public void findAdminTest()
    {
    	Admin admin = new Admin();

        admin.setAdminname("admin");
        admin.setEmail("admin@gmail.com");
        admin.setFirstname("admin");
        admin.setLastname("admin");
        admin.setPassword("password");
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);
       when(adminRepo.findAll()).thenReturn(admins);
       assertEquals(admin, adminService.findAdmin(admin.getEmail()));
    }
    
    @Test
    public void findAdminNegativeTest()
    {
    	Admin admin = new Admin();

        admin.setAdminname("admin");
        admin.setEmail("admin@gmail.com");
        admin.setFirstname("admin");
        admin.setLastname("admin");
        admin.setPassword("password");
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);
       when(adminRepo.findAll()).thenReturn(admins);
       assertEquals(null, adminService.findAdmin("ad@gmail.com"));
    }
    
    @Test
    public void getAllShiftsTest()
    {
    	Shift shift = new Shift();
    	
    	shift.setAdminEmail("admin@gmail.com");
    	shift.setEmpId("1");
    	shift.setStartTime("9:00");
    	shift.setFromDate("12/12/2023");
    	shift.setEndTime("5:00");
    	shift.setToDate("14/12/2024");
    	
        List<Shift> shifts = new ArrayList<>();
        shifts.add(shift);
       when(shiftRepo.findAll()).thenReturn(shifts);
       assertEquals(shifts, adminService.getAllShifts());
    }
    
    @Test
    public void getAllShiftsNegativeTest()
    {
    	Shift shift = new Shift();
    	
    	shift.setAdminEmail("admin@gmail.com");
    	shift.setEmpId("1");
    	shift.setStartTime("9:00");
    	shift.setFromDate("12/12/2023");
    	shift.setEndTime("5:00");
    	shift.setToDate("14/12/2024");
    	
        List<Shift> shifts = new ArrayList<>();
        shifts.add(shift);
       when(shiftRepo.findAll()).thenReturn(null);
       assertEquals(null, adminService.getAllShifts());
    }
    
    @Test
    public void getAllInventoryTest()
    {
    	Inventory inv = new Inventory();
    	
    	inv.setAdminEmail("admin@gmail.com");
    	inv.setCategory("fruits");
    	inv.setName("Apple");
    	inv.setPrice("120");
    	inv.setQuantity("100");
    	
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inv);
       when(inventoryRepo.findAll()).thenReturn(inventories);
       assertEquals(inventories, adminService.getAllInventories(inv.getAdminEmail()));
    }
    
    
    @Test
    public void findInventoryByIdTest()
    {
    	Inventory inv = new Inventory();
    	
    	inv.setAdminEmail("admin@gmail.com");
    	inv.setCategory("fruits");
    	inv.setName("Apple");
    	inv.setPrice("120");
    	inv.setQuantity("100");
    	inv.setId(1L);
    	
        List<Inventory> inventories = new ArrayList<>();
        inventories.add(inv);
       when(inventoryRepo.findInventoryById(inv.getId())).thenReturn(inv);
       assertEquals(inv, adminService.getInventoryById(inv.getId()));
    }
    
    @Test
    public void findRoleTest()
    {
    	
    	Admin admin = new Admin();
    	admin.setAdminname("admin");
    	admin.setEmail("admin@gmail.com");
    	admin.setRole("admin");
    	
    	Employee emp = new Employee();
    	 
    	emp.setAdminEmail("admin@gmail.com");
    	emp.setEmail("emp@gmail.com");
    	emp.setRole("employee");
    	emp.setName("employee");
    	
    	
       when(adminRepo.findbyEmail("admin@gmail.com")).thenReturn(null);
       when(employeeRepo.findbyEmail("admin@gmail.com")).thenReturn(emp);
       assertEquals("employee", adminService.findRole("admin@gmail.com"));
    }
    
    
   
    
    

}