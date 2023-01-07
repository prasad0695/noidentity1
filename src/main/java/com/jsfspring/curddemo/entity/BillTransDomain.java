package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
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
@Table(name="BILL_TRANS")
public class BillTransDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne
	@JoinColumn(name="BILL_NO")
	private BillMasterDomain billMaster;

	@Column(name="PRODUCTS")
	private String products;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private ProductDomain productId;
	
	@Column(name="HSN")
	private int hsn;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UOM")
	private ProductUom uom;
	
	@Column(name="QUANTITY")
	private double qty;

	@Column(name="RATE")
	private double rate;
	
	@Column(name="AMOUNT")
	private double amount;
	
	@Column(name="GST")
	private double gst;
	
	@Column(name="PUR_RATE")
	private double purchaseRate;
	
	@OneToOne(orphanRemoval=true,mappedBy="billTransNo",targetEntity=DeliveryChalanDomain.class,
		       fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private DeliveryChalanDomain dcTrans;

	@Transient
	private double gstAmt;
	
	@Transient
	private double totalAmount;
	
	@Transient
	private String amountNumeric;
	
	@Transient
	private String stationOrHouse;
	
	@Transient
	private int slNo;
	
	@Transient
	private boolean editBoolean;
	
	@Transient
	private double mrpRate;
	
	@Transient
	private int decimalPattern;
	
	@Transient
	private List<PriceListForInvoice> priceList;
	
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
	
	public DeliveryChalanDomain getDcTransForBillUpdate(DeliveryChalanDomain trans,BillTransDomain billTrans){
		trans.setItems(billTrans.getProductId());
		trans.setUom(billTrans.getUom());
		trans.setNos(billTrans.getQty());
		return trans;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProducts() {
		if(getProductId()!=null){
			products=getProductId().getItem();
		}
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}
	
	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		if(rate>0) {
			mrpRate = rate + ((rate * gst)/100);
		}
		this.rate = rate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getHsn() {
		return hsn;
	}

	public void setHsn(int hsn) {
		this.hsn = hsn;
	}

	public String getAmountNumeric() {
		return amountNumeric;
	}

	public void setAmountNumeric(String amountNumeric) {
		this.amountNumeric = amountNumeric;
	}

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public String getStationOrHouse() {
		return stationOrHouse;
	}

	public void setStationOrHouse(String stationOrHouse) {
		this.stationOrHouse = stationOrHouse;
	}

	public BillMasterDomain getBillMaster() {
		return billMaster;
	}

	public void setBillMaster(BillMasterDomain billMaster) {
		this.billMaster = billMaster;
	}

	public ProductDomain getProductId() {
		return productId;
	}

	public void setProductId(ProductDomain productId) {
		this.productId = productId;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getGstAmt() {
		gstAmt=(getGst()*getAmount())/100;
		return gstAmt;
	}

	public void setGstAmt(double gstAmt) {
		this.gstAmt = gstAmt;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}

	public ProductUom getUom() {
		return uom;
	}

	public void setUom(ProductUom uom) {
		System.out.println("uom.getUomId().getDecPattern()" + uom.getUomId().getDecPattern());
		this.decimalPattern = uom.getUomId().getDecPattern();
		this.uom = uom;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public DeliveryChalanDomain getDcTrans() {
		return dcTrans;
	}

	public void setDcTrans(DeliveryChalanDomain dcTrans) {
		this.dcTrans = dcTrans;
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

	public List<PriceListForInvoice> getPriceList() {
		if(priceList==null) {
			return priceList = new ArrayList<PriceListForInvoice>();
		}
		return priceList;
	}

	public void setPriceList(List<PriceListForInvoice> priceList) {
		this.priceList = priceList;
	}

	public double getMrpRate() {
		return mrpRate;
	}

	public void setMrpRate(double mrpRate) {
		if(mrpRate>0) {
			rate = (mrpRate*100)/(100+gst);
		}
		this.mrpRate = mrpRate;
	}

	public double getPurchaseRate() {
		return purchaseRate;
	}

	public void setPurchaseRate(double purchaseRate) {
		this.purchaseRate = purchaseRate;
	}

	public int getDecimalPattern() {
		return decimalPattern;
	}

	public void setDecimalPattern(int decimalPattern) {
		this.decimalPattern = decimalPattern;
	}
}
