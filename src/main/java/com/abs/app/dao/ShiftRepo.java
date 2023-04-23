package com.abs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.abs.app.model.Employee;
import com.abs.app.model.Shift;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, Long>{

	@Query( value = "select * from shifts where id = :id", nativeQuery = true)
	Shift findShiftById(@Param("id") Long id);



}
