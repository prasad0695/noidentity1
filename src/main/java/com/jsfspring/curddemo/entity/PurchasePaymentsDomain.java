package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsfspring.curddemo.utills.SukiAppUtil;

@Entity
@Table(name="PURCHASE_PAYMENTS")
public class PurchasePaymentsDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ROW_ID")
	private int paymentNo;
	
	@Transient
	private String companyName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_ID")
	private SupplierDomain supId;
	
	@Column(name="DATE")
	private Timestamp date;
	
	@Column(name="PAYMENT_MODE")
	private String paymentmode;
	
	@Column(name="CHEQUE_NO")
	private int chequeNo;

	@Column(name="AMOUNT")
	private double totalAmount;
	
	@Transient
	private java.util.Date paymentDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentNo",cascade= {CascadeType.MERGE})
	private List<PurchaseBillMaster> billMasterList=new ArrayList<PurchaseBillMaster>();
	
	@Transient
	private boolean selectAll;
	
	@Transient
	private double amountToPay;
	
	@Transient
	private boolean editBoolean;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(String paymentmode) {
		this.paymentmode = paymentmode;
	}

	public java.util.Date getPaymentDate() {
		paymentDate=SukiAppUtil.getUtilDateFromSQLDate(date);
		return paymentDate;
	}

	public void setPaymentDate(java.util.Date paymentDate) {
		date=SukiAppUtil.getTimeStampFromUtilDate(paymentDate);
		this.paymentDate = paymentDate;
	}

	
	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public int getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(int paymentNo) {
		this.paymentNo = paymentNo;
	}

	public int getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(int chequeNo) {
		this.chequeNo = chequeNo;
	}


	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}

	public double getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}

	public List<PurchaseBillMaster> getBillMasterList() {
		return billMasterList;
	}

	public void setBillMasterList(List<PurchaseBillMaster> billMasterList) {
		this.billMasterList = billMasterList;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public SupplierDomain getSupId() {
		return supId;
	}

	public void setSupId(SupplierDomain supId) {
		this.supId = supId;
	}

}
