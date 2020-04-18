package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.jsfspring.curddemo.entity.BillMasterDomain;

public interface BillMasterRepo extends JpaRepository<BillMasterDomain, Integer>{

	@Query("select u from BillMasterDomain u where u.companyId.compId =?1 and status='Balance'")
	public List<BillMasterDomain> findInvByPendingStatus(Integer compId);
	
	@Transactional
	@Query(value = "UPDATE DC_MASTER SET STATUS='Pending',BILL_NO = NULL where BILL_NO=?1", nativeQuery = true)
	@Modifying
	public void updateBeforeDelete(Integer billNo);
	
}
