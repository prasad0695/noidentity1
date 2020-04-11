package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.PurchasePaymentsDomain;

public interface PurchasePaymentRepo extends JpaRepository<PurchasePaymentsDomain, Integer>{

}
