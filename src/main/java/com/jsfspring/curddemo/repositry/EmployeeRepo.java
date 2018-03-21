package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsfspring.curddemo.entity.EmployeeDTO;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeDTO, Long> {
}