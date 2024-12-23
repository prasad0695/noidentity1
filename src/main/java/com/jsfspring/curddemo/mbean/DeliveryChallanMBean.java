package com.jsfspring.curddemo.mbean;


import java.io.File;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;
import com.jsfspring.curddemo.entity.DeliveryChalanDomain;
import com.jsfspring.curddemo.entity.DeliveryChalanMaster;
import com.jsfspring.curddemo.repositry.DcMasterRepo;
import com.jsfspring.curddemo.repositry.DcTransRepo;
import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

@Controller("deliveryChallanMBean")
@SessionScope
public class DeliveryChallanMBean {
	
	@Autowired
	public DcMasterRepo dcMasterRepo;
	
	@Autowired
	public DcTransRepo dcTransRepo;
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public PdfDocuments pdfDocuments;

	public DeliveryChalanMaster deliveryMaster;
	
	String addDc="/jsfspring/pages/dc/newDc.xhtml";
	String dcOverview="/jsfspring/pages/dc/DeliveryChalan.xhtml";
	public boolean dcEdit;
	
	public DeliveryChallanMBean(){
		deliveryMaster=new DeliveryChalanMaster();
		
	}
	
	
	public void newDeliveryChalan() {
		deliveryMaster=new DeliveryChalanMaster();
		try {
			deliveryMaster.setDeliveryNo(commonObjects.getAutoNumber("deliveryNo","DeliveryChalanMaster"));
			deliveryMaster.setDateUtil(SukiAppUtil.getCurrentUtilDate());
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sukiBaseBean.pageRedirect(addDc);
	}
	
	public void saveDC(){
			// deliveryMaster.setDate(SukiAppUtil.getTimeStampFromUtilDate(deliveryMaster.getDateUtil()));
			boolean flag=false;
			if (sukiBaseBean.validateString(deliveryMaster.getCompany(), "Company Name"))
				flag = true;
			if (sukiBaseBean.validateList(deliveryMaster.getDcTransList().size(), "Enter Delivery Items"))
				flag = true;
			if (flag)
				return;
				deliveryMaster=dcMasterRepo.save(deliveryMaster);
				sukiBaseBean.addMessage("Delivery Chalan", "Saved Successfullly");
				deliveryMaster.setEditBoolean(true);
				deliveryMaster.getDcTransList().forEach(i->i.setEditBoolean(false));
	}
	
	public void updateDC(){
			deliveryMaster.setDate(SukiAppUtil.getTimeStampFromUtilDate(deliveryMaster.getDateUtil()));
			deliveryMaster=dcMasterRepo.save(deliveryMaster);
			sukiBaseBean.addMessage("Delivery Chalan", "Updated Successfullly");
			deliveryMaster.setEditBoolean(true);
			deliveryMaster.getDcTransList().forEach(i->i.setEditBoolean(false));
	}
	public void getDCObjActionEvent(ActionEvent event) {
		deliveryMaster=new DeliveryChalanMaster();
		deliveryMaster=dcMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
		deliveryMaster.setEditBoolean(true);
		sukiBaseBean.pageRedirect(addDc);
	}
	public void printDc(ActionEvent event) {
		try {
			deliveryMaster=dcMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			dcPdfprint(deliveryMaster);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void print() {
		dcPdfprint(deliveryMaster);
	}
	public void dcPdfprint(DeliveryChalanMaster deliveryMaster) {
		try {
		pdfDocuments.createDc(deliveryMaster);
			String desktopPath = System.getProperty("user.home");
			String desktopPathModified = desktopPath.replace("\\","/");
//		String desktopPathModified = "/home";
		String file=desktopPathModified+"/INVOICE/Dc/"+deliveryMaster.getDeliveryNo()+".pdf";
//		String file=SukiAppConstants.DC_FOLDER+deliveryMaster.getDeliveryNo()+".pdf";
		File pdfFile = new File(file);
//		if (pdfFile.exists()) {
//			Process p = Runtime
//			   .getRuntime()
//			   .exec("rundll32 url.dll,FileProtocolHandler "+file);
//			p.waitFor();
//		} else {
//			System.out.println("File is not exists");
//		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getDelete() {
		if(deliveryMaster.getBillNo()!= null) {
			sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Delivery Chalan", "can't  delete used for Bill"+deliveryMaster.getBillNo().getBillNo()));
			return;
		}
			dcMasterRepo.delete(deliveryMaster);
			newDeliveryChalan();
		sukiBaseBean.addMessage("Delivery Chalan", "Deleted Successfullly");
	}
	public void getDeleteActionEvent(ActionEvent event) {
			deliveryMaster=new DeliveryChalanMaster();
			deliveryMaster=dcMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			getDelete();
	}
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(dcOverview);
		sukiBaseBean.t=deliveryMaster;
		sukiBaseBean.overviewList();
	}
	
	public String addRowDC(){
		DeliveryChalanDomain temp=new DeliveryChalanDomain();
		temp.setEditBoolean(true);
		deliveryMaster.addTrans(temp);
		return "";
	}
	public void dcItemEdit(ActionEvent event) {
		int index=sukiBaseBean.actionEvent(event);
		DeliveryChalanDomain dcTrans=deliveryMaster.getDcTransList().get(index);
				dcTrans.setEditBoolean(true);
				dcTrans.setNosForEdit(dcTrans.getNos());
				dcTrans.setProductForEdit(dcTrans.getItems());
				dcTrans.setUomForEdit(dcTrans.getUom());
				deliveryMaster.getDcTransList().set(index, dcTrans);
	}
	public void saveDcItem(ActionEvent event) {
			dcTransRepo.save(deliveryMaster.getDcTransList().get(sukiBaseBean.actionEvent(event)));
			deliveryMaster.getDcTransList().get(sukiBaseBean.actionEvent(event)).setEditBoolean(false);
	}
	public void deleteDcItem(ActionEvent event){
		try{
			int index=sukiBaseBean.actionEvent(event);
			DeliveryChalanDomain dcTrans=deliveryMaster.getDcTransList().get(index);
			if(dcTrans.getRowId()>0)
				dcTransRepo.delete(dcTrans);
			    deliveryMaster.getDcTransList().remove(index);
		}catch(Exception e){
		}
	}
	
	public void dcReset(){
		deliveryMaster=new DeliveryChalanMaster();
		try {
			deliveryMaster.setDeliveryNo(commonObjects.getAutoNumber("deliveryNo", "DeliveryChalanMaster"));
			deliveryMaster.setDate(SukiAppUtil.getCurrentDateAndTime());
			dcEdit=false;
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void newDc(){
		dcReset();
		sukiBaseBean.pageRedirect(addDc);
	}
	
	public boolean dcNoValidation(){
	if(deliveryMaster.getDeliveryNo()>0){
			if(!dcEdit){
			DeliveryChalanMaster dcMasterList=dcMasterRepo.findById(deliveryMaster.getDeliveryNo()).get();
			if(dcMasterList!=null){
				sukiBaseBean.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Delivery chalan","This is duplicate Dc No."));
				return true;
			}
			}
	}else{
		sukiBaseBean.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"Delivery chalan","Dc No. not to be Empty"));
		return true;
	}
	return false;
	}
	
		public DeliveryChalanMaster getDeliveryMaster() {
		return deliveryMaster;
	}


	public void setDeliveryMaster(DeliveryChalanMaster deliveryMaster) {
		this.deliveryMaster = deliveryMaster;
	}


		public LazyDataModel getModel() {
			return sukiBaseBean.model;
			}
	}