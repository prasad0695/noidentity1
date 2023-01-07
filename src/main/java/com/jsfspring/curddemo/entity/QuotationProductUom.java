package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="QUOTATION_PRODUCT_UOM")
public class QuotationProductUom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUOT_TRANS_ROW_ID")
	private QuotationTrans quotTransRowId;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UOM_ID")
	private UnitMasterDomain uomId;

	@Column(name="PRICE")
	private double price;
	
	@Column(name="GST")
	private double gst;
	
	@Column(name="PRICEWITHGST")
	private double priceWithGst;
	
	@Transient
	private String uomName;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public UnitMasterDomain getUomId() {
		return uomId;
	}

	public void setUomId(UnitMasterDomain uomId) {
		this.uomId = uomId;
	}

	public QuotationTrans getQuotTransRowId() {
		return quotTransRowId;
	}

	public void setQuotTransRowId(QuotationTrans quotTransRowId) {
		this.quotTransRowId = quotTransRowId;
	}

	public String getUomName() {
		uomName=uomId.getUnitName();
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getPriceWithGst() {
		priceWithGst = getPrice() + (getGst()*getPrice())/100;
		return priceWithGst;
	}

	public void setPriceWithGst(double priceWithGst) {
		this.priceWithGst = priceWithGst;
	}
	
	
}
