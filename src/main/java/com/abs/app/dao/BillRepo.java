package com.abs.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abs.app.model.Bill;
import com.abs.app.model.Employee;
import com.abs.app.model.Shift;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long>{

	@Query(value = "delete from bill where admin_email = :email and timestamp = :str", nativeQuery = true)
	@Modifying
    @Transactional
	void deleteTodayBills(String email, String str);



}
