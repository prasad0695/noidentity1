package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.UnitMasterDomain;

public interface UnitMasterRepo extends JpaRepository<UnitMasterDomain, Integer>{
	
	public List<UnitMasterDomain> findByUnitNameContaining(String uom);

}
