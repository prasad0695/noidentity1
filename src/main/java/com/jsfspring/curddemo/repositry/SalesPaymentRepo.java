package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jsfspring.curddemo.entity.SalesPaymentsDomain;

public interface SalesPaymentRepo extends JpaRepository<SalesPaymentsDomain, Integer>{
	
	@Transactional
	@Query(value = "UPDATE BILL_MASTER SET STATUS='Balance',PAYMENT_NO = NULL where PAYMENT_NO=?1", nativeQuery = true)
	@Modifying
	public void updateBeforeDelete(Integer billNo);

}
