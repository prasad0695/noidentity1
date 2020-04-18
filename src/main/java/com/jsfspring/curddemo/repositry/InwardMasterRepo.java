package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.InwardMaster;

public interface InwardMasterRepo extends JpaRepository<InwardMaster, Integer>{
	
	@Query("select u from InwardMaster u where u.supId.supCode =?1 and status='Pending'")
	public List<InwardMaster> findInwDcByPendingStatus(Integer supId);
	
}
