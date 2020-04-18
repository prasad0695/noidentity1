package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name="INWARD_TRANS")
public class InwardTrans implements Serializable {
	/**
	 * 
	 */
	public InwardTrans(){
		
	}
	public InwardTrans(InwardMaster inwardMaster,PurchaseBillTrans billTrans){
    	inwardNo=inwardMaster;
    	productId=billTrans.getProductId();
    	uom=billTrans.getUom();
    	received=billTrans.getQty();
    	purchaseBillTransNo=billTrans;
    	status="Billed";
	}
	public InwardTrans(PurchaseBillTrans billTrans){
    	inwardNo=billTrans.getMasterRowId().getInwardMasterList().get(0);
    	productId=billTrans.getProductId();
    	uom=billTrans.getUom();
    	received=billTrans.getQty();
    	purchaseBillTransNo=billTrans;
	}
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ROW_ID")
	private int rowId;
	
	@OneToOne(fetch=FetchType.LAZY)
	
	@JoinColumn(name="INWARD_NO")
	private InwardMaster inwardNo;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private ProductDomain productId;
	
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="UOM")
	private ProductUom uom;
	
	@Column(name="RECEIVED")
	private double received;
	
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="PUR_BILL_TRANS_NO")
	private PurchaseBillTrans purchaseBillTransNo;
	
//	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
//	@JoinColumn(name="PO_TRANS_NO")
//	private PurchaseOrderTrans poTransNo;
	
	@Column(name="STATUS")
	private String status;
	
	@Transient
	private double nosForEdit;
	
	@Transient
	private ProductDomain productForEdit;

	@Transient
	private ProductUom uomForEdit;
	
	@Transient
	private String  productTrans;
	
	@Transient
	private boolean editBoolean;
	
	public List<ProductUom> getProdUomList(String query){
		System.out.println(productId);
		System.out.println(productId.getProdUomTransList());
		if(productId!=null)
			return productId.getProdUomTransList();
		return null;
	}
	
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
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

	public double getReceived() {
		return received;
	}

	public void setReceived(double received) {
		this.received = received;
	}

	public String getProductTrans() {
		if(productId!=null)
			productTrans=productId.getItem();
		return productTrans;
	}

	public void setProductTrans(String productTrans) {
		this.productTrans = productTrans;
	}

	public InwardMaster getInwardNo() {
		return inwardNo;
	}

	public void setInwardNo(InwardMaster inwardNo) {
		this.inwardNo = inwardNo;
	}

	public PurchaseBillTrans getPurchaseBillTransNo() {
		return purchaseBillTransNo;
	}

	public void setPurchaseBillTransNo(PurchaseBillTrans purchaseBillTransNo) {
		this.purchaseBillTransNo = purchaseBillTransNo;
	}
//	public PurchaseOrderTrans getPoTransNo() {
//		return poTransNo;
//	}
//	public void setPoTransNo(PurchaseOrderTrans poTransNo) {
//		this.poTransNo = poTransNo;
//	}
	public boolean isEditBoolean() {
		return editBoolean;
	}
	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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

}
