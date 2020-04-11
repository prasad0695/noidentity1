package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
@Table(name="PRODUCT_UOM")
public class ProductUom implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy= GenerationType.AUTO)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	private ProductDomain productId;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UOM_ID")
	private UnitMasterDomain uomId;

	@Column(name="PRICE")
	private double price=0;
	
	@Column(name="STOCK")
	private double stock;
	
	@Column(name="OPENING")
	private double opening;
	
	@Transient
	private Integer uomIdTrans=0;
	
	@Transient
	private String uomName;
	
	@Transient
	private boolean editBoolean;

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	

	public UnitMasterDomain getUomId() {
		return uomId;
	}

	public void setUomId(UnitMasterDomain uomId) {
		this.uomId = uomId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getUomIdTrans() {
		return uomIdTrans;
	}

	public void setUomIdTrans(Integer uomIdTrans) {
		uomId=new UnitMasterDomain();
		uomId.setRowId(uomIdTrans);
		this.uomIdTrans = uomIdTrans;
	}

	public ProductDomain getProductId() {
		return productId;
	}

	public void setProductId(ProductDomain productId) {
		this.productId = productId;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public String getUomName() {
		uomName=uomId.getUnitName();
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public double getOpening() {
		return opening;
	}

	public void setOpening(double opening) {
		this.opening = opening;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}

	
	
}
