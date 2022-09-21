package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY")
public class ProductCategory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@Column(name="CATEGORY")
	private String category;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="categoryId")
    private List<ProductSubCategory> categoryList=new ArrayList<ProductSubCategory>();

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<ProductSubCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ProductSubCategory> categoryList) {
		this.categoryList = categoryList;
	}

	
}
