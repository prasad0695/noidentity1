package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.InwardMaster;
import com.jsfspring.curddemo.entity.InwardTrans;
import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.PurchaseOrderMaster;
import com.jsfspring.curddemo.entity.PurchaseOrderTrans;
import com.jsfspring.curddemo.repositry.InwardMasterRepo;
import com.jsfspring.curddemo.repositry.InwardTransRepo;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

@Controller("inwardMBean")
@SessionScope
public class InwardMBean{
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public InwardMasterRepo inwardMasterRepo;
	
	@Autowired
	public InwardTransRepo inwardTransRepo;

	InwardMaster inwardMaster;
	List<PurchaseOrderMaster> poMasterList;
	List<PurchaseOrderMaster> selectedPoMasterList;
	List<PurchaseOrderTrans> poTransList;
	List<PurchaseOrderTrans> selectedPoTransList;
	
	public InwardMBean(){
	inwardMaster=new InwardMaster();
//	poMasterList=new ArrayList<PurchaseOrderMaster>();
//	selectedPoMasterList=new ArrayList<PurchaseOrderMaster>();
	poTransList=new ArrayList<PurchaseOrderTrans>();
	selectedPoTransList=new ArrayList<PurchaseOrderTrans>();
	}
	
	String inward="/jsfspring/pages/Inward/InwardOverview.xhtml";
	String newInward="/jsfspring/pages/Inward/newInward.xhtml";
	
	public void newInward() {
		inwardMaster=new InwardMaster();
		try {
			inwardMaster.setInwardNo(commonObjects.getAutoNumber("inwardNo","InwardMaster"));
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inwardMaster.setDate(SukiAppUtil.getCurrentDateAndTime());
		sukiBaseBean.pageRedirect(newInward);
	}
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(inward);
		sukiBaseBean.t=inwardMaster;
		sukiBaseBean.overviewList();
	}
//	public void getPendingList() {
//		try {
//			poMasterList=SukiServiceAPI.getInstance().getPoMasterList(inwardMaster.getSupId().getSupCode());
//		} catch (SukiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public String addTrans(){
		InwardTrans inwardTrans=new InwardTrans();
		inwardTrans.setEditBoolean(true);
		inwardTrans.setInwardNo(inwardMaster);
		inwardMaster.getInwardTransList().add(inwardTrans);
		// inwardMaster.addTrans(inwardTrans);
		return "";
	}
	public void inwardTransDetailsFromProduct(SelectEvent event) {
		int i=sukiBaseBean.selectEvent(event);
		ProductDomain product=new ProductDomain();
		product=inwardMaster.getInwardTransList().get(i).getProductId();
		/*inwardMaster.getInwardTransList().get(i).setRate(product.getRate());
		inwardMaster.getInwardTransList().get(i).setGst(product.getCgst());
		inwardMaster.getInwardTransList().get(i).setHsn(product.getHsnCode());
		inwardMaster.getInwardTransList().get(i).setStationOrHouse(product.getStationOrHouse());*/
	}
	public void getPendingPoTransList(SelectEvent event) {
		poTransList=new ArrayList<PurchaseOrderTrans>();
		selectedPoMasterList.parallelStream().forEach(i->{poTransList.addAll(i.getPurchaseOrderTransList());});
	}
	public void receivingAll(){
		selectedPoTransList.parallelStream().forEach(i->{i.setReceived(i.getNos());});
	}
	public void addToInwardTrans(){
			InwardTrans trans=new InwardTrans();
			trans.setEditBoolean(true);
			inwardMaster.addTrans(trans);
	}
	public void saveInward() {
			inwardMaster=inwardMasterRepo.save(inwardMaster);
			if(inwardMaster.isEditBoolean())
				sukiBaseBean.addMessage("Inward", "Updated Successfully");
			else
				sukiBaseBean.addMessage("Inward", "Saved Successfully");
	}
	public void saveInwItem(ActionEvent event) {
			inwardTransRepo.save(inwardMaster.getInwardTransList().get(sukiBaseBean.actionEvent(event)));
			inwardMaster.getInwardTransList().get(sukiBaseBean.actionEvent(event)).setEditBoolean(false);
			sukiBaseBean.addMessage("Inward", "Item Saved Successfully");
	}
	public void getReceivedValidation(double received,double ordered) {
		if(received<=0)
			sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Inward", "Enter value greater than 0"));
		else if(received<ordered)
			sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Inward", "Enter value less than ordered"));
	}
	public void inwItemEdit(ActionEvent event) {
		int index=sukiBaseBean.actionEvent(event);
		inwardMaster.getInwardTransList().get(index).setEditBoolean(true);;
//		trans.setEditBoolean(true);
//		trans.setNosForEdit(trans.getReceived());
//		trans.setProductForEdit(trans.getProductId());
//		trans.setUomForEdit(trans.getUom());
//		inwardMaster.getInwardTransList().set(index, trans);
	}
	public void deleteInwardItem(ActionEvent event) {
		int i=sukiBaseBean.actionEvent(event);
		InwardTrans trans= inwardMaster.getInwardTransList().get(i);
		if(trans.getRowId()>0) {
			inwardTransRepo.delete(trans);
			sukiBaseBean.addMessage("Inward", "Deleted Successfully");
		}
		inwardMaster.getInwardTransList().remove(i);
	}
	public void getInwObjActionEvent(ActionEvent event) {
		inwardMaster=new InwardMaster();
			inwardMaster=inwardMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			inwardMaster.setEditBoolean(true);
		sukiBaseBean.pageRedirect(newInward);
	}
	public void getDeleteActionEvent(ActionEvent event) {
			inwardMasterRepo.deleteById(sukiBaseBean.actionEvent(event));
			sukiBaseBean.addMessage("Inward", "Deleted Successfully");
	}
	public void deleteInward() {
		inwardMasterRepo.delete(inwardMaster);
		sukiBaseBean.addMessage("Inward", "Deleted Successfully");
	}
	public InwardMaster getInwardMaster() {
		return inwardMaster;
	}

	public void setInwardMaster(InwardMaster inwardMaster) {
		this.inwardMaster = inwardMaster;
	}

	public List<PurchaseOrderMaster> getPoMasterList() {
		return poMasterList;
	}

	public void setPoMasterList(List<PurchaseOrderMaster> poMasterList) {
		this.poMasterList = poMasterList;
	}

	public List<PurchaseOrderMaster> getSelectedPoMasterList() {
		return selectedPoMasterList;
	}

	public void setSelectedPoMasterList(List<PurchaseOrderMaster> selectedPoMasterList) {
		this.selectedPoMasterList = selectedPoMasterList;
	}

	public List<PurchaseOrderTrans> getSelectedPoTransList() {
		return selectedPoTransList;
	}

	public void setSelectedPoTransList(List<PurchaseOrderTrans> selectedPoTransList) {
		this.selectedPoTransList = selectedPoTransList;
	}

	public void setPoTransList(List<PurchaseOrderTrans> poTransList) {
		this.poTransList = poTransList;
	}

	public List<PurchaseOrderTrans> getPoTransList() {
		return poTransList;
	}
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
		}
}
