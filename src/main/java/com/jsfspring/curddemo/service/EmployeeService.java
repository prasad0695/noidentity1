
package com.jsfspring.curddemo.service;

import java.util.List;

import com.jsfspring.curddemo.uito.EmployeeUITO;

public interface EmployeeService {
	EmployeeUITO doSaveEmp(EmployeeUITO employeeUiTO);

	List<EmployeeUITO> doFetchAllEmp();

	EmployeeUITO doGetEmp(EmployeeUITO employeeUiTO);

	void doDeleteEmp(EmployeeUITO employeeUiTO);
}