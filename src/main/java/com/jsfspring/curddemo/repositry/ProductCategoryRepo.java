package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.ProductCategory;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Integer>{

}
