package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.CompanyCategory;


public interface CompanyCategoryRepo extends JpaRepository<CompanyCategory,Integer>{

	public List<CompanyCategory> findByCategoryContaining(String companyName);
	
	public CompanyCategory findByCategory(String companyName);
}
