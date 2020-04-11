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
@Table(name="PURCHASE_ORDER_MASTER")
public class PurchaseOrderMaster implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="PO_NO")
	private int poNo;
	
	@Column(name="DATE")
	private Timestamp date;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUPPLIER_ID")
	private SupplierDomain supId;
	
	@Transient
	private String supplier;
	
	@Column(name="STATUS")
	private String  status="Pending";
	
	@Transient
	private String  statusColor;
	
	@Transient
	private boolean  selectAll;
	
	@Transient
	private boolean  select;
	
	@Transient
	private boolean editBoolean;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="poNo",cascade=CascadeType.ALL)
	private List<PurchaseOrderTrans> purchaseOrderTransList=new ArrayList<PurchaseOrderTrans>();
	
	@Transient
	private java.util.Date dateUtil;
	
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


	public List<PurchaseOrderTrans> getPurchaseOrderTransList() {
		return purchaseOrderTransList;
	}

	public void setPurchaseOrderTransList(List<PurchaseOrderTrans> purchaseOrderTransList) {
		this.purchaseOrderTransList = purchaseOrderTransList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}


	public void addTrans(PurchaseOrderTrans trans) {
		trans.setPoNo(this);
		purchaseOrderTransList.add(trans);
    }

	public int getPoNo() {
		return poNo;
	}

	public void setPoNo(int poNo) {
		this.poNo = poNo;
	}

	public String getSupplier() {
		if(supId!=null)
			supplier=supId.getName();
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public SupplierDomain getSupId() {
		return supId;
	}

	public void setSupId(SupplierDomain supId) {
		this.supId = supId;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}
	
}
