package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="VW_PRODUCT")
public class VWProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id	
	@Column(name="NO")
	private int no;

	@Column(name="DATE")
	private Timestamp date;

	@Column(name="NAME")
	private String name;

	@Column(name="TYPE")
	private String type;

	@Column(name="NOS")
	private double nos;
	
	@Column(name="UOM_ID")
	private int uomId;

	@Column(name="PRICE")
	private double price;
	
	@Column(name="ITEMS")
	private int items;
	
	@Transient
	private double amount;
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getNos() {
		return nos;
	}

	public void setNos(double nos) {
		this.nos = nos;
	}

	public int getUomId() {
		return uomId;
	}

	public void setUomId(int uomId) {
		this.uomId = uomId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public double getAmount() {
		amount=nos*price;
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	
}
