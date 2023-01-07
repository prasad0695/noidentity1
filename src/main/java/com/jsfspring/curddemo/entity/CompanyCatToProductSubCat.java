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
@Table(name="COMPANY_CAT_PRODUCT_SUB_CAT")
public class CompanyCatToProductSubCat implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="COMPANY_CATEGORY")
	private CompanyCategory categoryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_SUB_CATEGORY")
	private ProductSubCategory productSubCategory;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public CompanyCategory getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CompanyCategory categoryId) {
		this.categoryId = categoryId;
	}

	public ProductSubCategory getProductSubCategory() {
		return productSubCategory;
	}

	public void setProductSubCategory(ProductSubCategory productSubCategory) {
		this.productSubCategory = productSubCategory;
	}

}

