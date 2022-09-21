
package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMPANY_CATEGORY_LIST")
public class CompanyCategoryList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
	private Company compId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID")
	private CompanyCategory categoryId;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public Company getCompId() {
		return compId;
	}

	public void setCompId(Company compId) {
		this.compId = compId;
	}

	public CompanyCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CompanyCategory categoryId) {
		this.categoryId = categoryId;
	}
}
