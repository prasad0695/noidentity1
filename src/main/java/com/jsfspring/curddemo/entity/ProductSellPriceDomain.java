package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PRODUCT_SELL_PRICE")
public class ProductSellPriceDomain implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;

	@Column(name="DATE")
	private Timestamp date;

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private ProductDomain product;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="UOM_ID")
	private ProductUom productUom;
	
	@Column(name="RATE")
	private double rate;

	@Column(name="SELL_PRICE")
	private double sellPrice;
	
	@Column(name="MRP")
	private double mrp;
	
	@Column(name="ENABLE")
	private boolean enable;
	
	@Transient
	private int tableRowId;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public ProductDomain getProduct() {
		return product;
	}

	public void setProduct(ProductDomain product) {
		this.product = product;
	}

	public ProductUom getProductUom() {
		return productUom;
	}

	public void setProductUom(ProductUom productUom) {
		this.productUom = productUom;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		this.mrp = mrp;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getTableRowId() {
		return tableRowId;
	}

	public void setTableRowId(int tableRowId) {
		this.tableRowId = tableRowId;
	}
}
