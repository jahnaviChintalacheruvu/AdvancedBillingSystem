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

import java.util.ArrayList;
import java.util.List;

//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private BillRepo billRepo;

    @Mock
    private RewardRepo rewardRepo;

    
    @Test
    public void getAllCustomersTest() {

        Customer cus = new Customer();
        cus.setAdminEmail("admin@gmail.com");
        cus.setEmail("customer@gmail.com");
        cus.setRole("customer");
        List<Customer> customers = new ArrayList<>();
        
        customers.add(cus);
        
        when(customerRepo.findAll()).thenReturn(customers);

        assertEquals(customers,customerService.getAllCustomers(cus.getAdminEmail()));

    }
    
    @Test
    public void authenticateCustomerTest() {

    	 Customer cus = new Customer();
         cus.setAdminEmail("admin@gmail.com");
         cus.setEmail("customer@gmail.com");
         cus.setRole("customer");
         cus.setPassword("op");
         cus.setName("cus");
         List<Customer> customers = new ArrayList<>();
         
         customers.add(cus);
         
         Customer cus1 = new Customer();
         cus1.setAdminEmail("ad@gmail.com");
         cus1.setEmail("e@gmail.com");
         cus1.setPassword("pass");
         cus1.setRole("customer");
         cus1.setName("cus2");
         customers.add(cus1);
         when(customerRepo.findAll()).thenReturn(customers);

        assertEquals(cus1,customerService.authenticateCustomer(cus1));

    }

    @Test
    public void findCustomerTest() {

    	Customer cus = new Customer();
        cus.setAdminEmail("admin@gmail.com");
        cus.setEmail("customer@gmail.com");
        cus.setRole("customer");
        cus.setPassword("op");
        cus.setName("cus");
        when(customerRepo.findbyEmail(cus.getAdminEmail())).thenReturn(cus);
        assertEquals(cus, customerService.findCustomer(cus.getAdminEmail()));
    }
    

    @Test
    public void getAllBillsTest() {

    	 Bill bill = new Bill();

         bill.setAdminEmail("admin@gmail.com");
         bill.setCustomerMail("customer@gmail.com");
         bill.setNoOfItems("5");
         bill.setTimestamp("12/12/1111");
         bill.setTotalCost("234");
         
         List<Bill> bills = new ArrayList<>();
         bills.add(bill);
    
        when(billRepo.findAll()).thenReturn(bills);
        assertEquals(bills, customerService.getAllBills(bill.getCustomerMail()));
    }
    
    @Test
    public void getAllRewardsTest() {

    	 Reward reward = new Reward();

    	 reward.setCustomerMail("customers");
    	 reward.setPoints("345");
    	 
         
         List<Reward> rewards = new ArrayList<>();
         rewards.add(reward);
    
        when(rewardRepo.findAll()).thenReturn(rewards);
        assertEquals(rewards, customerService.getAllRewads(reward.getCustomerMail()));
    }

    
   
    
    

}