package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
@Table(name="DC_TRANS")
public class DeliveryChalanDomain implements Serializable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DeliveryChalanDomain(){
		
	}
	public DeliveryChalanDomain(DeliveryChalanMaster dcMaster,BillTransDomain billTrans){
		this.dcMaster=dcMaster;
		this.setItems(billTrans.getProductId());
    	uom=billTrans.getUom();
    	nos=billTrans.getQty();
    	billTransNo=billTrans;
	}
	public DeliveryChalanDomain(BillTransDomain billTrans){
		dcMaster=billTrans.getBillMaster().getDcMaster();
		items=billTrans.getProductId();
    	uom=billTrans.getUom();
    	nos=billTrans.getQty();
    	billTransNo=billTrans;
	}
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="ROW_ID")
private int rowId;

@OneToOne(fetch=FetchType.LAZY)
@JoinColumn(name="ITEMS")
private ProductDomain items;

@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "UOM")
private ProductUom uom;

@Column(name="NOS")
private double nos;

@Transient
private double nosForEdit;

@Transient
private ProductDomain productForEdit;

@Transient
private ProductUom uomForEdit;

@Transient
private ProductDomain product;

@Transient
private String itemTrans;

@Transient
private int hsnTrans;

@Transient
private int slNo;

@Transient
private double rate;

@Transient
private double cgst;

@Transient
private double sgst;

@Transient
private double cgst25;

@Transient
private double sgst25;

@Transient
private double cgst6;

@Transient
private double sgst6;

@Transient
private double cgst9;

@Transient
private double sgst9;

@Transient
private double cgst14;

@Transient
private double sgst14;

@Transient
private double totalCgst25;

@Transient
private double totalSgst25;

@Transient
private double totalCgst6;

@Transient
private double totalSgst6;

@Transient
private double totalCgst9;

@Transient
private double totalSgst9;

@Transient
private double totalCgst14;

@Transient
private double totalSgst14;

@Transient
private double amount;

@Transient
private boolean editBoolean;

@Transient
private String decimalPattern;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "DC_NO")
private DeliveryChalanMaster dcMaster;

@OneToOne(fetch=FetchType.LAZY)
@JoinColumn(name="BILL_TRANS_NO")
private BillTransDomain billTransNo;

public List<ProductUom> getProdUomList(String query){
	if(items!=null)
		return items.getProdUomTransList();
	return null;
}

public int getRowId() {
	return rowId;
}

public void setRowId(int rowId) {
	this.rowId = rowId;
}

public double getRate() {
	return rate;
}

public void setRate(double rate) {
	this.rate = rate;
}

public double getAmount() {
	return amount;
}

public double getCgst25() {
	return cgst25;
}

public void setCgst25(double cgst25) {
	this.cgst25 = cgst25;
}

public double getSgst25() {
	return sgst25;
}

public void setSgst25(double sgst25) {
	this.sgst25 = sgst25;
}

public double getCgst6() {
	return cgst6;
}

public void setCgst6(double cgst6) {
	this.cgst6 = cgst6;
}

public double getSgst6() {
	return sgst6;
}

public void setSgst6(double sgst6) {
	this.sgst6 = sgst6;
}

public double getCgst9() {
	return cgst9;
}

public void setCgst9(double cgst9) {
	this.cgst9 = cgst9;
}

public double getSgst9() {
	return sgst9;
}

public void setSgst9(double sgst9) {
	this.sgst9 = sgst9;
}

public double getCgst14() {
	return cgst14;
}

public void setCgst14(double cgst14) {
	this.cgst14 = cgst14;
}

public double getSgst14() {
	return sgst14;
}

public void setSgst14(double sgst14) {
	this.sgst14 = sgst14;
}

public void setAmount(double amount) {
	this.amount = amount;
}

public double getCgst() {
	return cgst;
}

public void setCgst(double cgst) {
	this.cgst = cgst;
}

public double getSgst() {
	return sgst;
}

public void setSgst(double sgst) {
	this.sgst = sgst;
}

public int getSlNo() {
	return slNo;
}

public void setSlNo(int slNo) {
	this.slNo = slNo;
}

public String getItemTrans() {
	if(items!=null)
		itemTrans=items.getItem();
	return itemTrans;
}

public void setItemTrans(String itemTrans) {
	this.itemTrans = itemTrans;
}

public int getHsnTrans() {
	return hsnTrans;
}

public void setHsnTrans(int hsnTrans) {
	this.hsnTrans = hsnTrans;
}

public ProductDomain getProduct() {
	return product;
}

public void setProduct(ProductDomain product) {
	this.product = product;
}

public DeliveryChalanMaster getDcMaster() {
	return dcMaster;
}

public void setDcMaster(DeliveryChalanMaster dcMaster) {
	this.dcMaster = dcMaster;
}

public ProductDomain getItems() {
	return items;
}

public void setItems(ProductDomain items) {
	this.items = items;
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
	this.uom = uom;
//	NumberFormat numberFormatter = new DecimalFormat("##");
//	String result = numberFormatter.format(0);
//	System.out.println(result);
//	this.nos=Double.parseDouble(result);
	double time = 0;
	DecimalFormat df = new DecimalFormat("#.##");      
	this.nos = Double.valueOf(df.format(time));
	System.out.println(this.nos);
}
public int getDecinalPattern(ProductUom uom) {
	System.out.println("uom.getUomId().getDecPattern()"+uom.getUomId().getDecPattern());
	return uom.getUomId().getDecPattern();
}
public double getNos() {
	return nos;
}

public void setNos(double nos) {
	this.nos = nos;
}
public BillTransDomain getBillTransNo() {
	return billTransNo;
}
public void setBillTransNo(BillTransDomain billTransNo) {
	this.billTransNo = billTransNo;
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
public String getDecimalPattern() {
	return decimalPattern;
}
public void setDecimalPattern(String decimalPattern) {
	this.decimalPattern = decimalPattern;
}








}
