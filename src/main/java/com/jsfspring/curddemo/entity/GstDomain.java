package com.jsfspring.curddemo.entity;

public class GstDomain {
	
	private double gstPercentage;
	private double gstAmount;
	private int billNo;
	
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
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

}
