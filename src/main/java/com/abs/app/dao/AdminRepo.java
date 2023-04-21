package com.abs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abs.app.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long>{

	@Query( value = "select * from admins where email = :email", nativeQuery = true)
	Admin findbyEmail(@Param("email") String email);


}
