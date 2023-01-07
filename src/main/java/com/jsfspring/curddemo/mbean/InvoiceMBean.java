package com.jsfspring.curddemo.mbean;


import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.BillTransDomain;
import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.DeliveryChalanDomain;
import com.jsfspring.curddemo.entity.DeliveryChalanMaster;
import com.jsfspring.curddemo.entity.ExpenseDomain;
import com.jsfspring.curddemo.entity.PriceListForInvoice;
import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.ProductSellPriceDomain;
import com.jsfspring.curddemo.entity.ProductUom;
import com.jsfspring.curddemo.entity.QuotationMaster;
import com.jsfspring.curddemo.entity.QuotationProductUom;
import com.jsfspring.curddemo.entity.QuotationTrans;
import com.jsfspring.curddemo.repositry.BillMasterRepo;
import com.jsfspring.curddemo.repositry.BillTransRepo;
import com.jsfspring.curddemo.repositry.DcMasterRepo;
import com.jsfspring.curddemo.repositry.ExpenseRepo;
import com.jsfspring.curddemo.repositry.QuotationMasterRepo;
import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;;
import java.awt.Desktop;
@Controller("invoiceMBean")
@SessionScope
public class InvoiceMBean{

	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public BillMasterRepo billMasterRepo;
	
	@Autowired
	public BillTransRepo billTransRepo;
	
	@Autowired
	public DcMasterRepo dcMasterRepo;
	
	@Autowired
	public PdfDocuments pdfDocuments;
	
	@Autowired
	public QuotationMasterRepo quotationMasterRepo;
	
	@Autowired
	public ExpenseRepo expenseRepo;
	
	public BillMasterDomain  billMaster=new BillMasterDomain();
	public List<DeliveryChalanMaster> dcMasterList=new ArrayList<DeliveryChalanMaster>();
	public List<DeliveryChalanMaster> selectedDcMasterList=new ArrayList<DeliveryChalanMaster>();
	
	public List<PriceListForInvoice> priceList;
	
	public ExpenseDomain expense;
	public List<ExpenseDomain> expenseList;
	
	public Company company;
	
	public ProductDomain product;
	int addProductRow;
	
	String invoice="/jsfspring/pages/Invoice/InvoiceOverview.xhtml";
	String newinvoice="/jsfspring/pages/Invoice/newInvoice.xhtml";
	String expensePage="/jsfspring/pages/Invoice/expenses.xhtml";
	
	public InvoiceMBean(){
		
	}
	public void addCompany() {
		company=new Company();
	}
	public void saveCompany() {
		company = sukiBaseBean.saveCompany(company);
		if(company.getCompId()>0) {
			billMaster.setCompanyId(company);
			sukiBaseBean.dialogHide("addCompany");
		}
	}
	public void addProduct(ActionEvent event) {
		addProductRow = sukiBaseBean.actionEvent(event);
		product=new ProductDomain();
	}
	public void saveProduct() {
		product = sukiBaseBean.saveProduct(product);
//		if(product.getProdCode()>0) {
//			billMaster.getBillTransList().get(addProductRow).setProductId(product);
//			billMaster.getBillTransList().get(addProductRow).setGst(product.getCgst()+product.getSgst());
		sukiBaseBean.dialogHide("addProduct");
//		}
	}
	public void addNewUom() {
		ProductUom uom = new ProductUom();
		product.addUomTrans(uom);
		System.out.println("MBEAN LIST SIZE---" + product.getProdUomTransList().size());
	}
	public void newExpense() {
		expense = new ExpenseDomain();
	}
	
	public void expenseSave() {
		expenseRepo.save(expense);
	}
	
	public void expenseDelete() {
		expenseRepo.delete(expense);
	}
	
	public void expenseOverview() {
		sukiBaseBean.t = expense;
		sukiBaseBean.overviewList();
		sukiBaseBean.pageRedirect(expensePage);
	}
	
	public void getExpenseEditAction(ActionEvent event) {
		expense = new ExpenseDomain();
		expense=expenseRepo.findById(sukiBaseBean.actionEvent(event)).get();
		expense.setEditBoolean(true);
	}
	
