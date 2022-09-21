package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.ProductCategory;
import com.jsfspring.curddemo.entity.ProductSubCategory;


public interface ProductSubCategoryRepo extends JpaRepository<ProductSubCategory, Integer>{
	
	public List<ProductSubCategory> findByCategoryId(ProductCategory categoryId);
}
