package com.jsfspring.curddemo.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsfspring.curddemo.utills.SukiAppUtil;


@Entity
@Table(name = "COMPANY")
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMPANY_ID")
	private int compId;

	@Column(name = "COMPANY_NAME")
	private String compName;
	
	@Column(name = "MAIL_ID")
	private String mailId;

	@Column(name = "GST")
	private String gst;
	
	@Column(name = "AREA_1")
	private String area1;
	
	@Column(name = "AREA_2")
	private String area2;
	
	@Column(name = "AREA_3")
	private String area3;
	
	@Column(name = "CITY")
	private String city;
	
	@Column(name = "OPENING_BALANCE")
	private double openingBalance;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="compId",cascade=CascadeType.ALL)
    private List<CompanyCategoryList> categoryList=new ArrayList<CompanyCategoryList>();
	
	@Transient
	private String address;
	
	@Transient
	private double balance;
	
	@Transient
	private double billAmt;
	
	@Transient
	private double paymentAmt;
	
	@Transient
	private double ledgerBalance;
	
	@Transient
	private List<String> categoryListString = new ArrayList<String>();;
	
	@Column(name = "SPLIT")
	private String split;
	

	public int getCompId() {
		return compId;
	}

	public void setCompId(int compId) {
		this.compId = compId;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getArea1() {
		return area1;
	}

	public void setArea1(String area1) {
		this.area1 = area1;
	}

	public String getArea2() {
		return area2;
	}

	public void setArea2(String area2) {
		this.area2 = area2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		StringBuffer string=new StringBuffer();
		string.append(EmptyString(area1));
		string.append(EmptyString(area2));
		string.append(EmptyString(area3));
		string.append(EmptyStringCity(city));
		address=string.toString();
		return address;
	}

	public String EmptyString(String string) {
		if(SukiAppUtil.isEmpty(string))
			return "";
		return string;
	}
	public String EmptyStringCity(String string) {
		if(SukiAppUtil.isEmpty(string))
			return "";
		return string+".";
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea3() {
		return area3;
	}

	public void setArea3(String area3) {
		this.area3 = area3;
	}

	public String getSplit() {
		return split;
	}

	public void setSplit(String split) {
		this.split = split;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(double billAmt) {
		this.billAmt = billAmt;
	}

	public double getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(double paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public double getLedgerBalance() {
		return ledgerBalance;
	}

	
	public void setLedgerBalance(double ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}

	public double getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(double openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	
	public List<CompanyCategoryList> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CompanyCategoryList> categoryList) {
		this.categoryList = categoryList;
	}

	public List<String> getCategoryListString() {
//		if(categoryList!=null && categoryList.size()>0)
//		categoryListString = categoryList.stream().map(i->i.getCategoryId().getCategory()).collect(Collectors.toList());
		return categoryListString;
	}

	public void setCategoryListString(List<String> categoryListString) {
		System.out.println(categoryListString.size());
		this.categoryListString = categoryListString;
	}

}
