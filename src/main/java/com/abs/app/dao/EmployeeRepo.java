package com.abs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abs.app.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long>{

	@Query( value = "select * from employees where email = :email", nativeQuery = true)
	Employee findbyEmail(@Param("email") String email);

	@Query( value = "select * from employees where id = :id", nativeQuery = true)
	Employee getEmployeeById(@Param("id") Long id);


}
