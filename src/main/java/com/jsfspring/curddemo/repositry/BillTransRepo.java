package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.BillTransDomain;

public interface BillTransRepo extends JpaRepository<BillTransDomain, Integer>{

}
