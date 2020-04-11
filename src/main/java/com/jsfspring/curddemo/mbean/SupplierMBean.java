package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.SupplierDomain;
import com.jsfspring.curddemo.repositry.CompanyRepo;
import com.jsfspring.curddemo.repositry.SupplierRepo;
import com.jsfspring.curddemo.utills.SukiException;

@Controller("supplierMBean")
@SessionScope
public class SupplierMBean{
	
	@Autowired
	public SupplierRepo supplierRepo;
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	public SupplierDomain supplier;
	public List<SupplierDomain> supplierList;
	public List<SupplierDomain> filteredSupplierList;
	
	String addSupplier="/jsfspring/pages/Supplier/addSupplier.xhtml";
	String supplierOverview="/jsfspring/pages/Supplier/supplier.xhtml";
	
	
	public SupplierMBean() {
		supplier=new SupplierDomain();
		supplierList=new ArrayList<SupplierDomain>();
	}
	public void newSupplier() {
		reset();
		sukiBaseBean.pageRedirect(addSupplier);
	}
	public void reset() {
		supplier=new SupplierDomain();
	}
	public void saveSupplier(){
			boolean flag=false;
			if(sukiBaseBean.validateString(supplier.getName(), "Supplier Name"))
				flag=true;
			if(flag)
				return;
			if(supplier.getSupCode()>0) {
				sukiBaseBean.addMessage("Supplier", "Update Successfullly");
			}else {
			    sukiBaseBean.addMessage("Supplier", "Saved Successfullly");
			}
			supplier=supplierRepo.save(supplier);
	}
	
	public void getSupObjActionEvent(ActionEvent event) {
		supplier=new SupplierDomain();
		supplier=supplierRepo.findById(sukiBaseBean.actionEvent(event)).get();
		sukiBaseBean.pageRedirect(addSupplier);
	}
	public void delete() {
			supplierRepo.delete(supplier);
			sukiBaseBean.addMessage("Supplier", "Deleted Successfullly");
			sukiBaseBean.dialogHide();
			supplier=new SupplierDomain();
	}
	public void getDeleteActionEvent(ActionEvent event) {
			supplierRepo.deleteById(sukiBaseBean.actionEvent(event));
	}
	
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(supplierOverview);
		sukiBaseBean.t=supplier;
		sukiBaseBean.overviewList();
	}
	
	public SupplierDomain getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierDomain supplier) {
		this.supplier = supplier;
	}
	public List<SupplierDomain> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<SupplierDomain> supplierList) {
		this.supplierList = supplierList;
	}
	
	public LazyDataModel getModel() {
	return sukiBaseBean.model;
	}

}
