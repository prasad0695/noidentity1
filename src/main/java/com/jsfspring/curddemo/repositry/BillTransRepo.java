package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsfspring.curddemo.entity.BillTransDomain;

public interface BillTransRepo extends JpaRepository<BillTransDomain, Integer>{
	
	@Query("select b from BillMasterDomain a, BillTransDomain b where a.companyId.compId=?1 and a.billNo = b.billMaster.billNo and b.productId.prodCode=?2")
	public List<BillTransDomain> findProductByCompId(int compId,int prodCode);
	

}
