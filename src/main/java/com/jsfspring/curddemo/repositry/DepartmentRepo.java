package com.jsfspring.curddemo.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsfspring.curddemo.entity.DepartmentDTO;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentDTO, Long> {

	DepartmentDTO findTitleByDeptName(String deptName);
}