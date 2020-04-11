package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.BillTransDomain;
import com.jsfspring.curddemo.entity.InwardMaster;
import com.jsfspring.curddemo.entity.InwardTrans;
import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.ProductUom;
import com.jsfspring.curddemo.entity.PurchaseBillMaster;
import com.jsfspring.curddemo.entity.PurchaseBillTrans;
import com.jsfspring.curddemo.entity.PurchaseOrderMaster;
import com.jsfspring.curddemo.repositry.InwardMasterRepo;
import com.jsfspring.curddemo.repositry.PurchaseBillMasterRepo;
import com.jsfspring.curddemo.repositry.PurchaseBillTransRepo;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

@Controller("purchaseInvoiceMBean")
@SessionScope
public class PurchaseInvoiceMBean{

	/**
	 * 
	 */
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public PurchaseBillMasterRepo purchaseBillMasterRepo;
	
	@Autowired
	public PurchaseBillTransRepo purchaseBillTransRepo;
	
	@Autowired
	public InwardMasterRepo inwardMasterRepo;
	
	public PurchaseBillMaster  purchaseBillMaster;
	public List<InwardMaster> inwardMasterList;
	public List<InwardMaster> selectedInwardMasterList;
	public List<PurchaseOrderMaster> poMasterList;
	public List<PurchaseOrderMaster> selectedPoMasterList;
	
	
	String purchaseInvoice="/jsfspring/pages/PurchaseInvoice/PurchaseInvoiceOverview.xhtml";
	String newPurchaseinvoice="/jsfspring/pages/PurchaseInvoice/newPurchaseInvoice.xhtml";
	
	public PurchaseInvoiceMBean(){
		purchaseBillMaster=new PurchaseBillMaster();
		inwardMasterList=new ArrayList<InwardMaster>();
		selectedInwardMasterList=new ArrayList<InwardMaster>();
		poMasterList=new ArrayList<PurchaseOrderMaster>();
		selectedPoMasterList=new ArrayList<PurchaseOrderMaster>();
	}
	public PurchaseBillMaster getInwardMasterFromPurchaseBill(PurchaseBillMaster billMaster) {
		billMaster.setInwardMaster(new InwardMaster(billMaster));
		return billMaster;
	}
	
	public void newInvoice() {
		purchaseBillMaster=new PurchaseBillMaster();
		try {
			purchaseBillMaster.setRowId(commonObjects.getAutoNumber("rowId","PurchaseBillMaster"));
		} catch (SukiException e) {
			e.printStackTrace();
		}
		sukiBaseBean.pageRedirect(newPurchaseinvoice);
	}
	
	public void saveInvoice(){
			boolean flag=false;
		if (sukiBaseBean.validateString(purchaseBillMaster.getSupplier(), "Supplier Name"))
			flag = true;
		if (sukiBaseBean.validateString(purchaseBillMaster.getBillNo(), "Bill No"))
			flag = true;
		if(sukiBaseBean.validateList(purchaseBillMaster.getPurchaseBillTransList().size(),"Purchase Invoice")) {
			flag = true;
		}
		if (flag)
			return;
				purchaseBillMaster=purchaseBillMasterRepo.save(purchaseBillMaster);
				sukiBaseBean.addMessage("Invoice", "Invoice saved successfully");
	}
	public void updateInvoice(){
				purchaseBillMaster=purchaseBillMasterRepo.save(purchaseBillMaster);
				sukiBaseBean.addMessage("Invoice", "Invoice updated successfully");
	}
	
	public void getInvObjActionEvent(ActionEvent event) {
		purchaseBillMaster=new PurchaseBillMaster();
			purchaseBillMaster=purchaseBillMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			purchaseBillMaster.setEditBoolean(true);
		sukiBaseBean.pageRedirect(newPurchaseinvoice);
	}
	
