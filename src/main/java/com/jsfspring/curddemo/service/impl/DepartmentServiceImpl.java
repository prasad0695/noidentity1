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

import com.jsfspring.curddemo.entity.DepartmentDTO;
import com.jsfspring.curddemo.repositry.DepartmentRepo;
import com.jsfspring.curddemo.service.DepartmentService;
import com.jsfspring.curddemo.uito.DepartmentUITO;
import com.jsfspring.curddemo.uito.EmployeeUITO;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	public List<DepartmentUITO> getAllDepartment() {
		List<DepartmentUITO> departmentUITOLst = new ArrayList<>();
		List<DepartmentDTO> departmentDTOLst = departmentRepo.findAll();

		departmentDTOLst.forEach(dto -> {
			DepartmentUITO tmpUiTO = new DepartmentUITO();

			BeanUtils.copyProperties(dto, tmpUiTO);
			departmentUITOLst.add(tmpUiTO);
		});

		return departmentUITOLst;
	}

	@Override
	public DepartmentUITO getDepartment(DepartmentUITO departmentUITO) {
		System.out.println(">>>>> "+departmentUITO.getDeptName());
		DepartmentDTO dto = departmentRepo.findTitleByDeptName(departmentUITO.getDeptName());
		DepartmentUITO uito = new DepartmentUITO();

		BeanUtils.copyProperties(dto, uito);
		return uito;
	}
}