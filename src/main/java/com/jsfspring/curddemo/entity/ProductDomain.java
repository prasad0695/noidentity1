package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="PRODUCT")
public class ProductDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROD_CODE")
	private int prodCode;
	
	@Column(name="HSN_CODE")
	private int hsnCode;
	
	@Column(name="ITEM")
	private String item;
	
	@Column(name="UOM")
	private String uom;
	
	@Column(name="RATE")
	private double rate;
	
	@Column(name="CGST")
	private double cgst;
	
	@Column(name="SGST")
	private double sgst;
	
	@Column(name="TYPE")
	private String stationOrHouse;
	
	@Column(name="STOCK")
	private double stock;
	
	@Column(name="OPENING_QUANTITY")
	private int openingQty;
	
	@Transient
	private boolean selectAll;
	
	@Transient
	private double gst;
	
	@OneToMany(fetch=FetchType.LAZY,cascade= {CascadeType.ALL},mappedBy="productId")
	private List<ProductUom> prodUomTransList=new ArrayList<ProductUom>();
	
	@Transient
	private List<String> quotUomList;
	
	@Transient
	private List<String> uomList=new ArrayList<String>();
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="product")
	private List<ProductSellPriceDomain> productSellPriceList=new ArrayList<ProductSellPriceDomain>();
	
	public String getproductUom() {
		StringJoiner joiner=new StringJoiner(" / "," "," ");
		List<ProductUom> uomList=getProdUomTransList();
		if(uomList!=null) {
		for(int i=0;i<uomList.size();i++) {
		joiner.add(uomList.get(i).getUomName());
		}
		}
		return joiner.toString();
	}
	public String getproductStock() {
		StringJoiner joiner=new StringJoiner(" / "," "," ");
		List<ProductUom> uomList=getProdUomTransList();
		if(uomList!=null) {
		for(int i=0;i<uomList.size();i++) {
		joiner.add(String.valueOf(uomList.get(i).getStock()));
		}
		}
		return joiner.toString();
	}
	public String getproductRate() {
		StringJoiner joiner=new StringJoiner(" / "," "," ");
		List<ProductUom> uomList=getProdUomTransList();
		if(uomList!=null) {
		for(int i=0;i<uomList.size();i++) {
		joiner.add(String.valueOf(uomList.get(i).getPrice()));
		}
		}
		return joiner.toString();
	}
	
	public void addUomTrans(ProductUom uomTrans) {
		uomTrans.setEditBoolean(true);
		uomTrans.setProductId(this);
		prodUomTransList.add(uomTrans);
	}

	public double getGst() {
		gst=cgst+sgst;
		return gst;
	}

	public void setGst(double gst) {
		
		cgst=gst/2;
		sgst=gst/2;
		this.gst = gst;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public int getProdCode() {
		return prodCode;
	}

	public void setProdCode(int prodCode) {
		this.prodCode = prodCode;
	}

	public int getHsnCode() {
		return hsnCode;
	}

	public void setHsnCode(int hsnCode) {
		this.hsnCode = hsnCode;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getCgst() {
		return cgst;
	}

	public void setCgst(double cgst) {
		this.cgst = cgst;
	}

	public double getSgst() {
		return sgst;
	}

	public void setSgst(double sgst) {
		this.sgst = sgst;
	}

	public String getStationOrHouse() {
		return stationOrHouse;
	}

	public void setStationOrHouse(String stationOrHouse) {
		this.stationOrHouse = stationOrHouse;
	}

	public double getStock() {
		return stock;
	}

	public void setStock(double stock) {
		this.stock = stock;
	}

	public int getOpeningQty() {
		return openingQty;
	}

	public void setOpeningQty(int openingQty) {
		this.openingQty = openingQty;
	}

	public List<ProductUom> getProdUomTransList() {
		return prodUomTransList;
	}

	public void setProdUomTransList(List<ProductUom> prodUomTransList) {
		this.prodUomTransList = prodUomTransList;
	}
	public List<String> getQuotUomList() {
		return quotUomList;
	}
	public void setQuotUomList(List<String> quotUomList) {
		this.quotUomList = quotUomList;
	}
	public List<String> getUomList() {
		uomList = getProdUomTransList().stream().map(i->i.getUomId().getUnitName()).collect(Collectors.toList());
		List<ProductSellPriceDomain> s = getProductSellPriceList();
		if(s.size()>0) {
			quotUomList =new ArrayList<String>();
			ProductUom uom = s.get(s.size()-1).getProductUom();
			UnitMasterDomain u = uom.getUomId();
			quotUomList.add(u.getUnitName());
		}else
		quotUomList = uomList;
		return uomList;
	}
	public void setUomList(List<String> uomList) {
		this.uomList = uomList;
	}
	public List<ProductSellPriceDomain> getProductSellPriceList() {
		productSellPriceList = productSellPriceList.stream().filter(i->i.isEnable()).collect(Collectors.toList());
		return productSellPriceList;
	}
	public void setProductSellPriceList(List<ProductSellPriceDomain> productSellPriceList) {
		this.productSellPriceList = productSellPriceList;
	}
}
