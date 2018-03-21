
package com.jsfspring.curddemo.service;

import java.util.List;

import com.jsfspring.curddemo.uito.DepartmentUITO;

public interface DepartmentService {

	List<DepartmentUITO> getAllDepartment(); 

	DepartmentUITO getDepartment(DepartmentUITO departmentUITO);

}