	public void getExpenseDeleteAction(ActionEvent event) {
		expense = new ExpenseDomain();
		expenseRepo.deleteById(sukiBaseBean.actionEvent(event));
	}
	
	public String decimalPattern(int decimal) {
		System.out.println("decimal"+decimal);
		NumberFormat formatter1 = null;
		if (decimal == 0)
			formatter1 = new DecimalFormat("#0");
		if (decimal == 1)
			formatter1 = new DecimalFormat("#0.0");
		if (decimal == 2)
			formatter1 = new DecimalFormat("#0.00");
		if (decimal == 3)
			formatter1 = new DecimalFormat("#0.000");
		String value3 = formatter1.format(0000);
		return value3;
	}
	
	public String decimalPatternForConvertNo(int decimal) {
		System.out.println("decimal"+decimal);
		String formatter1 = null;
		if (decimal == 0)
			formatter1 = "0";
		if (decimal == 1)
			formatter1 = "0.0";
		if (decimal == 2)
			formatter1 = "0.00";
		if (decimal == 3)
			formatter1 = "0.000";
		return formatter1;
	}

	public void newInvoice() {
		newReset();
		sukiBaseBean.pageRedirect(newinvoice);
	}
	
	public void newReset() {
		billMaster=new BillMasterDomain();
		try {
			billMaster.setBillNo(commonObjects.getAutoNumber("billNo","BillMasterDomain"));
		} catch (SukiException e) {
			e.printStackTrace();
		}
		billMaster.setDate(SukiAppUtil.getCurrentDateAndTime());
	}
	
	public void updateInvoice(){
				if(billMaster.getInvoiceType().equalsIgnoreCase("Direct"))
					billMaster.setDcMaster(billMaster.getDcMasterFromBillMaster(billMaster));
				billMaster=billMasterRepo.save(billMaster);
				sukiBaseBean.addMessage("Invoice", "Invoice updated successfully");
	}
	
