package com.abs.app.tests;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.abs.app.dao.AdminRepo;
import com.abs.app.model.Admin;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase
public class AdvancedBillingSystemControllerTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Test
	public void testRegisterAdmin() {
		
		Admin admin = new Admin();
		admin.setAdminname("Hari");
		admin.setEmail("hari@gmail.com");
		admin.setAge("26");
		admin.setCity("Sydney");
		List<Admin> admins = adminRepo.findAll();
		
		assertThat(admins).isEmpty();
		
		adminRepo.save(admin);
	    
	    List<Admin> admins2 = adminRepo.findAll();

	    assertThat(admins2).hasSize(1);
	    adminRepo.deleteAll();
	    
	    Admin admin1 = new Admin();
		
	    admin1.setEmail("varma@gmail.com");
	    admin1.setAdminname("varma");
	    admin1.setPassword("password");
		
		List<Admin> adminaData = adminRepo.findAll();
		
		assertThat(adminaData).isEmpty();
		
		adminRepo.save(admin1);
	    
	    List<Admin> adminsData = adminRepo.findAll();

	    assertThat(adminsData).hasSize(1);
	    
	  }
	
	@Test
	public void testDonotRegisterAdmin() {
		
		Admin user = new Admin();
		
		user.setEmail("rama@gmail.com");
		user.setAdminname("krsihna");
		user.setPassword("hari");
		
		List<Admin> userdata = adminRepo.findAll();
		
		assertThat(userdata).isEmpty();
		
		try {
			adminRepo.save(null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	    
	    List<Admin> admins = adminRepo.findAll();

	    assertThat(admins).hasSize(0);

	    
	    Admin admin1 = new Admin();
		
	    admin1.setEmail("banu@gmail.com");
	    admin1.setAdminname("Banu");
	    admin1.setPassword("Sree");
		
		List<Admin> admins1 = adminRepo.findAll();
		
		assertThat(admins1).isEmpty();
		
		try {
			adminRepo.save(null);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	    
	    List<Admin> admins2 = adminRepo.findAll();

	    assertThat(admins2).hasSize(0);
	    
	  }
	

}
