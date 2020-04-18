package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jsfspring.curddemo.entity.PurchaseBillMaster;

public interface PurchaseBillMasterRepo extends JpaRepository<PurchaseBillMaster, Integer>{
	
	@Transactional
	@Query(value = "UPDATE INWARD_MASTER SET STATUS='Pending',PURCHASE_BILL_NO = NULL where PURCHASE_BILL_NO=?1", nativeQuery = true)
	@Modifying
	public void updateBeforeDelete(Integer billNo);

}
