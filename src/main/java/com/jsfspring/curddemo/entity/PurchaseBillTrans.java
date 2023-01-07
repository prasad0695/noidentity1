package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.util.List;

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
@Table(name="PURCHASE_BILL_TRANS")
public class PurchaseBillTrans implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne
	@JoinColumn(name="MASTER_ROW_ID")
	private PurchaseBillMaster masterRowId;

	@OneToOne
	@JoinColumn(name="PRODUCT_ID")
	private ProductDomain productId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UOM")
	private ProductUom uom;
	
	@Column(name="QUANTITY")
	private double qty;

	@Column(name="RATE")
	private double rate;
	
	@Column(name="SELLING_RATE")
	private double sellingPrice;
	
	@Column(name="MRP")
	private double mrp;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="GST")
	private double gst;
	
	@OneToOne(orphanRemoval=true,mappedBy="purchaseBillTransNo",targetEntity=InwardTrans.class,
		       fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private InwardTrans inwardTransNo;
	
	@Transient
	private double gstAmt;
	
	@Transient
	private String  productTrans;
	
	@Transient
	private boolean editBoolean;
	
	@Transient
	private double totalAmount;
	
	public List<ProductUom> getProdUomList(String query){
		if(productId!=null)
			return productId.getProdUomTransList();
		return null;
	}
	
	@Transient
	private double nosForEdit;
	
	@Transient
	private ProductDomain productForEdit;

	@Transient
	private ProductUom uomForEdit;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRODUCT_SELL_ID")
	private ProductSellPriceDomain productSellPrice;
	
	public InwardTrans getInwardTransForBillUpdate(InwardTrans trans,PurchaseBillTrans billTrans){
		trans.setProductId(billTrans.getProductId());
		trans.setUom(billTrans.getUom());
		trans.setReceived(billTrans.getQty());
		return trans;
	}
	
	public double getRate() {
		return rate;
	}
	

	public void setRate(double rate) {
		if(rate>0) {
			ProductSellPriceDomain productSellPrice = getProductSellPrice();
			if(productSellPrice!=null) {
		if(productSellPrice.getRowId()>0 && productSellPrice.getRate()!=rate)
			productSellPrice.setRowId(0);
			productSellPrice.setRate(rate);
			productSellPrice.setEnable(true);
		}}
		this.rate = rate;
	}

	public double getAmount() {
		amount = getRate() * getQty();
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getProductTrans() {
		if(productId!=null)
			productTrans=productId.getItem();
		return productTrans;
	}

	public void setProductTrans(String productTrans) {
		this.productTrans = productTrans;
	}

	public PurchaseBillMaster getMasterRowId() {
		return masterRowId;
	}

	public void setMasterRowId(PurchaseBillMaster masterRowId) {
		this.masterRowId = masterRowId;
	}

	public ProductDomain getProductId() {
		return productId;
	}

	public void setProductId(ProductDomain productId) {
		this.productId = productId;
	}

	public ProductUom getUom() {
		return uom;
	}

	public void setUom(ProductUom uom) {
		this.uom = uom;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}

	public double getGstAmt() {
		gstAmt=(getGst()*getAmount())/100;
		return gstAmt;
	}

	public void setGstAmt(double gstAmt) {
		this.gstAmt = gstAmt;
	}

	public double getTotalAmount() {
		totalAmount = getAmount() + getGstAmt();
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public InwardTrans getInwardTransNo() {
		return inwardTransNo;
	}

	public void setInwardTransNo(InwardTrans inwardTransNo) {
		this.inwardTransNo = inwardTransNo;
	}

	public double getNosForEdit() {
		return nosForEdit;
	}

	public void setNosForEdit(double nosForEdit) {
		this.nosForEdit = nosForEdit;
	}

	public ProductDomain getProductForEdit() {
		return productForEdit;
	}

	public void setProductForEdit(ProductDomain productForEdit) {
		this.productForEdit = productForEdit;
	}

	public ProductUom getUomForEdit() {
		return uomForEdit;
	}

	public void setUomForEdit(ProductUom uomForEdit) {
		this.uomForEdit = uomForEdit;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		if(sellingPrice>0) {
			ProductSellPriceDomain productSellPrice = getProductSellPrice();
			if(productSellPrice!=null) {
			productSellPrice.setSellPrice(sellingPrice);
			productSellPrice.setEnable(true);
		}}
		this.sellingPrice = sellingPrice;
	}

	public double getMrp() {
		return mrp;
	}

	public void setMrp(double mrp) {
		if(mrp>0) {
			ProductSellPriceDomain productSellPrice = getProductSellPrice();
			if(productSellPrice!=null) {
		if(productSellPrice.getRowId()>0 && productSellPrice.getMrp()!=mrp)
			productSellPrice.setRowId(0);
			productSellPrice.setMrp(mrp);
			productSellPrice.setEnable(true);
		}}
		this.mrp = mrp;
	}

	public ProductSellPriceDomain getProductSellPrice() {
		return productSellPrice;
	}

	public void setProductSellPrice(ProductSellPriceDomain productSellPrice) {
		this.productSellPrice = productSellPrice;
	}

	
}