	public void getDeleteActionEvent(ActionEvent event) {
			purchaseBillMasterRepo.deleteById(sukiBaseBean.actionEvent(event));
	}
	
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(purchaseInvoice);
		sukiBaseBean.t=purchaseBillMaster;
		sukiBaseBean.overviewList();
	}
	
	public String getPOList()throws Exception{
//			if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("PO"))
//			poMasterList=SukiServiceAPI.getInstance().getPoMasterList(purchaseBillMaster.getSupId().getSupCode());
			if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("DC"))
			inwardMasterList=inwardMasterRepo.findInwDcByPendingStatus(purchaseBillMaster.getSupId().getSupCode());
		return "";
	}
	public void getBillTranslistForInw() {
		//check poTrans Status
		if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("PO")) {
			selectedPoMasterList.parallelStream().forEach(i->{
				i.getPurchaseOrderTransList().parallelStream().forEach(j->{
					PurchaseBillTrans trans=new PurchaseBillTrans();
					trans.setProductId(j.getItems());
					trans.setUom(j.getUom());
					trans.setQty(j.getReceived());
					trans.setGst(trans.getProductId().getCgst()+trans.getProductId().getSgst());
					trans.setEditBoolean(true);
					purchaseBillMaster.addTrans(trans);
				});
			});
		}else if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("DC")){
			selectedInwardMasterList.parallelStream().forEach(i->{
				i.getInwardTransList().parallelStream().forEach(j->{
					PurchaseBillTrans trans=new PurchaseBillTrans();
					trans.setProductId(j.getProductId());
					trans.setUom(j.getUom());
					trans.setQty(j.getReceived());
					trans.setGst(trans.getProductId().getCgst()+trans.getProductId().getSgst());
					trans.setEditBoolean(true);
					purchaseBillMaster.addTrans(trans);
				});
			});
		}
	}
//	public String getDcListAction(){
//		if(selectedDeliveryMasterList1.size()>0){
//		dcNoAndName=selectedDeliveryMasterList1.get(0).getDeliveryNo()+" - "+selectedDeliveryMasterList1.get(0).getCompany();
//		deliveryChalanShowList=selectedDeliveryMasterList1.get(0).getDcTransList();
//		sukiBaseBean.dialogShow("dlg2");
//		}
//	return "";
//	}
	
