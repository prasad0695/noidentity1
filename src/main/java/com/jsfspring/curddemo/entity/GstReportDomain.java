package com.jsfspring.curddemo.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GstReportDomain {
	
	private List<GstDomain> gstReportList=new ArrayList<GstDomain>();
	
	private double totalPurchaseAmount;
	
	private double totalGstAmount;

	public List<GstDomain> getGstReportList() {
		return gstReportList;
	}

	public void setGstReportList(List<GstDomain> gstReportList) {
		this.gstReportList = gstReportList;
	}

	public double getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}

	public void setTotalPurchaseAmount(double totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}

	public double getTotalGstAmount() {
		return totalGstAmount;
	}

	public void setTotalGstAmount(double totalGstAmount) {
		this.totalGstAmount = totalGstAmount;
	}
	
	
}
