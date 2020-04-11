package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsfspring.curddemo.entity.BillMasterDomain;

public interface BillMasterRepo extends JpaRepository<BillMasterDomain, Integer>{

	@Query("select u from BillMasterDomain u where u.companyId.compId =?1 and status='Balance'")
	public List<BillMasterDomain> findInvByPendingStatus(Integer compId);
	
}
