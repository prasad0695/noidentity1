package com.jsfspring.curddemo.entity;

import java.util.HashMap;
import java.util.Map;

public class DashBoardReportDomain {
	
	private Map<String,Double> salesByMonth;
	
	private double salesPending;
	
	private double purchasePending;
	
	public double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public double getGstCredit() {
		return gstCredit;
	}

	public void setGstCredit(double gstCredit) {
		this.gstCredit = gstCredit;
	}

	private double totalProfit;
	
	private double gstCredit;

	public Map<String, Double> getSalesByMonth() {
		return salesByMonth;
	}

	public void setSalesByMonth(Map<String, Double> salesByMonth) {
		this.salesByMonth = salesByMonth;
	}

	public double getSalesPending() {
		return salesPending;
	}

	public void setSalesPending(double salesPending) {
		this.salesPending = salesPending;
	}

	public double getPurchasePending() {
		return purchasePending;
	}

	public void setPurchasePending(double purchasePending) {
		this.purchasePending = purchasePending;
	}

}
