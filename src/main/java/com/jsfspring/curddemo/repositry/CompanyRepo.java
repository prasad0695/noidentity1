package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsfspring.curddemo.entity.Company;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer>{
	
	public List<Company> findByCompNameContaining(String companyName);

}
