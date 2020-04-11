package com.jsfspring.curddemo.entity;

public class ProductUnitWise {
	
	private String product;
	private double nos;
	private int rowId;
	private String unitName;
	private double amount;
	
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getNos() {
		return nos;
	}
	public void setNos(double nos) {
		this.nos = nos;
	}
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

}
