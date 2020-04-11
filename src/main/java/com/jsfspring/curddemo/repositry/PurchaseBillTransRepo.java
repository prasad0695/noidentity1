package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.PurchaseBillTrans;

public interface PurchaseBillTransRepo extends JpaRepository<PurchaseBillTrans, Integer>{

}
