package com.jsfspring.curddemo.mbean;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.SalesPaymentsDomain;
import com.jsfspring.curddemo.repositry.BillMasterRepo;
import com.jsfspring.curddemo.repositry.SalesPaymentRepo;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;


@Controller("salesPaymentMBean")
@SessionScope
public class SalesPaymentMBean{
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public SalesPaymentRepo salesPaymentRepo;
	
	@Autowired
	public BillMasterRepo billMasterRepo;
	
	private static final long serialVersionUID = 1L;
	public SalesPaymentsDomain salesPayment;
	public List<BillMasterDomain> billListForPayment;
	public List<BillMasterDomain> selectedBillListForPayment;
	
	String newsalesPayment="/jsfspring/pages/Payments/newSalesPayments.xhtml";
	String salesPaymentOverview="/jsfspring/pages/Payments/salesPayments.xhtml";
	
	public SalesPaymentMBean() {
		salesPayment=new SalesPaymentsDomain();
		billListForPayment=new ArrayList<BillMasterDomain>();
		selectedBillListForPayment=new ArrayList<BillMasterDomain>();
	}

	public void newPayment() {
		resetPayment();
		sukiBaseBean.pageRedirect(newsalesPayment);
	}
	
	public void savePayment(){
			if(SukiAppUtil.isEmpty(salesPayment.getPaymentmode())) {
				sukiBaseBean.errorMessage("Payment", "Select any Payment mode");
				return;
			}
			if(!salesPayment.isEditBoolean()){
			selectedBillListForPayment.parallelStream().forEach(i->{
			if(i.getPaidAmt()==i.getTotalAmount())
			i.setStatus("Paid");
			i.setPaymentNo(salesPayment);
//			i.setPaidAmt(i.getTotalAmount());
			});
				salesPayment.setBillMasterList(selectedBillListForPayment);
				sukiBaseBean.addMessage("Payment", "Payment saved succesfully");
			}else{
				 sukiBaseBean.addMessage("Payment", "Payment Updated succesfully");
			}
			    salesPayment=salesPaymentRepo.save(salesPayment);
			    salesPayment.setEditBoolean(true);
	}
	
//	public void updateDC(){
//		try {
//			salesPayment=CommonAPI.getInstance().update(salesPayment);
//		} catch (SukiException e) {
//			e.printStackTrace();
//		}
//	}
	public void getSalPaymentObjActionEvent(ActionEvent event) {
		salesPayment=new SalesPaymentsDomain();
		salesPayment=salesPaymentRepo.findById(sukiBaseBean.actionEvent(event)).get();
		salesPayment.setEditBoolean(true);
		sukiBaseBean.pageRedirect(newsalesPayment);
	}
	
	public void getDeleteActionEvent(ActionEvent event) {
			salesPayment=salesPaymentRepo.findById(sukiBaseBean.actionEvent(event)).get();
			deletePayment();
	}
	public void deletePayment() {
		salesPaymentRepo.updateBeforeDelete(salesPayment.getPaymentNo());
		salesPaymentRepo.delete(salesPayment);
		sukiBaseBean.addMessage("Sales Payment", "Deleted Successfully");
	}

	public void resetPayment() {
		salesPayment=new SalesPaymentsDomain();
		billListForPayment=new ArrayList<BillMasterDomain>();
		selectedBillListForPayment=new ArrayList<BillMasterDomain>();
		try {
			salesPayment.setPaymentNo(commonObjects.getAutoNumber("paymentNo","SalesPaymentsDomain"));
			salesPayment.setEditBoolean(false);
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getAmtFromList(){
		selectedBillListForPayment.parallelStream().forEach(i->{i.setPaidAmt(i.getBalanceAmt());});
		salesPayment.setAmountToPay(selectedBillListForPayment.parallelStream().mapToDouble(i -> i.getPaidAmt()).sum());
		salesPayment.setTotalAmount(salesPayment.getAmountToPay());
	}
	public void getTotalAmt(AjaxBehaviorEvent event) {
		salesPayment.setAmountToPay(selectedBillListForPayment.parallelStream().mapToDouble(i -> i.getPaidAmt()).sum());
		salesPayment.setTotalAmount(salesPayment.getAmountToPay());
	}
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(salesPaymentOverview);
		sukiBaseBean.t=salesPayment;
		sukiBaseBean.overviewList();
	}
	public void getPendingSalesBill() {
			billListForPayment=billMasterRepo.findInvByPendingStatus(salesPayment.getCompanyId().getCompId());
			billListForPayment.parallelStream().forEach(i->{i.setBalanceAmt(i.getTotalAmount()-i.getPaidAmt());i.setPaidAmt(0);});
	}
	public SalesPaymentsDomain getSalesPayment() {
		return salesPayment;
	}

	public void setSalesPayment(SalesPaymentsDomain salesPayment) {
		this.salesPayment = salesPayment;
	}
	
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}

	public List<BillMasterDomain> getBillListForPayment() {
		return billListForPayment;
	}

	public void setBillListForPayment(List<BillMasterDomain> billListForPayment) {
		this.billListForPayment = billListForPayment;
	}

	public List<BillMasterDomain> getSelectedBillListForPayment() {
		return selectedBillListForPayment;
	}

	public void setSelectedBillListForPayment(List<BillMasterDomain> selectedBillListForPayment) {
		this.selectedBillListForPayment = selectedBillListForPayment;
	}
}
