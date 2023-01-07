package com.jsfspring.curddemo.repositry;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jsfspring.curddemo.entity.PurchaseBillMaster;

import java.util.List;

public interface PurchaseBillMasterRepo extends JpaRepository<PurchaseBillMaster, Integer>{

	@Query("select u from PurchaseBillMaster u where u.supId.supCode =?1 and status='Balance'")
	public List<PurchaseBillMaster> findInvByPendingStatus(Integer supId);

	@Transactional
	@Query(value = "UPDATE INWARD_MASTER SET STATUS='Pending',PURCHASE_BILL_NO = NULL where PURCHASE_BILL_NO=?1", nativeQuery = true)
	@Modifying
	public void updateBeforeDelete(Integer billNo);

}
