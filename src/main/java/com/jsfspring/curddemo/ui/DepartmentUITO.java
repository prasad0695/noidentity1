package com.jsfspring.curddemo.ui;

import java.io.Serializable;

public class DepartmentUITO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long deptId;
	private String deptName;

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
