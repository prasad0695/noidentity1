package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.PurchaseBillMaster;
import com.jsfspring.curddemo.entity.PurchasePaymentsDomain;
import com.jsfspring.curddemo.repositry.PurchasePaymentRepo;
import com.jsfspring.curddemo.utills.SukiException;

@Controller("purchasePaymentMBean")
@SessionScope
public class PurchasePaymentMBean{
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public PurchasePaymentRepo purchasePaymentRepo;
	
	private static final long serialVersionUID = 1L;
	public PurchasePaymentsDomain purchasePayment;
	public List<PurchaseBillMaster> billListForPayment;
	public List<PurchaseBillMaster> selectedBillListForPayment;
	
	String newPurchasePayment="/jsfspring/pages/Payments/newPurchasePayment.xhtml";
	String PurchasePaymentOverview="/jsfspring/pages/Payments/PurchasePayment.xhtml";
	
	public PurchasePaymentMBean() {
		purchasePayment=new PurchasePaymentsDomain();
		billListForPayment=new ArrayList<PurchaseBillMaster>();
		selectedBillListForPayment=new ArrayList<PurchaseBillMaster>();
	}

	public void newPayment() {
		purchasePayment=new PurchasePaymentsDomain();
		billListForPayment=new ArrayList<PurchaseBillMaster>();
		selectedBillListForPayment=new ArrayList<PurchaseBillMaster>();
		try {
			purchasePayment.setPaymentNo(commonObjects.getAutoNumber("paymentNo","PurchasePaymentsDomain"));
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sukiBaseBean.pageRedirect(newPurchasePayment);
	}
	
	public void savePayment(){
			selectedBillListForPayment.parallelStream().forEach(i->{
			if(i.getPaidAmt()==i.getTotalAmount())
			i.setStatus("Paid");
			i.setPaymentNo(purchasePayment);
		});
		purchasePayment.setBillMasterList(selectedBillListForPayment);
		purchasePayment=purchasePaymentRepo.save(purchasePayment);
	}
	public void updateDC(){
			purchasePayment=purchasePaymentRepo.save(purchasePayment);
	}
	public void getSalPaymentObjActionEvent(ActionEvent event) {
		purchasePayment=new PurchasePaymentsDomain();
			purchasePayment=purchasePaymentRepo.findById(sukiBaseBean.actionEvent(event)).get();
			purchasePayment.setEditBoolean(true);
		sukiBaseBean.pageRedirect(newPurchasePayment);
	}
	
	public void getDeleteActionEvent(ActionEvent event) {
			purchasePaymentRepo.deleteById(sukiBaseBean.actionEvent(event));
	}
	public void getAmtFromList(){
		selectedBillListForPayment.parallelStream().forEach(i->{i.setPaidAmt(i.getTotalAmount());});
		purchasePayment.setAmountToPay(selectedBillListForPayment.parallelStream().mapToDouble(i -> i.getTotalAmount()).sum());
		purchasePayment.setTotalAmount(purchasePayment.getAmountToPay());
	}
	public void getTotalAmt(AjaxBehaviorEvent event) {
		purchasePayment.setAmountToPay(selectedBillListForPayment.parallelStream().mapToDouble(i -> i.getPaidAmt()).sum());
		purchasePayment.setTotalAmount(purchasePayment.getAmountToPay());
	}
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(PurchasePaymentOverview);
		sukiBaseBean.t=purchasePayment;
		sukiBaseBean.overviewList();
	}
	public void getPendingPurchaseBill() {
		try {
			billListForPayment=SukiServiceAPI.getInstance().getPendingPurchaseBillMasterList(purchasePayment.getSupId().getSupCode());
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}

	public PurchasePaymentsDomain getPurchasePayment() {
		return purchasePayment;
	}

	public void setPurchasePayment(PurchasePaymentsDomain purchasePayment) {
		this.purchasePayment = purchasePayment;
	}

	public List<PurchaseBillMaster> getBillListForPayment() {
		return billListForPayment;
	}

	public void setBillListForPayment(List<PurchaseBillMaster> billListForPayment) {
		this.billListForPayment = billListForPayment;
	}

	public List<PurchaseBillMaster> getSelectedBillListForPayment() {
		return selectedBillListForPayment;
	}

	public void setSelectedBillListForPayment(List<PurchaseBillMaster> selectedBillListForPayment) {
		this.selectedBillListForPayment = selectedBillListForPayment;
	}

	
}
