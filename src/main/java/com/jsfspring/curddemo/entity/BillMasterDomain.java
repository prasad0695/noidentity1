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
@Table(name="BILL_MASTER")
public class BillMasterDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="INVOICE_NO")
	private int billNo;

	@Column(name="DATE")
	private Timestamp date;

	@Transient
	private String company;
	
	@Column(name="PO_NO")
	private String poNo;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
	private Company companyId;

	@Column(name="TOTAL_AMOUNT")
	private double totalAmount;
	
	@Column(name="STATUS")
	private String status="Balance";
	
	@Column(name="PAID_AMT")
	private double paidAmt;
	
	@Column(name="PAID")
	private Timestamp paidOn;
	
	@Column(name="FOR_DC")
	private String dcNos;
	
	@Column(name="PDF_FILE")
	private byte[] byteFilePdf;
	
	@Column(name="FREIGHT_CHARGES")
	private double freightCharges;
	
	@Transient
	private String statusColor;
	
	@Transient
	private java.util.Date dateUtil;
	
	@Transient
	private boolean editBoolean;
	
	@OneToMany(orphanRemoval=true,mappedBy="billMaster",targetEntity=BillTransDomain.class,
		       fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<BillTransDomain> billTransList=new ArrayList<BillTransDomain>();
	
	@OneToOne(orphanRemoval=true,
		       fetch=FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name="DC_NO")
	private DeliveryChalanMaster dcMaster=new DeliveryChalanMaster();
	
	@OneToMany(orphanRemoval=true,mappedBy="billNo",targetEntity=DeliveryChalanMaster.class,fetch=FetchType.LAZY,cascade = {CascadeType.MERGE})
	private List<DeliveryChalanMaster> dcMasterList=new ArrayList<DeliveryChalanMaster>();
	
	@Transient
	private double taxable;
	
	@Transient
	private double totalWithoutTax;
	
	@Transient
	private String poNoTrans;
	
	@Transient
	private boolean select;
	
	@Transient
	private String amountString;
	
	@Column(name="INVOICE_TYPE")
	private String invoiceType;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
	@JoinColumn(name = "PAYMENT_NO")
	private SalesPaymentsDomain paymentNo;
	
	@Transient
	private Map<Double,Double> gstValue;
	
	@Transient
	private List<Map.Entry<Double,Double>> mapList;
	
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
	
	public void addTrans(BillTransDomain trans) {
		trans.setBillMaster(this);
		billTransList.add(trans);
	}
	
	public DeliveryChalanMaster getDcMasterFromBillMaster(BillMasterDomain billMaster) {
		DeliveryChalanMaster dcMaster=billMaster.getDcMaster();
		dcMaster.setCompanyId(billMaster.getCompanyId());
		dcMaster.setDate(billMaster.getDate());
		return dcMaster;
	}
	
	public double getTaxable() {
		return taxable;
	}

	public void setTaxable(double taxable) {
		this.taxable = taxable;
	}


	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
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

	public Timestamp getPaidOn() {
		return paidOn;
	}

	public void setPaidOn(Timestamp paidOn) {
		this.paidOn = paidOn;
	}

	public String getDcNos() {
		return dcNos;
	}

	public void setDcNos(String dcNos) {
		this.dcNos = dcNos;
	}

	public String getStatusColor() {
		return statusColor;
	}

	public void setStatusColor(String statusColor) {
		this.statusColor = statusColor;
	}

	public java.util.Date getDateUtil() {
		dateUtil=SukiAppUtil.getUtilDateFromSQLDate(date);
		return dateUtil;
	}

	public void setDateUtil(java.util.Date dateUtil) {
		date=SukiAppUtil.getTimeStampFromUtilDate(dateUtil);
		this.dateUtil = dateUtil;
	}

	public List<BillTransDomain> getBillTransList() {
		return billTransList;
	}

	public void setBillTransList(List<BillTransDomain> billTransList) {
		this.billTransList = billTransList;
	}

	public double getTotalWithoutTax() {
		return totalWithoutTax;
	}

	public void setTotalWithoutTax(double totalWithoutTax) {
		this.totalWithoutTax = totalWithoutTax;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getPoNoTrans() {
		return poNoTrans;
	}

	public void setPoNoTrans(String poNoTrans) {
		this.poNoTrans = poNoTrans;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public byte[] getByteFilePdf() {
		return byteFilePdf;
	}

	public void setByteFilePdf(byte[] byteFilePdf) {
		this.byteFilePdf = byteFilePdf;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public String getAmountString() {
		amountString=SukiAppUtil.getNumericWords(getTotalAmount());
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public Map<Double, Double> getGstValue() {
		return gstValue;
	}

	public void setGstValue(Map<Double, Double> gstValue) {
		this.gstValue = gstValue;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public boolean isEditBoolean() {
		return editBoolean;
	}

	public void setEditBoolean(boolean editBoolean) {
		this.editBoolean = editBoolean;
	}

	public DeliveryChalanMaster getDcMaster() {
		return dcMaster;
	}

	public void setDcMaster(DeliveryChalanMaster dcMaster) {
		this.dcMaster = dcMaster;
	}

	public SalesPaymentsDomain getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(SalesPaymentsDomain paymentNo) {
		this.paymentNo = paymentNo;
	}

	public double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getCompany() {
		if(companyId!=null){
			company=companyId.getCompName();
		}
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<DeliveryChalanMaster> getDcMasterList() {
		return dcMasterList;
	}

	public void setDcMasterList(List<DeliveryChalanMaster> dcMasterList) {
		this.dcMasterList = dcMasterList;
	}

	public double getFreightCharges() {
		return freightCharges;
	}

	public void setFreightCharges(double freightCharges) {
		this.freightCharges = freightCharges;
	}
	
}
