package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jsfspring.curddemo.entity.PurchaseBillMaster;
import com.jsfspring.curddemo.entity.PurchasePaymentsDomain;

public interface PurchasePaymentRepo extends JpaRepository<PurchasePaymentsDomain, Integer>{
	
	@Query("select u from PurchaseBillMaster u where u.supId.supCode =:supId and status='Balance'")
	public List<PurchaseBillMaster> findInvByPendingStatus(Integer supId); 
	
	@Transactional
	@Query(value = "UPDATE PURCHASE_BILL_MASTER SET STATUS='Balance',PAYMENT_NO = NULL where PAYMENT_NO=?1", nativeQuery = true)
	@Modifying
	public void updateBeforeDelete(Integer billNo);
	
}
