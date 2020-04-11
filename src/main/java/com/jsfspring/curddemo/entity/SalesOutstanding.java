package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SALES_OUTSTANDING")
public class SalesOutstanding implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COMPANY_ID")
	private Company companyId;
	
	@Column(name="SALES_AMT")
	private double outStandingAmt;
	
	public Company getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}
	public double getOutStandingAmt() {
		return outStandingAmt;
	}
	public void setOutStandingAmt(double outStandingAmt) {
		this.outStandingAmt = outStandingAmt;
	}
	
	

}
