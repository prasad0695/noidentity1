package com.jsfspring.curddemo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommonReportDomain {
	public Map<String,List<String>> dropDownList;
	List<String> selectItemList;
	public  CommonReportDomain(){
		dropDownList=new HashMap<String,List<String>>();
		selectItemList=new ArrayList<String>();
		selectItemList.add("DC");
		selectItemList.add("INVOICE");
		selectItemList.add("PATMENT");
		dropDownList.put("Company", selectItemList);
		selectItemList=new ArrayList<String>();
		selectItemList.add("PO");
		selectItemList.add("INWARD");
		selectItemList.add("INVOICE");
		selectItemList.add(new String("PATMENT"));
		dropDownList.put("Supplier", selectItemList);
		selectItemList=new ArrayList<String>();
		selectItemList.add("DC");
		selectItemList.add("SALES INVOICE");
		selectItemList.add("PO");
		selectItemList.add("INWARD");
		selectItemList.add("PURCHASE INVOICE");
		selectItemList.add("QUOTATION");
		dropDownList.put("Product", selectItemList);
	}
	public String type;
	
	public Company company;
	
	public SupplierDomain supplier;
	
	public ProductDomain product;
	
	public String[] selectedVariant;
	
	public List<CommonReportDomainList> list=new ArrayList<CommonReportDomainList>();
	
	public List<String> getList(){
		selectItemList=new ArrayList<String>();
		if(type!=null)
			selectItemList=dropDownList.get(type);
		System.out.println("LIST----"+selectItemList);
		return selectItemList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getSelectedVariant() {
		return selectedVariant;
	}

	public void setSelectedVariant(String[] selectedVariant) {
		this.selectedVariant = selectedVariant;
	}

	public void setList(List<CommonReportDomainList> list) {
		this.list = list;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SupplierDomain getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierDomain supplier) {
		this.supplier = supplier;
	}

	public ProductDomain getProduct() {
		return product;
	}

	public void setProduct(ProductDomain product) {
		this.product = product;
	}

	public Map<String, List<String>> getDropDownList() {
		return dropDownList;
	}

	public void setDropDownList(Map<String, List<String>> dropDownList) {
		this.dropDownList = dropDownList;
	}

	public List<String> getSelectItemList() {
		return selectItemList;
	}

	public void setSelectItemList(List<String> selectItemList) {
		this.selectItemList = selectItemList;
	}

}
