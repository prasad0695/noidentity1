package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.PurchaseBillMaster;

public interface PurchaseBillMasterRepo extends JpaRepository<PurchaseBillMaster, Integer>{

}
