package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.ProductSellPriceDomain;

public interface ProductSellPriceRepo extends JpaRepository<ProductSellPriceDomain, Integer>{
	
	List<ProductSellPriceDomain> findByProduct(ProductDomain id);

}
