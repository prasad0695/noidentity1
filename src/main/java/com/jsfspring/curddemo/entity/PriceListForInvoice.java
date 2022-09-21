package com.jsfspring.curddemo.entity;

import java.util.Date;

public class PriceListForInvoice {
	
	public String type;
	public Date date;
	public int number;
	public double price;
	
	public PriceListForInvoice() {
		
	}
	
	public PriceListForInvoice(String type, int number, Date date, double price) {
		this.type = type;
		this.number =number;
		this.date = date;
		this.price = price;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
