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
@Table(name = "DC_MASTER")
public class DeliveryChalanMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DeliveryChalanMaster() {
		
	}
	public DeliveryChalanMaster(BillMasterDomain billMaster){
		date=billMaster.getDate();
		companyId=billMaster.getCompanyId();
		forBill=String.valueOf(billMaster.getBillNo());
		this.billNo=billMaster;
		status="Billed";
		billMaster.getBillTransList().parallelStream().forEach(i->{
			DeliveryChalanDomain dcTrans=new DeliveryChalanDomain(this,i);
			this.getDcTransList().add(dcTrans);
			i.setDcTrans(dcTrans);
			});
	//	poNo=purchaseBillMaster.getp
	}
	
	@Id
	@Column(name = "DC_NO")
	private int deliveryNo;

	@Column(name = "DATE")
	private Timestamp date;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	private Company companyId;

	@Column(name = "PDF_NAME")
	private String fileName;

	@Column(name = "PDF_FILE")
	private byte[] byteFilePdf;

	@Column(name = "STATUS")
	private String status="Pending";

	@Column(name = "FOR_BILL")
	private String forBill;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BILL_NO")
	private BillMasterDomain billNo;

	@Transient
	private java.util.Date dateUtil=SukiAppUtil.getCurrentUtilDate();

	@Transient
	private String month;

	@Transient
	private boolean select;

	@Transient
	private Long count;

	@Transient
	private java.util.Date fromDate;

	@Transient
	private java.util.Date toDate;

	@Transient
	private String company;
	
	@Transient
	private boolean editBoolean;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dcMaster",cascade= CascadeType.ALL)
	private List<DeliveryChalanDomain> dcTransList=new ArrayList<DeliveryChalanDomain>();

	public String getCompany() {
		if (getCompanyId() != null)
			company = getCompanyId().getCompName();
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public java.util.Date getDateUtil() {
		dateUtil = SukiAppUtil.getUtilDateFromSQLDate(date);
		return dateUtil;
	}

	public void setDateUtil(java.util.Date dateUtil) {
		date = SukiAppUtil.getTimeStampFromUtilDate(dateUtil);
		this.dateUtil = dateUtil;
	}

	public int getDeliveryNo() {
		return deliveryNo;
	}

	public void setDeliveryNo(int deliveryNo) {
		this.deliveryNo = deliveryNo;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public java.util.Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(java.util.Date fromDate) {
		this.fromDate = fromDate;
	}

	public java.util.Date getToDate() {
		return toDate;
	}

	public void setToDate(java.util.Date toDate) {
		this.toDate = toDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getByteFilePdf() {
		return byteFilePdf;
	}

	public void setByteFilePdf(byte[] byteFilePdf) {
		this.byteFilePdf = byteFilePdf;
	}

	public List<DeliveryChalanDomain> getDcTransList() {
		return dcTransList;
	}

	public void setDcTransList(List<DeliveryChalanDomain> dcTransList) {
		this.dcTransList = dcTransList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getForBill() {
		return forBill;
	}

	public void setForBill(String forBill) {
		this.forBill = forBill;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public void addTrans(DeliveryChalanDomain trans) {
		trans.setDcMaster(this);
		dcTransList.add(trans);
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}
	public BillMasterDomain getBillNo() {
		return billNo;
	}
	public void setBillNo(BillMasterDomain billNo) {
		this.billNo = billNo;
	}
}
