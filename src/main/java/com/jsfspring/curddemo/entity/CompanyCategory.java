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

import com.ocpsoft.pretty.faces.config.rewrite.Case;

@Entity
@Table(name="COMPANY_CATEGORY")
public class CompanyCategory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@Column(name="CATEGORY")
	private String category;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="categoryId")
    private List<CompanyCategoryList> categoryList=new ArrayList<CompanyCategoryList>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="categoryId", cascade = CascadeType.ALL)
    private List<CompanyCatToProductSubCat> compCatToProdSubCat=new ArrayList<CompanyCatToProductSubCat>();

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

	public List<CompanyCategoryList> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CompanyCategoryList> categoryList) {
		this.categoryList = categoryList;
	}

	public List<CompanyCatToProductSubCat> getCompCatToProdSubCat() {
		return compCatToProdSubCat;
	}

	public void setCompCatToProdSubCat(List<CompanyCatToProductSubCat> compCatToProdSubCat) {
		this.compCatToProdSubCat = compCatToProdSubCat;
	}
	
	

}
