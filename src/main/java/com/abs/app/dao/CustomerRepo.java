package com.abs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abs.app.model.Customer;
import com.abs.app.model.Employee;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>{

	@Query( value = "select * from customers where email = :email", nativeQuery = true)
	Customer findbyEmail(@Param("email") String email);

	@Query( value = "select * from customers where id = :id", nativeQuery = true)
	Customer getCustomerById(@Param("id") Long id);


}
