package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsfspring.curddemo.utills.SukiAppUtil;

@Entity
@Table(name="PURCHASE_BILL_MASTER")
public class PurchaseBillMaster implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ROW_ID")
	private int rowId;
	
	@Column(name="BILL_NO")
	private String billNo;

	@Column(name="DATE")
	private Timestamp date;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="SUPPLIER_ID")
	private SupplierDomain supId;
	
	@Column(name="TOTAL_AMOUNT")
	private double totalAmount;
	
	@Column(name="STATUS")
	private String status="Balance";
	
	@Column(name="PAID_AMT")
	private double paidAmt;
	
	@Column(name="FOR_PO")
	private String poNos;
	
	@Column(name="INVOICE_TYPE")
	private String invoiceType;
	
	@OneToMany(orphanRemoval=true,mappedBy="masterRowId",targetEntity=PurchaseBillTrans.class,
		       fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<PurchaseBillTrans> purchaseBillTransList=new ArrayList<PurchaseBillTrans>();
	
//	@OneToOne(orphanRemoval=true,mappedBy="purchaseBillMaster",targetEntity=InwardMaster.class,
//		       fetch=FetchType.LAZY,cascade = CascadeType.ALL)
//	private InwardMaster inwardMaster=new InwardMaster();
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "PAYMENT_NO")
	private PurchasePaymentsDomain paymentNo;
	
	@OneToMany(orphanRemoval=true,mappedBy="purchaseBillMaster",targetEntity=InwardMaster.class,fetch=FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<InwardMaster> inwardMasterList=new ArrayList<InwardMaster>();
	
	@Transient
	private double totalWithoutTax;
	
	@Transient
	private Map<Double,Double> gstValue;
	
	@Transient
	private List<Map.Entry<Double,Double>> mapList;
	
	@Transient
	private String amountString;
	
	@Transient
	private String supplier;
	
	@Transient
	private double taxable;
	
	@Transient
	private boolean select;
	
	@Transient
	private boolean editBoolean;
	
	@Transient
	private java.util.Date dateUtil;
	
	public void addTrans(PurchaseBillTrans trans) {
		trans.setMasterRowId(this);
		purchaseBillTransList.add(trans);
		System.out.println("LIST SIZE---"+purchaseBillTransList.size());
	}
	
	public void setMapList(List<Map.Entry<Double, Double>> mapList) {
		this.mapList = mapList;
	}

	public List<Map.Entry<Double,Double>> getMapList() {
	    if (gstValue == null) {
	        return null;
	    }
	    List<Map.Entry<Double,Double>> list = new ArrayList<Map.Entry<Double,Double>>();
	    list.addAll(gstValue.entrySet());
	    return list;
	}
	
	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPoNos() {
		return poNos;
	}

	public void setPoNos(String poNos) {
		this.poNos = poNos;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public double getTaxable() {
		return taxable;
	}

	public void setTaxable(double taxable) {
		this.taxable = taxable;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public List<PurchaseBillTrans> getPurchaseBillTransList() {
		return purchaseBillTransList;
	}

	public void setPurchaseBillTransList(List<PurchaseBillTrans> purchaseBillTransList) {
		this.purchaseBillTransList = purchaseBillTransList;
	}

	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public void setSupId(SupplierDomain supId) {
		this.supId = supId;
	}
	public java.util.Date getDateUtil() {
		dateUtil=SukiAppUtil.getUtilDateFromSQLDate(date);
		return dateUtil;
	}

	public void setDateUtil(java.util.Date dateUtil) {
		date=SukiAppUtil.getTimeStampFromUtilDate(dateUtil);
		this.dateUtil = dateUtil;
	}

	public Map<Double, Double> getGstValue() {
		return gstValue;
	}

	public void setGstValue(Map<Double, Double> gstValue) {
		this.gstValue = gstValue;
	}

	public SupplierDomain getSupId() {
		return supId;
	}

	public double getTotalWithoutTax() {
		return totalWithoutTax;
	}

	public void setTotalWithoutTax(double totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}

//	public InwardMaster getInwardMaster() {
//		return inwardMaster;
//	}
//
//	public void setInwardMaster(InwardMaster inwardMaster) {
//		this.inwardMaster = inwardMaster;
//	}

	public PurchasePaymentsDomain getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(PurchasePaymentsDomain paymentNo) {
		this.paymentNo = paymentNo;
	}

	public double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getSupplier() {
		if(supId!=null)
			supplier=supId.getName();
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public List<InwardMaster> getInwardMasterList() {
		return inwardMasterList;
	}

	public void setInwardMasterList(List<InwardMaster> inwardMasterList) {
		this.inwardMasterList = inwardMasterList;
	}
}
