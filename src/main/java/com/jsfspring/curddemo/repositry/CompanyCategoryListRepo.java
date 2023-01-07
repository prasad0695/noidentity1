package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.CompanyCategory;
import com.jsfspring.curddemo.entity.CompanyCategoryList;

public interface CompanyCategoryListRepo extends JpaRepository<CompanyCategoryList,Integer>{

}
