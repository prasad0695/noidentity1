package com.jsfspring.curddemo.entity;

public class CompanyWiseSalesDomain {
	
	private int companyId;
	private String companyName;
	private double totalSales;
	private double percentage;
	private double widthPercentage;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public double getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public double getWidthPercentage() {
		return widthPercentage;
	}
	public void setWidthPercentage(double widthPercentage) {
		this.widthPercentage = widthPercentage;
	}
	
	
}
