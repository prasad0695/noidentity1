package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="UNIT_MASTER")
public class UnitMasterDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy= GenerationType.AUTO)
	@Column(name="ROW_ID")
	private int rowId=0;

	@Column(name="UNIT_NAME")
	private String unitName;
	
	@Column(name="DECIMAL_PATTERN")
	private int decPattern;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getDecPattern() {
		return decPattern;
	}

	public void setDecPattern(int decPattern) {
		this.decPattern = decPattern;
	}
	
	

}
