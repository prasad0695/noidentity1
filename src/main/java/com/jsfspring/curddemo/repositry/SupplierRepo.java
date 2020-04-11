package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.SupplierDomain;

public interface SupplierRepo extends JpaRepository<SupplierDomain, Integer>{
	
	public List<SupplierDomain> findByNameContaining(String supName);
}
