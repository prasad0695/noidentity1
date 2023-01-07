package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
@Table(name="PURCHASE_ORDER_TRANS")
public class PurchaseOrderTrans implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ROW_ID")
	private int rowId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PO_NO")
	private PurchaseOrderMaster poNo;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PRODUCT_ID")
	private ProductDomain items;
	
	@Column(name="NOS")
	private double nos;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UOM")
	private ProductUom uom;
	
	@Column(name="RECEIVED")
	private double received;
	
	@Column(name="STATUS")
	private String  status="Pending";
	
	@Transient
	private boolean editBoolean;
	
	@Transient
	private int  receivedTrans;
	
	@Transient
	private int  slNo;
	
	@Transient
	private String productTrans;
	
	@Transient
	private double  rate;
	
	@Transient
	private double  gst;
	
	@Transient
	private double  gst5;
	
	@Transient
	private double  gst12;
	
	@Transient
	private double  gst18;
	
	@Transient
	private double  gst28;
	
	@Transient
	private double amount;
	
	@Transient
	private int nosTrans;
	
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

	public void setNos(int nos) {
		this.nos = nos;
	}

	public int getSlNo() {
		return slNo;
	}

	public void setSlNo(int slNo) {
		this.slNo = slNo;
	}

	public String getProductTrans() {
		if(items!=null) {
			productTrans=items.getItem();
		}
		return productTrans;
	}

	public void setProductTrans(String productTrans) {
		this.productTrans = productTrans;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getGst5() {
		return gst5;
	}

	public void setGst5(double gst5) {
		this.gst5 = gst5;
	}

	public double getGst12() {
		return gst12;
	}

	public void setGst12(double gst12) {
		this.gst12 = gst12;
	}

	public double getGst18() {
		return gst18;
	}

	public void setGst18(double gst18) {
		this.gst18 = gst18;
	}

	public double getGst28() {
		return gst28;
	}

	public PurchaseOrderMaster getPoNo() {
		return poNo;
	}

	public void setPoNo(PurchaseOrderMaster poNo) {
		this.poNo = poNo;
	}

	public void setGst28(double gst28) {
		this.gst28 = gst28;
	}

	public int getReceivedTrans() {
		return receivedTrans;
	}

	public void setReceivedTrans(int receivedTrans) {
		this.receivedTrans = receivedTrans;
	}

	public int getNosTrans() {
		return nosTrans;
	}

	public void setNosTrans(int nosTrans) {
		this.nosTrans = nosTrans;
	}

	public ProductDomain getItems() {
		return items;
	}

	public ProductUom getUom() {
		return uom;
	}

	public void setUom(ProductUom uom) {
		this.uom = uom;
	}

	public void setItems(ProductDomain items) {
		this.items = items;
	}

	public double getNos() {
		return nos;
	}

	public void setNos(double nos) {
		this.nos = nos;
	}

	public double getReceived() {
		return received;
	}

	public void setReceived(double received) {
		this.received = received;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}
	
}
