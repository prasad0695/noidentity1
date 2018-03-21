/**
 * 
 */
package com.jsfspring.curddemo.uito;

import java.io.Serializable;

/**
 * @author s727953
 *
 */
public class EmployeeUITO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4840260415183676656L;
	private Long empId;
	private String empName;
	private String password;
	private String address1;
	private String emailId;
	private DepartmentUITO departmentUITO;

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public DepartmentUITO getDepartmentUITO() {
		return departmentUITO;
	}

	public void setDepartmentUITO(DepartmentUITO departmentUITO) {
		this.departmentUITO = departmentUITO;
	}

	@Override
	public String toString() {
		return "EmployeeUITO [empId=" + empId + ", empName=" + empName + ", password=" + password + ", address1="
				+ address1 + ", emailId=" + emailId + ", departmentUITO=" + departmentUITO + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empName == null) ? 0 : empName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeUITO other = (EmployeeUITO) obj;
		if (empName == null) {
			if (other.empName != null)
				return false;
		} else if (!empName.equals(other.empName))
			return false;
		return true;
	}

}