	public void getInvObjActionEvent(ActionEvent event) {
			billMaster=new BillMasterDomain();
			billMaster=billMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			billMaster.setEditBoolean(true);
			if(billMaster.isGstBill()) {
			Map<Double,Double> map=billMaster.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getGst, Collectors.summingDouble(BillTransDomain::getGstAmt)));
			billMaster.setGstValue(map);
			}
			billMaster.setTotalWithoutTax(billMaster.getBillTransList().parallelStream().mapToDouble(i->i.getAmount()).sum());
			sukiBaseBean.pageRedirect(newinvoice);
	}
	
	public void getDeleteActionEvent(ActionEvent event) {
			billMaster=new BillMasterDomain();
			billMaster=billMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			deleteFunction();
	}
	public void deleteFunction() {
		if(billMaster.getPaymentNo()!= null) {
			sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Invoice", "can't  delete Bill already paid for "+billMaster.getPaymentNo().getPaymentNo()));
			return;
		}
		if(billMaster.getInvoiceType().equalsIgnoreCase("DC"))
			billMasterRepo.updateBeforeDelete(billMaster.getBillNo());
			billMasterRepo.delete(billMaster);
			sukiBaseBean.addMessage("Delivery Chalan", "Deleted Successfullly");
	}
		
	public void getDelete() {
		deleteFunction();
		newInvoice();
	}
	public void OverviewListFromMenu() {
		sukiBaseBean.pageRedirect(invoice);
		sukiBaseBean.t=billMaster;
		sukiBaseBean.overviewList();
	}
	
	public String getDcList()throws Exception{
			if(billMaster.getCompanyId()!=null)
			    dcMasterList=dcMasterRepo.findDcByPendingStatus(billMaster.getCompanyId().getCompId());
			else
				sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Invoice", "Select company"));
			if(dcMasterList.size()>0)
				sukiBaseBean.dialogShow("dlg2");
			else
				sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Invoice", "There is no pending Dc's for "+billMaster.getCompanyId().getCompName()));
		return "";
	}
	
	public void printInvoice() {
		invoicePdfprint(billMaster);
	}
	public void print(ActionEvent event) {
		try {
			billMaster=new BillMasterDomain();
			billMaster=billMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			Map<Double,Double> map=billMaster.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getGst, Collectors.summingDouble(BillTransDomain::getGstAmt)));
			billMaster.setTotalWithoutTax(billMaster.getBillTransList().parallelStream().mapToDouble(i->i.getAmount()).sum());
			billMaster.setGstValue(map);
			invoicePdfprint(billMaster);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void invoicePdfprint(BillMasterDomain billMaster) {
		try {
		PdfDocuments.createBill(billMaster);
		String desktopPath = System.getProperty("user.home")+"\\Desktop\\";
		String desktopPathModified = desktopPath.replace("\\","/");
//		String desktopPathModified = "/home";
		String file=desktopPathModified+"/INVOICE/savedBill/"+billMaster.getBillNo()+".pdf";
//		String file=SukiAppConstants.BILL_FOLDER+billMaster.getBillNo()+".pdf";
		File pdfFile = new File(file);
		DesktopApi.open(pdfFile);
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
	
	
	public String billSelect(){
		try{
			StringJoiner joiner=new StringJoiner(",","","");
			// StringBuffer sf=new StringBuffer();
			billMaster.setBillTransList(new ArrayList<BillTransDomain>());
		    selectedDcMasterList.stream().forEach(i->{
		    	i.setStatus("Billed");
		    	joiner.add(String.valueOf(i.getDeliveryNo()));
		    	i.setBillNo(billMaster);
		    	i.getDcTransList().forEach(j->{
			BillTransDomain newBill=new BillTransDomain();
			ProductDomain product=j.getItems();
			newBill.setProductId(product);
			newBill.setUom(j.getUom());
			newBill = setRateForBillTrans(newBill);
			newBill.setQty(j.getNos());
			 if(j.getUom()!=null)
		       newBill.setRate(j.getUom().getPrice());
			 else
				 newBill.setRate(product.getRate()); 
		    newBill.setGst(product.getCgst()+product.getSgst());
		    newBill.setHsn(product.getHsnCode());
		    newBill.setStationOrHouse(product.getStationOrHouse());
		    newBill.setAmount(newBill.getQty()*newBill.getRate());
		    if(billMaster.isGstBill())
		    newBill.setGstAmt((newBill.getGst()*newBill.getAmount())/100);
		    newBill.setTotalAmount(newBill.getAmount()+newBill.getGstAmt());
		    newBill.setEditBoolean(true);
		    billMaster.setTotalWithoutTax(billMaster.getTotalWithoutTax()+newBill.getAmount());
		    billMaster.setTotalAmount(SukiAppUtil.roundedOff(billMaster.getTotalAmount()+newBill.getTotalAmount()));
		    System.out.println(newBill.getAmount()+" "+newBill.getGstAmt()+" "+billMaster.getTotalAmount());
		    billMaster.addTrans(newBill);
		});});
     		Map<Double,Double> map=billMaster.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getGst, Collectors.summingDouble(BillTransDomain::getGstAmt)));
			billMaster.setGstValue(map);
		    billMaster.setDcNos(joiner.toString());
		// billMaster.setAmountString(SukiAppUtil.getNumericWords(billMaster.getTotalAmount()));
		}catch (Exception e) {
		}
		return "";
		}

	public void billItemRateEdit(AjaxBehaviorEvent event) throws Exception {
		int i = sukiBaseBean.ajaxEvent(event);
		List<BillTransDomain> billTransListEdit = billMaster.getBillTransList();
		billTransListEdit.get(i).setAmount(billTransListEdit.get(i).getRate() * billTransListEdit.get(i).getQty());
//		if(billMaster.isGstBill())
//		billTransListEdit.get(i).setGstAmt((billTransListEdit.get(i).getGst() * billTransListEdit.get(i).getAmount() )/100);
		//billTransListEdit.get(i).setTotalAmount(billTransListEdit.get(i).getAmount()
		//		+ billTransListEdit.get(i).getGstAmt());
		getBillAmount(billTransListEdit);
//		sukiBaseBean.dialogHide("dlg3");
	}
	
	public void billItemMrpEdit() {
		sukiBaseBean.dialogHide("dlg3");
	}
	
	public void setNonGstBill() {
		List<BillTransDomain> billTransListEdit = billMaster.getBillTransList();
		if(!billMaster.isEditBoolean()) {
			billTransListEdit.forEach(i->i.setGstAmt(0));
			getBillAmount(billTransListEdit);
		}else {
			billTransListEdit.forEach(i->i.setGstAmt((i.getGst()*i.getAmount())/100));
			getBillAmount(billTransListEdit);
		}
//		billMaster.setEditBoolean(!billMaster.isEditBoolean());
	}
	
	public void addFreightAmount(){
		double gstAmt=billMaster.getBillTransList().parallelStream().mapToDouble(j -> j.getGstAmt()).sum();
		billMaster.setTotalAmount(SukiAppUtil.roundedOff(billMaster.getTotalWithoutTax()+gstAmt+billMaster.getFreightCharges()));
	}
	public void getBillAmount(List<BillTransDomain> billTransListEdit) {
		double amtWithoutTax=billTransListEdit.parallelStream().mapToDouble(j -> j.getAmount()).sum();
		double gstAmt = 0;
		if(billMaster.isGstBill())
		gstAmt=billTransListEdit.parallelStream().mapToDouble(j -> j.getGstAmt()).sum();
		billMaster.setGstAmount(gstAmt);
		billMaster.setTotalWithoutTax(amtWithoutTax);
		billMaster.setTotalAmount(SukiAppUtil.roundedOff(amtWithoutTax+gstAmt+billMaster.getFreightCharges()));
		billMaster.setAmountString(SukiAppUtil.getNumericWords(billMaster.getTotalAmount()));
		billMaster.setBillTransList(billTransListEdit);
		Map<Double,Double> map=billMaster.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getGst, Collectors.summingDouble(BillTransDomain::getGstAmt)));
		billMaster.setGstValue(map);
	}
	public String addRowBillTrans(){
		BillTransDomain billTrans=new BillTransDomain();
		billTrans.setEditBoolean(true);
		billTrans.setBillMaster(billMaster);
		billMaster.getBillTransList().add(billTrans);
		return "";
	}
	public void getRateFromQuotation(SelectEvent event) {
		int i=sukiBaseBean.selectEvent(event);
		BillTransDomain trans = setRateForBillTrans(billMaster.getBillTransList().get(i));
		billMaster.getBillTransList().set(i, trans);
		priceList = new ArrayList<PriceListForInvoice>();
		priceList = trans.getPriceList();
	}
	public BillTransDomain setRateForBillTrans(BillTransDomain billTrans) {
		ProductDomain product=billTrans.getProductId();
		ProductUom uom = billTrans.getUom();
		QuotationMaster qmaster = quotationMasterRepo.findByCompanyId(billMaster.getCompanyId());
		if(qmaster!=null) {
		List<QuotationTrans> transList = qmaster.getQuotTransList().stream().filter(j-> j.getProductId().getProdCode()==billTrans.getProductId().getProdCode()).collect(Collectors.toList());
		if(transList.size()>0) {
			QuotationProductUom quotUom = transList.get(0).getQuotUomList().stream().filter(k->k.getUomId().getUnitName().equals(uom.getUomId().getUnitName())).findFirst().get();
			billTrans.setRate(quotUom.getPrice());
			billTrans.setGst(quotUom.getGst());
			billTrans.getPriceList().add(new PriceListForInvoice("Quotation", qmaster.getQuotationNo(), SukiAppUtil.getUtilDateFromSQLDate(qmaster.getDate()), quotUom.getPrice()));
		}else {
			List<ProductSellPriceDomain> prodPriceList = product.getProductSellPriceList();
			if(prodPriceList.size()>0) {
				ProductSellPriceDomain price = prodPriceList.stream().filter(j-> j.getProductUom().getUomId().getUnitName().equals(uom.getUomId().getUnitName())).filter(j->j.isEnable()).findFirst().get();
				billTrans.setRate(price.getSellPrice());
				billTrans.getPriceList().add(new PriceListForInvoice("Purchase",0, SukiAppUtil.getUtilDateFromSQLDate(price.getDate()), price.getRate()));
				billTrans.setPurchaseRate(price.getRate());
			}
		}
		}else {
			List<ProductSellPriceDomain> prodPriceList = product.getProductSellPriceList();
			if(prodPriceList.size()>0) {
				ProductSellPriceDomain price = prodPriceList.stream().filter(j-> j.getProductUom().getUomId().getUnitName().equals(uom.getUomId().getUnitName())).filter(j->j.isEnable()).findFirst().get();
				billTrans.setRate(price.getSellPrice());
				billTrans.getPriceList().add(new PriceListForInvoice("Purchase",0, SukiAppUtil.getUtilDateFromSQLDate(price.getDate()), price.getRate()));
				billTrans.setPurchaseRate(price.getRate());
			}
		}
		List<BillTransDomain> pastTransList = billTransRepo.findProductByCompId(billMaster.getCompanyId().getCompId(), product.getProdCode());
		if(pastTransList.size()>0) {
			BillTransDomain pastTrans = pastTransList.get(pastTransList.size()-1);
			billTrans.setRate(pastTrans.getRate());
			billTrans.getPriceList().add(new PriceListForInvoice("Past Rate---"+pastTrans.getUom().getUomName(),pastTrans.getBillMaster().getBillNo(), SukiAppUtil.getUtilDateFromSQLDate(pastTrans.getBillMaster().getDate()), pastTrans.getRate()));
		}
		return billTrans;
	}
	
	public void billTransDetailsFromProduct(SelectEvent event) {
		int i=sukiBaseBean.selectEvent(event);
		ProductDomain product=new ProductDomain();
		product=billMaster.getBillTransList().get(i).getProductId();
		
		billMaster.getBillTransList().get(i).setRate(product.getRate());
		billMaster.getBillTransList().get(i).setGst(product.getCgst()+product.getSgst());
		if(billMaster.isEditBoolean())
			billMaster.getBillTransList().get(i).setGstAmt((billMaster.getBillTransList().get(i).getGst()* billMaster.getBillTransList().get(i).getAmount())/100);	
		billMaster.getBillTransList().get(i).setHsn(product.getHsnCode());
		billMaster.getBillTransList().get(i).setStationOrHouse(product.getStationOrHouse());
		System.out.println("GST---"+billMaster.getBillTransList().get(i).getGst());
	}
	public void getInvoiceTransForProduct() {
		sukiBaseBean.t=new BillTransDomain();
		sukiBaseBean.overviewList();
	}
	
	public void deleteBillItem(ActionEvent event) {
		int i=sukiBaseBean.actionEvent(event);
		BillTransDomain trans= billMaster.getBillTransList().get(i);
		if(trans.getRowId()>0) {
			billTransRepo.delete(trans);
			sukiBaseBean.addMessage("Invoice", "Invoice Item deleted successfully");
		}
		billMaster.getBillTransList().remove(i);
		getBillAmount(billMaster.getBillTransList());
	}
	public void saveBill(){
		try {
			if(billMaster.getInvoiceType().equalsIgnoreCase("Direct")) {
					billMaster.setDcMaster(new DeliveryChalanMaster(billMaster));;
					billMaster.getDcMaster().setDeliveryNo(commonObjects.getAutoNumber("deliveryNo","DeliveryChalanMaster"));
				billMasterRepo.save(billMaster);
				sukiBaseBean.addMessage("Invoice", "Invoice saved successfully");
			}else {
		//		selectedDcMasterList.parallelStream().forEach(i->{i.setStatus("Billed");i.setBillNo(billMaster);i.setForBill(String.valueOf(billMaster.getBillNo()))};
			billMaster.setDcMasterList(selectedDcMasterList);
			Company company=billMaster.getCompanyId();
			billMaster.setDcMaster(null);
			int i=0;
			if(SukiAppUtil.isEmpty(billMaster.getCompanyId().getSplit())){
				sukiBaseBean.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR,"can't save","select type for company"));
				return ;
			}
			if(!SukiAppUtil.isEmpty(company.getSplit())){
			if(company.getSplit().equalsIgnoreCase("yes")){
				Map<String,List<BillTransDomain>> separateBillList=billMaster.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getStationOrHouse, Collectors.toList()));
				for(Map.Entry<String,List<BillTransDomain>> map:separateBillList.entrySet()) {
					BillMasterDomain bill=billMaster;
					if(i!=0)
						bill.setBillNo(billMaster.getBillNo()+1);
					bill.setBillTransList(map.getValue());
					Map<Double,Double> gstMap=bill.getBillTransList().parallelStream().collect(Collectors.groupingBy(BillTransDomain::getGst, Collectors.summingDouble(BillTransDomain::getGstAmt)));
					bill.setGstValue(gstMap);
					// double gstAmt=map.getValue().parallelStream().collect(Collectors.summingDouble(j->j.getGstAmt()));
					
					bill.setTotalWithoutTax(map.getValue().parallelStream().collect(Collectors.summingDouble(j->j.getAmount())));
					bill.setTotalAmount(map.getValue().parallelStream().collect(Collectors.summingDouble(j->j.getGstAmt())) + bill.getTotalWithoutTax());
					bill.setTotalAmount(SukiAppUtil.roundedOff(bill.getTotalAmount()));
					// bill.setByteFilePdf(PdfDocuments.createBill(billMaster.getBillNo(),company,billMaster,newBill,stationary,SukiAppUtil.getDate(billMaster.getDate())));
					System.out.println("SAVED---");
					billMasterRepo.save(bill);
				    i++;
				}
			}else {
				billMasterRepo.save(billMaster);
				}
			billMaster.setEditBoolean(true);
			sukiBaseBean.addMessage("Invoice", "Invoice saved successfully");
			}}}catch (Exception e) {
			System.out.println("Error---"+e);
			}
		}
			
	public void saveInvItem(ActionEvent event) {
			BillTransDomain trans=billMaster.getBillTransList().get(sukiBaseBean.actionEvent(event));
			if(trans.getRowId()>0) {
				if(billMaster.getInvoiceType().equalsIgnoreCase("Direct"))
					trans.setDcTrans(trans.getDcTransForBillUpdate(trans.getDcTrans(),trans));
			        billTransRepo.save(trans);
			        sukiBaseBean.addMessage("Invoice", "Invoice Item updated successfully");
			}else {
				if(billMaster.getInvoiceType().equalsIgnoreCase("Direct"))
				trans.setDcTrans(new DeliveryChalanDomain(trans));
				billTransRepo.save(trans);
				sukiBaseBean.addMessage("Invoice", "Invoice Item saved successfully");
			}
			billMaster.getBillTransList().get(sukiBaseBean.actionEvent(event)).setEditBoolean(false);
	}
	
	public void pastRateForItem(ActionEvent event){
		int index=sukiBaseBean.actionEvent(event);
		BillTransDomain trans=billMaster.getBillTransList().get(index);
		priceList = trans.getPriceList();
	}
	
	public void invItemEdit(ActionEvent event) {
		int index=sukiBaseBean.actionEvent(event);
		BillTransDomain trans=billMaster.getBillTransList().get(index);
		trans.setEditBoolean(true);
		trans.setNosForEdit(trans.getQty());
		trans.setProductForEdit(trans.getProductId());
		trans.setUomForEdit(trans.getUom());
		billMaster.getBillTransList().set(index, trans);
	}
		
	
	
	public List<DeliveryChalanMaster> getSelectedDcMasterList() {
		return selectedDcMasterList;
	}

	public void setSelectedDcMasterList(List<DeliveryChalanMaster> selectedDcMasterList) {
		this.selectedDcMasterList = selectedDcMasterList;
	}

	public BillMasterDomain getBillMaster() {
		return billMaster;
	}

	public void setBillMaster(BillMasterDomain billMaster) {
		this.billMaster = billMaster;
	}

	public List<DeliveryChalanMaster> getDcMasterList() {
		return dcMasterList;
	}

	public void setDcMasterList(List<DeliveryChalanMaster> dcMasterList) {
		this.dcMasterList = dcMasterList;
	}

		public LazyDataModel getModel() {
			return sukiBaseBean.model;
			}

		public List<PriceListForInvoice> getPriceList() {
			return priceList;
		}

		public void setPriceList(List<PriceListForInvoice> priceList) {
			this.priceList = priceList;
		}

		public Company getCompany() {
			return company;
		}

		public void setCompany(Company company) {
			this.company = company;
		}
		public ProductDomain getProduct() {
			return product;
		}
		public void setProduct(ProductDomain product) {
			this.product = product;
		}

	}