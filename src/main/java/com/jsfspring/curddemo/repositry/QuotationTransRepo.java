package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.QuotationTrans;

public interface QuotationTransRepo extends JpaRepository<QuotationTrans, Integer>{

}
