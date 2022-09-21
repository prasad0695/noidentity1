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
import com.jsfspring.curddemo.entity.ProductSellPriceDomain;
import com.jsfspring.curddemo.entity.ProductUom;
import com.jsfspring.curddemo.entity.PurchaseBillMaster;
import com.jsfspring.curddemo.entity.PurchaseBillTrans;
import com.jsfspring.curddemo.entity.PurchaseOrderMaster;
import com.jsfspring.curddemo.entity.SupplierDomain;
import com.jsfspring.curddemo.repositry.InwardMasterRepo;
import com.jsfspring.curddemo.repositry.ProductSellPriceRepo;
import com.jsfspring.curddemo.repositry.PurchaseBillMasterRepo;
import com.jsfspring.curddemo.repositry.PurchaseBillTransRepo;
import com.jsfspring.curddemo.utills.SukiAppConstants;
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
	
	@Autowired
	public ProductSellPriceRepo productSellPriceRepo;
	
	public PurchaseBillMaster  purchaseBillMaster;
	public List<InwardMaster> inwardMasterList;
	public List<InwardMaster> selectedInwardMasterList;
	public List<PurchaseOrderMaster> poMasterList;
	public List<PurchaseOrderMaster> selectedPoMasterList;
	
	public List<ProductSellPriceDomain> productSellPriceList;
	public SupplierDomain supplier;
	public ProductDomain product;
	int addProductRow;
	
	
	String purchaseInvoice="/jsfspring/pages/PurchaseInvoice/PurchaseInvoiceOverview.xhtml";
	String newPurchaseinvoice="/jsfspring/pages/PurchaseInvoice/newPurchaseInvoice.xhtml";
	String newSupplier="/jsfspring/pages/PurchaseInvoice/newPurchaseInvoice.xhtml";
	
	public PurchaseInvoiceMBean(){
		purchaseBillMaster=new PurchaseBillMaster();
		inwardMasterList=new ArrayList<InwardMaster>();
		selectedInwardMasterList=new ArrayList<InwardMaster>();
		poMasterList=new ArrayList<PurchaseOrderMaster>();
		selectedPoMasterList=new ArrayList<PurchaseOrderMaster>();
		productSellPriceList=new ArrayList<ProductSellPriceDomain>(16);
	}
	public PurchaseBillMaster getInwardMasterFromPurchaseBill(PurchaseBillMaster billMaster) {
		billMaster.getInwardMasterList().add(new InwardMaster(billMaster));
		return billMaster;
	}
	public void addSupplier() {
		supplier=new SupplierDomain();
	}
	public void saveSupplier() {
		supplier = sukiBaseBean.saveSupplier(supplier);
		if(supplier.getSupCode()>0) {
		purchaseBillMaster.setSupId(supplier);
		sukiBaseBean.dialogHide("addSupplier");
		}
	}
	public void addProduct(ActionEvent event) {
		addProductRow = sukiBaseBean.actionEvent(event);
		product=new ProductDomain();
	}
	public void addNewUom() {
		ProductUom uom = new ProductUom();
		product.addUomTrans(uom);
		System.out.println("MBEAN LIST SIZE---" + product.getProdUomTransList().size());
	}
	public void setNonGstBill() {
		List<PurchaseBillTrans> billTransListEdit = purchaseBillMaster.getPurchaseBillTransList();
		if(!purchaseBillMaster.isEditBoolean()) {
			billTransListEdit.forEach(i->i.setGstAmt(0));
			getBillAmount(billTransListEdit);
		}else {
			billTransListEdit.forEach(i->i.setGstAmt((i.getGst()*i.getAmount())/100));
			getBillAmount(billTransListEdit);
		}
//		billMaster.setEditBoolean(!billMaster.isEditBoolean());
	}
	public void saveProduct() {
		product = sukiBaseBean.saveProduct(product);
		if(product.getProdCode()>0) {
		purchaseBillMaster.getPurchaseBillTransList().get(addProductRow).setProductId(product);
		purchaseBillMaster.getPurchaseBillTransList().get(addProductRow).setGst(product.getCgst()+product.getSgst());
		sukiBaseBean.dialogHide("addProduct");
		}
	}
	
	public void newInvoice() {
		purchaseBillMaster=new PurchaseBillMaster();
		selectedInwardMasterList=new ArrayList<InwardMaster>();
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
//				for(int i=0;i<productSellPriceList.size();i++) {
//					if(productSellPriceList.get(i)!=null)
//						productSellPriceRepo.save(productSellPriceList.get(i));
//				}
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
			purchaseBillMaster.setTotalWithoutTax(purchaseBillMaster.getPurchaseBillTransList().parallelStream().mapToDouble(j -> j.getAmount()).sum());
		sukiBaseBean.pageRedirect(newPurchaseinvoice);
	}
	
	public void getDeleteActionEvent(ActionEvent event) {
			purchaseBillMaster=purchaseBillMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();	
			deletePuchaseInvoice();
	}
	
	public void deletePuchaseInvoice() {
		if(purchaseBillMaster.getStatus().equals(SukiAppConstants.PAID)) {
			sukiBaseBean.errorMessage("Purchase Invoice", "Already Paid");
			return;
		}
		if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("DC"))
		purchaseBillMasterRepo.updateBeforeDelete(purchaseBillMaster.getRowId());
		purchaseBillMasterRepo.delete(purchaseBillMaster);
		sukiBaseBean.addMessage("Purchase Invoice", "Deleted Successfully");
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
				i.setStatus("Billed");
				i.setPurchaseBillMaster(purchaseBillMaster);
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
	public void getBillAmount(List<PurchaseBillTrans> billTransListEdit) {
		double amtWithoutTax=billTransListEdit.parallelStream().mapToDouble(j -> j.getAmount()).sum();
		double gstAmt = 0;
		if(purchaseBillMaster.isGstBill())
		gstAmt=billTransListEdit.parallelStream().mapToDouble(j -> j.getGstAmt()).sum();
		purchaseBillMaster.setGstAmount(gstAmt);
		purchaseBillMaster.setTotalWithoutTax(amtWithoutTax);
		purchaseBillMaster.setTotalAmount(SukiAppUtil.roundedOff(amtWithoutTax+gstAmt));
		purchaseBillMaster.setAmountString(SukiAppUtil.getNumericWords(purchaseBillMaster.getTotalAmount()));
		purchaseBillMaster.setPurchaseBillTransList(billTransListEdit);
		Map<Double,Double> map=purchaseBillMaster.getPurchaseBillTransList().parallelStream().collect(Collectors.groupingBy(PurchaseBillTrans::getGst, Collectors.summingDouble(PurchaseBillTrans::getGstAmt)));
		purchaseBillMaster.setGstValue(map);
	}
	public void billItemRateEdit(AjaxBehaviorEvent event) throws Exception {
		int i = sukiBaseBean.ajaxEvent(event);
		List<PurchaseBillTrans> billTransListEdit = purchaseBillMaster.getPurchaseBillTransList();
		billTransListEdit.get(i).setAmount(billTransListEdit.get(i).getRate() * billTransListEdit.get(i).getQty());
//		billTransListEdit.get(i).setGstAmt((billTransListEdit.get(i).getGst() * billTransListEdit.get(i).getAmount())/100);
//		billTransListEdit.get(i).setTotalAmount(billTransListEdit.get(i).getAmount()
//				+ billTransListEdit.get(i).getGstAmt());
		getBillAmount(billTransListEdit);
//		if(billTransListEdit.get(i).getRate()>0) {
//		if(billTransListEdit.get(i).getProductSellPrice()==null) {
//			productSellPriceList.get(i).setRate(billTransListEdit.get(i).getRate());
//		}else {
//			if(billTransListEdit.get(i).getRate()!=billTransListEdit.get(i).getProductSellPrice().getRate()) {
//			ProductSellPriceDomain item = new ProductSellPriceDomain();
//			item.setProduct(billTransListEdit.get(i).getProductId());
//			item.setProductUom(billTransListEdit.get(i).getUom());
//			item.setRate(billTransListEdit.get(i).getRate());
//			item.setDate(purchaseBillMaster.getDate());
//			billTransListEdit.get(i).setProductSellPrice(item);
//			}
//		}
//		}
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
		List<PurchaseBillTrans> transList = purchaseBillMaster.getPurchaseBillTransList();
		productUom=purchaseBillMaster.getPurchaseBillTransList().get(i).getUom();
		PurchaseBillTrans billTrans=transList.get(i);
		
		billTrans.setAmount(billTrans.getQty()*billTrans.getRate());
		purchaseBillMaster.getPurchaseBillTransList().set(i, billTrans);
		ProductSellPriceDomain item;
		int count = (int) transList.stream().filter(j->j.getProductId().getProdCode()==billTrans.getProductId().getProdCode()).count();
		
		List<ProductSellPriceDomain> productSellPrice = productSellPriceRepo.findByProduct(billTrans.getProductId());
		if(productSellPrice.size()>0) {
		List<ProductSellPriceDomain> productSellPriceList1 = productSellPrice.stream()
				.filter(j->j.getProductUom().getRowId()==billTrans.getUom().getRowId()).collect(Collectors.toList());
		if(productSellPriceList1.size()>0) {
			item = productSellPriceList1.get(productSellPriceList1.size()-1);
			billTrans.setProductSellPrice(item);
			
			billTrans.setRate(item.getRate());
			billTrans.setSellingPrice(item.getSellPrice());
			billTrans.setMrp(item.getMrp());
		}else {
			if(count==1) {
			item = new ProductSellPriceDomain();
			item.setProduct(billTrans.getProductId());
			item.setProductUom(productUom);
			item.setDate(purchaseBillMaster.getDate());
			item.setEnable(true);
			billTrans.setProductSellPrice(item);
			}
		}
		}else {
			if(count==1) {
			item = new ProductSellPriceDomain();
			item.setProduct(billTrans.getProductId());
			item.setProductUom(productUom);
			item.setDate(purchaseBillMaster.getDate());
			item.setEnable(true);
			billTrans.setProductSellPrice(item);
			}
		}
	}
	
	public void deleteBillItem(ActionEvent event) {
		int i=sukiBaseBean.actionEvent(event);
		PurchaseBillTrans trans= purchaseBillMaster.getPurchaseBillTransList().get(i);
		if(trans.getRowId()>0) {
			   purchaseBillTransRepo.delete(trans);
		}
		purchaseBillMaster.getPurchaseBillTransList().remove(i);
	}
	
