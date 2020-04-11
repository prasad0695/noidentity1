package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.ProductDomain;

public interface ProductRepo extends JpaRepository<ProductDomain, Integer>{
	
	public List<ProductDomain> findByItemContaining(String productName);

}
