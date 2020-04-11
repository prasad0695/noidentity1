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
@Table(name="PURCHASE_OUTSTANDING")
public class PurchaseOutstanding implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SUPPLIER_ID")
	private SupplierDomain supplierId;
	
	@Column(name="SALES_AMT")
	private double outStandingAmt;
	
	public double getOutStandingAmt() {
		return outStandingAmt;
	}
	public void setOutStandingAmt(double outStandingAmt) {
		this.outStandingAmt = outStandingAmt;
	}
	public SupplierDomain getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(SupplierDomain supplierId) {
		this.supplierId = supplierId;
	}

}