//	public String billSelect(){
//		try{
//		//StringJoiner joiner = new StringJoiner(",", PREFIX, SUFFIX);
//		selectedDcMasterList.parallelStream().forEach(i->{i.getDcTransList().forEach(j->{
//			//joiner.add(String.valueOf(i.getDeliveryNo());
//			BillTransDomain newBill=new BillTransDomain();
//			ProductDomain product=j.getItems();
//			newBill.setProductId(product);
//			newBill.setQty(j.getNos());
//		    newBill.setRate(product.getRate());
//		    newBill.setGst(product.getCgst());
//		    newBill.setHsn(product.getHsnCode());
//		    newBill.setUom(j.getUom());
//		    newBill.setStationOrHouse(product.getStationOrHouse());
//		    if(j.getUom()!=null) {
//		    newBill.setAmount(j.getUom().getPrice()*newBill.getQty());
//		    newBill.setGstAmt((product.getCgst()*j.getUom().getPrice())/100);
//		    }else {
//		    newBill.setAmount(product.getRate()*newBill.getQty()); 	
//		    newBill.setGstAmt((product.getCgst()*product.getRate())/100);
//		    }
//		    newBill.setTotalAmount(newBill.getAmount()+newBill.getGstAmt());
//		    purchaseBillMaster.setTotalWithoutTax(purchaseBillMaster.getTotalWithoutTax()+newBill.getAmount());
//		    purchaseBillMaster.setTotalAmount(SukiAppUtil.roundedOff(purchaseBillMaster.getTotalAmount()+newBill.getTotalAmount()));
//		    System.out.println(newBill.getAmount()+" "+newBill.getGstAmt()+" "+purchaseBillMaster.getTotalAmount());
//		    purchaseBillMaster.addTrans(newBill);
//		});});
//		Map<Double,Double> map=purchaseBillMaster.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getGst, Collectors.summingDouble(BillTransDomain::getGstAmt)));
//			System.out.println("VALUE---1");
//			purchaseBillMaster.setGstValue(map);
//		//purchaseBillMaster.setDcNos(joiner);
//			System.out.println(map);
//		purchaseBillMaster.setAmountString(SukiAppUtil.getNumericWords(purchaseBillMaster.getTotalAmount()));
//		}catch (Exception e) {
//		}
//		return "";
//		}

	public void billItemRateEdit(AjaxBehaviorEvent event) throws Exception {
		int i = sukiBaseBean.ajaxEvent(event);
		List<PurchaseBillTrans> billTransListEdit = purchaseBillMaster.getPurchaseBillTransList();
		billTransListEdit.get(i).setAmount(billTransListEdit.get(i).getRate() * billTransListEdit.get(i).getQty());
		billTransListEdit.get(i).setGstAmt((billTransListEdit.get(i).getGst() * billTransListEdit.get(i).getRate())/100);
		billTransListEdit.get(i).setTotalAmount(billTransListEdit.get(i).getAmount()
				+ billTransListEdit.get(i).getGstAmt());
		purchaseBillMaster.setTotalWithoutTax(billTransListEdit.parallelStream().mapToDouble(j -> j.getAmount()).sum());
		purchaseBillMaster.setTotalAmount(
				SukiAppUtil.roundedOff(billTransListEdit.parallelStream().mapToDouble(j -> j.getTotalAmount()).sum()));
		purchaseBillMaster.setAmountString(SukiAppUtil.getNumericWords(purchaseBillMaster.getTotalAmount()));
		purchaseBillMaster.setPurchaseBillTransList(billTransListEdit);
		Map<Double,Double> map=purchaseBillMaster.getPurchaseBillTransList().parallelStream().collect(Collectors.groupingBy(PurchaseBillTrans::getGst, Collectors.summingDouble(PurchaseBillTrans::getGstAmt)));
		purchaseBillMaster.setGstValue(map);
	}
	public String addRowBillTrans(){
		PurchaseBillTrans billTrans=new PurchaseBillTrans();
		billTrans.setEditBoolean(true);
		purchaseBillMaster.addTrans(billTrans);
		return "";
	}
	public void billTransDetailsFromProduct(SelectEvent event) {
		int i=sukiBaseBean.selectEvent(event);
		ProductDomain product=new ProductDomain();
		product=purchaseBillMaster.getPurchaseBillTransList().get(i).getProductId();
		purchaseBillMaster.getPurchaseBillTransList().get(i).setGst(product.getCgst()+product.getSgst());
	}
	public void billTransDetailsFromUom(SelectEvent event) {
		int i=sukiBaseBean.selectEvent(event);
		ProductUom productUom=new ProductUom();
		productUom=purchaseBillMaster.getPurchaseBillTransList().get(i).getUom();
		PurchaseBillTrans billTrans=purchaseBillMaster.getPurchaseBillTransList().get(i);
		billTrans.setRate(productUom.getPrice());
		billTrans.setAmount(billTrans.getQty()*billTrans.getRate());
		purchaseBillMaster.getPurchaseBillTransList().set(i, billTrans);
	}
	
	public void deleteBillItem(ActionEvent event) {
		int i=sukiBaseBean.actionEvent(event);
		PurchaseBillTrans trans= purchaseBillMaster.getPurchaseBillTransList().get(i);
		if(trans.getRowId()>0) {
			   purchaseBillTransRepo.delete(trans);
		}
		purchaseBillMaster.getPurchaseBillTransList().remove(i);
	}
	
	public void saveBill(){
		try {
			if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("Direct")) {
				purchaseBillMaster=getInwardMasterFromPurchaseBill(purchaseBillMaster);
				purchaseBillMaster.getInwardMaster().setInwardNo(commonObjects.getAutoNumber("inwardNo","InwardMaster"));
			}else {
				purchaseBillMaster.setInwardMaster(null);
			}
			purchaseBillMaster=purchaseBillMasterRepo.save(purchaseBillMaster);
			}catch (Exception e) {
			}
		}
				
	public void saveInvItem(ActionEvent event) {
			PurchaseBillTrans billTrans=purchaseBillMaster.getPurchaseBillTransList().get(sukiBaseBean.actionEvent(event));
			if(billTrans.getRowId()>0) {
				billTrans.getInwardTransForBillUpdate(billTrans.getInwardTransNo(),billTrans);
			}else {
				billTrans.setInwardTransNo(new InwardTrans(billTrans));
			}
			purchaseBillTransRepo.save(billTrans);
			purchaseBillMaster.getPurchaseBillTransList().get(sukiBaseBean.actionEvent(event)).setEditBoolean(false);
	}
	
	public void invItemEdit(ActionEvent event) {
		int index=sukiBaseBean.actionEvent(event);
		PurchaseBillTrans trans=purchaseBillMaster.getPurchaseBillTransList().get(index);
		trans.setEditBoolean(true);
		trans.setNosForEdit(trans.getQty());
		trans.setProductForEdit(trans.getProductId());
		trans.setUomForEdit(trans.getUom());
		purchaseBillMaster.getPurchaseBillTransList().set(index, trans);
	}
	
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
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


	public PurchaseBillMaster getPurchaseBillMaster() {
		return purchaseBillMaster;
	}


	public void setPurchaseBillMaster(PurchaseBillMaster purchaseBillMaster) {
		this.purchaseBillMaster = purchaseBillMaster;
	}
	public List<InwardMaster> getInwardMasterList() {
		return inwardMasterList;
	}
	public void setInwardMasterList(List<InwardMaster> inwardMasterList) {
		this.inwardMasterList = inwardMasterList;
	}
	public List<InwardMaster> getSelectedInwardMasterList() {
		return selectedInwardMasterList;
	}
	public void setSelectedInwardMasterList(List<InwardMaster> selectedInwardMasterList) {
		this.selectedInwardMasterList = selectedInwardMasterList;
	}

}
