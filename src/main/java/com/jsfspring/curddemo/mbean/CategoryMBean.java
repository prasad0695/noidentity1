package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.CompanyCategory;
import com.jsfspring.curddemo.entity.CompanyCategoryList;
import com.jsfspring.curddemo.entity.ProductCategory;
import com.jsfspring.curddemo.entity.ProductSubCategory;
import com.jsfspring.curddemo.repositry.CompanyCategoryRepo;
import com.jsfspring.curddemo.repositry.ProductCategoryRepo;
import com.jsfspring.curddemo.repositry.ProductSubCategoryRepo;

@Controller("categoryMBean")
@SessionScope
public class CategoryMBean {

	@Autowired
	public CompanyCategoryRepo companyCategoryRepo;
	
	@Autowired
	public ProductCategoryRepo productCategoryRepo;
	
	@Autowired
	public ProductSubCategoryRepo productSubCategoryRepo;
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	

	private CompanyCategory category; 
	public List<CompanyCategory> categoryList; 
	public List<ProductCategory> productCategoryList;
	public List<ProductSubCategory> productSubCategoryList;
	public List<CompanyCategoryList> companySubCategoryList;
	String categoryOverview = "/jsfspring/pages/Category/companyCategory.xhtml";
	String companyCategories = "/jsfspring/pages/Category/companyCategories.xhtml";
	String productCategoryPage = "/jsfspring/pages/Category/productCategory.xhtml";
	String productSubCategoryPage = "/jsfspring/pages/Category/productSubCategory.xhtml";
	String CompanyCatproductSubCatPage = "/jsfspring/pages/Category/companyCatProductSubCat.xhtml";

	public CategoryMBean() {
		category = new CompanyCategory();
		categoryList= new ArrayList<CompanyCategory>();
		productCategoryList = new ArrayList<ProductCategory>();
		productSubCategoryList = new ArrayList<ProductSubCategory>();
		companySubCategoryList = new ArrayList<CompanyCategoryList>();
	}

	public void newCompanyCategory() {
		reset();
		sukiBaseBean.pageRedirect(categoryOverview);
		sukiBaseBean.t = category;
		sukiBaseBean.overviewList();
	}

	public void reset() {
		category = new CompanyCategory();
	}

	public void saveCompanyCategory() {
		try {
			boolean flag=false;
			if(sukiBaseBean.validateString(category.getCategory(), "Category Name"))
				flag=true;
			if(flag)
				return;
			if (category.getRowId() > 0) {
//				company = CommonAPI.getInstance().update(company);
				sukiBaseBean.addMessage("Category", "Updated Successfullly");
			} else {
//				company = CommonAPI.getInstance().save(company);
				sukiBaseBean.addMessage("Category", "Saved Successfullly");
			}
			category=companyCategoryRepo.save(category);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCategoryObjActionEvent(ActionEvent event) {
		category = new CompanyCategory();
		category = companyCategoryRepo.findById(sukiBaseBean.actionEvent(event)).get();
	}

	public void delete() {
//			company = (Company) sukiBaseBean.t;
		companyCategoryRepo.delete(category);
			sukiBaseBean.addMessage("Category", "Deleted Successfullly");
			sukiBaseBean.dialogHide();
			category = new CompanyCategory();
	}

	public void getCompanyCategoryList() {
		categoryList = companyCategoryRepo.findAll();
		sukiBaseBean.pageRedirect(companyCategories);
	}
	
	public void getProductCategory() {
		productCategoryList = productCategoryRepo.findAll();
		sukiBaseBean.pageRedirect(productCategoryPage);
	}
	
	public void getProductSubCategory(ProductCategory categoryId) {
		productSubCategoryList = new ArrayList<ProductSubCategory>();
		productSubCategoryList = productSubCategoryRepo.findByCategoryId(categoryId);
		sukiBaseBean.pageRedirect(productSubCategoryPage);
	}
	
	public void getCompanySubCategory(CompanyCategory categoryId) {
		companySubCategoryList = new ArrayList<CompanyCategoryList>();
		companySubCategoryList = categoryId.getCategoryList();
		sukiBaseBean.pageRedirect(CompanyCatproductSubCatPage);
	}
	
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}

	public CompanyCategoryRepo getCompanyCategoryRepo() {
		return companyCategoryRepo;
	}

	public void setCompanyCategoryRepo(CompanyCategoryRepo companyCategoryRepo) {
		this.companyCategoryRepo = companyCategoryRepo;
	}

	public SukiBaseBean getSukiBaseBean() {
		return sukiBaseBean;
	}

	public void setSukiBaseBean(SukiBaseBean sukiBaseBean) {
		this.sukiBaseBean = sukiBaseBean;
	}

	public CompanyCategory getCategory() {
		return category;
	}

	public void setCategory(CompanyCategory category) {
		this.category = category;
	}

	public String getCategoryOverview() {
		return categoryOverview;
	}

	public void setCategoryOverview(String categoryOverview) {
		this.categoryOverview = categoryOverview;
	}

	public List<CompanyCategory> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CompanyCategory> categoryList) {
		this.categoryList = categoryList;
	}

	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}

	public List<ProductSubCategory> getProductSubCategoryList() {
		return productSubCategoryList;
	}

	public void setProductSubCategoryList(List<ProductSubCategory> productSubCategoryList) {
		this.productSubCategoryList = productSubCategoryList;
	}

	public List<CompanyCategoryList> getCompanySubCategoryList() {
		return companySubCategoryList;
	}

	public void setCompanySubCategoryList(List<CompanyCategoryList> companySubCategoryList) {
		this.companySubCategoryList = companySubCategoryList;
	}
}
