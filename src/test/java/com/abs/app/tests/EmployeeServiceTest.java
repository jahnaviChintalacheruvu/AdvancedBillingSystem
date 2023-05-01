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
import com.abs.app.dao.BillRepo;
import com.abs.app.dao.CustomerRepo;
import com.abs.app.dao.EmployeeRepo;
import com.abs.app.dao.InventoryRepo;
import com.abs.app.dao.RewardRepo;
import com.abs.app.dao.ShiftRepo;
import com.abs.app.model.Admin;
import com.abs.app.model.Bill;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;
import com.abs.app.model.Inventory;
import com.abs.app.model.Reward;
import com.abs.app.model.Shift;
import com.abs.app.service.AdminService;
import com.abs.app.service.AdminServiceImpl;
import com.abs.app.service.CustomerServiceImpl;
import com.abs.app.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Test
    public void getAllEmployeesTest() {

        Employee emp = new Employee();
        emp.setAdminEmail("admin@gmail.com");
        emp.setEmail("employee@gmail.com");
        emp.setRole("employee");
        List<Employee> employees = new ArrayList<>();
        
        employees.add(emp);
        
        when(employeeRepo.findAll()).thenReturn(employees);

        assertEquals(employees,employeeService.getAllEmployees(emp.getAdminEmail()));

    }
    
    @Test
    public void authenticateEmployeeTest() {

    	Employee emp = new Employee();
        emp.setAdminEmail("admin@gmail.com");
        emp.setEmail("employee@gmail.com");
        emp.setRole("employee");
         emp.setPassword("op");
         emp.setName("emp");
         List<Employee> employees = new ArrayList<>();
         
         employees.add(emp);
         
         Employee emp1 = new Employee();
         emp1.setAdminEmail("admin@gmail.com");
         emp1.setEmail("employee2@gmail.com");
         emp1.setRole("employee");
          emp1.setPassword("op2");
          emp1.setName("emp2");
         employees.add(emp1);
         when(employeeRepo.findAll()).thenReturn(employees);

        assertEquals(emp1,employeeService.authenticateEmployee(emp1));

    }

    @Test
    public void findEmployeeTest() {

    	Employee emp1 = new Employee();
        emp1.setAdminEmail("admin@gmail.com");
        emp1.setEmail("employee2@gmail.com");
        emp1.setRole("employee");
         emp1.setPassword("op2");
         emp1.setName("emp2");
        when(employeeRepo.findbyEmail(emp1.getAdminEmail())).thenReturn(emp1);
        assertEquals(emp1, employeeService.findEmployee(emp1.getAdminEmail()));
    }
    
    @Test
    public void getEmployeeByIdTest() {

    	Employee emp1 = new Employee();
        emp1.setAdminEmail("admin@gmail.com");
        emp1.setEmail("employee2@gmail.com");
        emp1.setRole("employee");
         emp1.setPassword("op2");
         emp1.setName("emp2");
         emp1.setId(1L);
        when(employeeRepo.getEmployeeById(1L)).thenReturn(emp1);
        assertEquals(emp1, employeeService.getEmployeeById(1L));
    }



   
   
    
    

}