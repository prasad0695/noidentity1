package com.jsfspring.curddemo.utills;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsfspring.curddemo.service.DepartmentService;
import com.jsfspring.curddemo.uito.DepartmentUITO;

@Service
public class DeptConverter implements Converter {

	@Autowired
	private DepartmentService departmentService;

	private static final Logger LOG = LoggerFactory.getLogger(DeptConverter.class.getName());

	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
		LOG.info("getAsObject: " + departmentService);

		DepartmentUITO departmentUITO = new DepartmentUITO();
		departmentUITO.setDeptName(string);
		departmentUITO = departmentService.getDepartment(departmentUITO);
		System.out.println(departmentUITO.toString());
		return departmentUITO;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object obj) {
		LOG.info("getAsString obj class: " + obj.getClass().getName());
		if (obj instanceof DepartmentUITO) {
			DepartmentUITO dept = (DepartmentUITO) obj;
			LOG.info("getAsString def name: " + dept.getDeptName());
			return dept.getDeptName();
		} else {
			StringBuilder sbError = new StringBuilder("The object of class ");
			sbError.append(obj.getClass().getName()).append(" is not of DepartmentUITO");
			throw new ClassCastException(sbError.toString());
		}
	}
}