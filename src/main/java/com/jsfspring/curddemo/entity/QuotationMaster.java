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
@Table(name="QUOTATION_MASTER")
public class QuotationMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="QUOTATION_NO")
	private int quotationNo;
	
	@Column(name="DATE")
	private Timestamp date;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="COMPANY_ID")
	private Company companyId;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="quotMaster",cascade=CascadeType.PERSIST)
	private List<QuotationTrans> quotTransList=new ArrayList<QuotationTrans>();
	
	@Transient
	private String company;
	
	@Transient
	private java.util.Date dateUtil;
	
	public void addTrans(QuotationTrans trans) {
		trans.setQuotMaster(this);
		quotTransList.add(trans);
    }

	public int getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(int quotationNo) {
		this.quotationNo = quotationNo;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public List<QuotationTrans> getQuotTransList() {
		return quotTransList;
	}

	public void setQuotTransList(List<QuotationTrans> quotTransList) {
		this.quotTransList = quotTransList;
	}

	public String getCompany() {
		company=getCompanyId().getCompName();
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
	
	

}
