/**
 * 
 */
package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author s727953
 *
 */
@Entity
@Table(name = "EMP_INFO")
public class EmployeeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long empId;
	@Column(name = "EMP_NAME", nullable = false)
	private String empName;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	private String address1;
	@Column(name = "EMAIL_ID")
	private String emailId;
	@Column(name = "EMP_DEPT_ID")
	private Long empDeptId;

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

	public Long getEmpDeptId() {
		return empDeptId;
	}

	public void setEmpDeptId(Long empDeptId) {
		this.empDeptId = empDeptId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empDeptId == null) ? 0 : empDeptId.hashCode());
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
		EmployeeDTO other = (EmployeeDTO) obj;
		if (empDeptId == null) {
			if (other.empDeptId != null)
				return false;
		} else if (!empDeptId.equals(other.empDeptId))
			return false;
		if (empName == null) {
			if (other.empName != null)
				return false;
		} else if (!empName.equals(other.empName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [empId=" + empId + ", empName=" + empName + ", password=" + password + ", address1="
				+ address1 + ", emailId=" + emailId + ", empDeptId=" + empDeptId + "]";
	}

	// @Column(name = "addded_BY", insertable = true, updatable = false)
	// @NotNull
	// private String addedBy;
	// @Column(name = "ADDED_DT", insertable = true, updatable = false)
	// private Date addedDate;
	// @Column(name = "MODIFIED_BY", insertable = true, updatable = true)
	// private String modifiedby;
	// @Column(name = "MODIFIED_DT", insertable = true, updatable = true)
	// private Date modifedDate;

}
