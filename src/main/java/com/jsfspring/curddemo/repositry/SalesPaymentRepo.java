package com.jsfspring.curddemo.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsfspring.curddemo.entity.SalesPaymentsDomain;

public interface SalesPaymentRepo extends JpaRepository<SalesPaymentsDomain, Integer>{
	

}
