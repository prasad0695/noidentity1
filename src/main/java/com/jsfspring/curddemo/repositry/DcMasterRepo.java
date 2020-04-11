package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsfspring.curddemo.entity.DeliveryChalanMaster;

public interface DcMasterRepo extends JpaRepository<DeliveryChalanMaster, Integer>{
	
	@Query("select u from DeliveryChalanMaster u where u.companyId.compId =?1 and status='Pending'")
	public List<DeliveryChalanMaster> findDcByPendingStatus(Integer compId);

}