//	public void updateInvoice(){
//		if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("Direct"))
//			purchaseBillMaster.setDcMaster(purchaseBillMaster.getDcMasterFromBillMaster(purchaseBillMaster));
//		purchaseBillMaster=pu.save(purchaseBillMaster);
//		sukiBaseBean.addMessage("Invoice", "Invoice updated successfully");
//	}
	
	public void saveBill(){
		try {
			if(purchaseBillMaster.getInvoiceType().equalsIgnoreCase("Direct")) {
				purchaseBillMaster=getInwardMasterFromPurchaseBill(purchaseBillMaster);
				purchaseBillMaster.getInwardMasterList().get(0).setInwardNo(commonObjects.getAutoNumber("inwardNo","InwardMaster"));
				purchaseBillMaster.getPurchaseBillTransList().forEach(i-> {ProductSellPriceDomain priceDomain = productSellPriceRepo.save(i.getProductSellPrice());i.setProductSellPrice(priceDomain);});
				purchaseBillMaster=purchaseBillMasterRepo.save(purchaseBillMaster);
				sukiBaseBean.addMessage("Purchase Invoice", "Saved Successfully");
				return;
			}else {
//				purchaseBillMaster.setInwardMaster(null);
			}
			if(selectedInwardMasterList.size()>0)
			purchaseBillMaster.setInwardMasterList(selectedInwardMasterList);
			purchaseBillMaster=purchaseBillMasterRepo.save(purchaseBillMaster);
//			for(int i=0;i<productSellPriceList.size();i++) {
//				if(productSellPriceList.get(i)!=null)
//					productSellPriceRepo.save(productSellPriceList.get(i));
//			}
			sukiBaseBean.addMessage("Purchase Invoice", "Saved Successfully");
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
			ProductSellPriceDomain priceDomain = productSellPriceRepo.save(billTrans.getProductSellPrice());
			billTrans.setProductSellPrice(priceDomain);
			billTrans = purchaseBillTransRepo.save(billTrans);
			purchaseBillMaster.getPurchaseBillTransList().get(sukiBaseBean.actionEvent(event)).setEditBoolean(false);
	}
	
	public void invItemEdit(ActionEvent event) {
		int index=sukiBaseBean.actionEvent(event);
		PurchaseBillTrans trans=purchaseBillMaster.getPurchaseBillTransList().get(index);
		ProductSellPriceDomain pt = trans.getProductSellPrice();
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
	public SupplierDomain getSupplier() {
		return supplier;
	}
	public void setSupplier(SupplierDomain supplier) {
		this.supplier = supplier;
	}
	public ProductDomain getProduct() {
		return product;
	}
	public void setProduct(ProductDomain product) {
		this.product = product;
	}

}
