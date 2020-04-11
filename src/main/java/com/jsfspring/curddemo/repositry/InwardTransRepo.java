package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.InwardTrans;

public interface InwardTransRepo extends JpaRepository<InwardTrans, Integer>{

}
