package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.QuotationMaster;

public interface QuotationMasterRepo extends JpaRepository<QuotationMaster, Integer>{
	
	public QuotationMaster findByCompanyId(Company company);

}
