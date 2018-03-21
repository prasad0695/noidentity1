/**
 * 
 */
/**
 * @author s727953
 *
 */
package com.jsfspring.curddemo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsfspring.curddemo.entity.EmployeeDTO;
import com.jsfspring.curddemo.repositry.EmployeeRepo;
import com.jsfspring.curddemo.service.EmployeeService;
import com.jsfspring.curddemo.ui.EmployeeUITO;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepo employeeRepo;

	@Override
	@Transactional(readOnly = false)
	public EmployeeUITO doSaveEmp(EmployeeUITO employeeUiTO) {
		EmployeeDTO dto = new EmployeeDTO();
		BeanUtils.copyProperties(employeeUiTO, dto);
		dto = employeeRepo.save(dto);
		BeanUtils.copyProperties(dto, employeeUiTO);
		return employeeUiTO;
	}

	@Override
	public List<EmployeeUITO> doFetchAllEmp() {
		List<EmployeeDTO> dtoLst = employeeRepo.findAll();
		List<EmployeeUITO> uiTOLst = new ArrayList<>();
		dtoLst.forEach(x -> {
			EmployeeUITO tmpUiTO = new EmployeeUITO();
			System.out.println(x.toString());
			BeanUtils.copyProperties(x, tmpUiTO);
			uiTOLst.add(tmpUiTO);
		});
		return uiTOLst;
	}

	@Override
	public EmployeeUITO doGetEmp(EmployeeUITO employeeUiTO) {
		if (null != employeeUiTO.getEmailId()) {
			EmployeeDTO dto = new EmployeeDTO();

			BeanUtils.copyProperties(employeeUiTO, dto);
			dto = employeeRepo.getOne(dto.getEmpId());

			BeanUtils.copyProperties(dto, employeeUiTO);
		}
		return employeeUiTO;
	}

	@Override
	@Transactional
	public void doDeleteEmp(EmployeeUITO employeeUiTO) {

		employeeRepo.deleteById(employeeUiTO.getEmpId());
	}

}