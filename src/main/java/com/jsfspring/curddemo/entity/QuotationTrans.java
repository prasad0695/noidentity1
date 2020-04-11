package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="QUOTATION_TRANS")
public class QuotationTrans implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ROW_ID")
	private int rowId;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PRODUCT_ID")
	private ProductDomain productId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUOT_ID")
	private QuotationMaster quotMaster;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="quotTransRowId",cascade=CascadeType.ALL)
    private List<QuotationProductUom> quotUomList=new ArrayList<QuotationProductUom>();
	
	@Transient
	private String item;
	
	public void addTrans(QuotationProductUom trans) {
		quotUomList.add(trans);
		trans.setQuotTransRowId(this);
    }
	
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public ProductDomain getProductId() {
		return productId;
	}

	public void setProductId(ProductDomain productId) {
		this.productId = productId;
	}

	public QuotationMaster getQuotMaster() {
		return quotMaster;
	}

	public void setQuotMaster(QuotationMaster quotMaster) {
		this.quotMaster = quotMaster;
	}

	public List<QuotationProductUom> getQuotUomList() {
		return quotUomList;
	}

	public void setQuotUomList(List<QuotationProductUom> quotUomList) {
		this.quotUomList = quotUomList;
	}

	public String getItem() {
		if(productId!=null)
			item=productId.getItem();
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	
	
	

}
