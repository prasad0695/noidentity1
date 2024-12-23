package com.jsfspring.curddemo.entity;

public class GstDomain {
	
	private double gstPercentage;
	private double gstAmount;

	private double gstValue;
	private int billNo;
	private String companyName;
	private String gst;
	private double totalAmt;
	
	public double getGstPercentage() {
		return gstPercentage;
	}
	public void setGstPercentage(double gstPercentage) {
		this.gstPercentage = gstPercentage;
	}
	public double getGstAmount() {
		return gstAmount;
	}
	public void setGstAmount(double gstAmount) {
		this.gstAmount = gstAmount;
	}

	public double getGstValue() {
		return gstValue;
	}

	public void setGstValue(double gstValue) {
		this.gstValue = gstValue;
	}

	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public double getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
}
