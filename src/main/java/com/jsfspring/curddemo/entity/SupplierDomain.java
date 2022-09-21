package com.jsfspring.curddemo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsfspring.curddemo.utills.SukiAppUtil;

@Entity
@Table(name="SUPPLIER")
public class SupplierDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SUP_CODE")
	private int supCode;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="GST_IN")
	private String gstIn;
	
	@Column(name="AREA_1")
	private String area1;
	
	@Column(name="AREA_2")
	private String area2;
	
	@Column(name="AREA_3")
	private String area3;
	
	@Column(name="CITY")
	private String city;
	
	@Column(name="OPENING_BAL")
	private double openingbal;
	
	@Transient
	private double balance;

	@Transient
	private String address;

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

	public String getArea3() {
		return area3;
	}

	public void setArea3(String area3) {
		this.area3 = area3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGstIn() {
		return gstIn;
	}

	public void setGstIn(String gstIn) {
		this.gstIn = gstIn;
	}

	public double getOpeningbal() {
		return openingbal;
	}

	public void setOpeningbal(double openingbal) {
		this.openingbal = openingbal;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getSupCode() {
		return supCode;
	}

	public void setSupCode(int supCode) {
		this.supCode = supCode;
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

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String EmptyString(String string) {
		if(SukiAppUtil.isEmpty(string))
			return "";
		return string+",";
	}
	public String EmptyStringCity(String string) {
		if(SukiAppUtil.isEmpty(string))
			return "";
		return string+".";
	}
	
}
