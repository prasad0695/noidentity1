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
@Table(name="INWARD_MASTER")
public class InwardMaster implements Serializable {

	/**
	 * 
	 */
	public InwardMaster(){
		
	}
	public InwardMaster(PurchaseBillMaster purchaseBillMaster){
		date=purchaseBillMaster.getDate();
		supId=purchaseBillMaster.getSupId();
		status="Received";
		this.purchaseBillMaster=purchaseBillMaster;
		purchaseBillMaster.getPurchaseBillTransList().parallelStream().forEach(i->{
			InwardTrans trans=new InwardTrans(this, i);
			this.getInwardTransList().add(trans);
			i.setInwardTransNo(trans);
			});
	//	poNo=purchaseBillMaster.getp
	}
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="INWARD_NO")
	private int inwardNo;
	
	@Column(name="DATE")
	private Timestamp date;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUPPLIER_ID")
	private SupplierDomain  supId;
	
	@Column(name="DC_NO")
	private String dcNo;
	
	@Column(name="INWARD_TYPE")
	private String inwardType;
	
	@OneToMany(orphanRemoval=true,mappedBy="inwardNo",targetEntity=InwardTrans.class,
		       fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<InwardTrans> inwardTransList=new ArrayList<InwardTrans>();
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PURCHASE_BILL_NO")
	private PurchaseBillMaster purchaseBillMaster;
	
	@Transient
	private String supplier;
	
	@Transient
	private String  billRender;
	
	@Transient
	private java.util.Date dateUtil;
	
	@Column(name="PO_NO")
	private String poNos;
	
	@Column(name="STATUS")
	private String status="Pending";
	
	@Transient
	private boolean editBoolean;
	
	public void addTrans(InwardTrans trans) {
		trans.setInwardNo(this);
		inwardTransList.add(trans);
	}
	
	public java.util.Date getDateUtil() {
		dateUtil=SukiAppUtil.getUtilDateFromSQLDate(date);
		return dateUtil;
	}

	public void setDateUtil(java.util.Date dateUtil) {
		date=SukiAppUtil.getTimeStampFromUtilDate(dateUtil);
		this.dateUtil = dateUtil;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public SupplierDomain getSupId() {
		return supId;
	}
	public void setSupId(SupplierDomain supId) {
		this.supId = supId;
	}
	
	public int getInwardNo() {
		return inwardNo;
	}

	public void setInwardNo(int inwardNo) {
		this.inwardNo = inwardNo;
	}

	public List<InwardTrans> getInwardTransList() {
		return inwardTransList;
	}

	public void setInwardTransList(List<InwardTrans> inwardTransList) {
		this.inwardTransList = inwardTransList;
	}

	public PurchaseBillMaster getPurchaseBillMaster() {
		return purchaseBillMaster;
	}

	public void setPurchaseBillMaster(PurchaseBillMaster purchaseBillMaster) {
		this.purchaseBillMaster = purchaseBillMaster;
	}
	public String getDcNo() {
		return dcNo;
	}
	public void setDcNo(String dcNo) {
		this.dcNo = dcNo;
	}
	public String getInwardType() {
		return inwardType;
	}
	public void setInwardType(String inwardType) {
		this.inwardType = inwardType;
	}
	public String getPoNos() {
		return poNos;
	}
	public void setPoNos(String poNos) {
		this.poNos = poNos;
	}
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
	public String getSupplier() {
		if(supId!=null)
			supplier=supId.getName();
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

}
