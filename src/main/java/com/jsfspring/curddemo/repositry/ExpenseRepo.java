package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsfspring.curddemo.entity.ExpenseDomain;

public interface ExpenseRepo extends JpaRepository<ExpenseDomain, Integer>{

}
