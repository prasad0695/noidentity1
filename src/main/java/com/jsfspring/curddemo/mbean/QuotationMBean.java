package com.jsfspring.curddemo.mbean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jsfspring.curddemo.entity.BillTransDomain;
import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.OverviewListAndCount;
import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.ProductSellPriceDomain;
import com.jsfspring.curddemo.entity.ProductUom;
import com.jsfspring.curddemo.entity.QuotationMaster;
import com.jsfspring.curddemo.entity.QuotationProductUom;
import com.jsfspring.curddemo.entity.QuotationTrans;
import com.jsfspring.curddemo.entity.SupplierDomain;
import com.jsfspring.curddemo.repositry.QuotationMasterRepo;
import com.jsfspring.curddemo.repositry.QuotationTransRepo;
import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

@Controller("quotationMBean")
public class QuotationMBean<T> {
	
	String addQuotation = "/jsfspring/pages/Quotation/newQuotation.xhtml";
	String quotationOverview = "/jsfspring/pages/Quotation/QuotationOverview.xhtml";
	
	public List<T> prodoverViewList;
	OverviewListAndCount prodoverview;
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public QuotationMasterRepo quotationMasterRepo;
	
	@Autowired
	public QuotationTransRepo quotationTransRepo;
	
	@Autowired
	public CommonObjects commonObjects;
	
	public QuotationMaster quotationMaster;
	
	public List<ProductDomain> selectedProductList;
	
	public ProductDomain product;
	
	public Company company;
	
	public QuotationMBean(){
		selectedProductList = new ArrayList<ProductDomain>();
		quotationMaster = new QuotationMaster();
		product = new ProductDomain();
	}
	
	public void getQuotMasterList() {
		sukiBaseBean.t = quotationMaster;
		sukiBaseBean.overviewList();
		sukiBaseBean.pageRedirect(quotationOverview);
	}
	
	public String getQuotationActionEvent(ActionEvent event) {
			quotationMaster = quotationMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			/*
			 * for(int i=0;i<quotationMaster.getQuotTransList().size();i++) {
			 * System.out.println(quotationMaster.getQuotTransList().get(i).getQuotUomList()
			 * .get(0).getUomName()+" "+quotationMaster.getQuotTransList().get(i).
			 * getQuotUomList().get(0).getPrice());
			 * System.out.println(quotationMaster.getQuotTransList().get(i).getQuotUomList()
			 * .get(1).getUomName()+" "+quotationMaster.getQuotTransList().get(i).
			 * getQuotUomList().get(1).getPrice()); }
			 */
			sukiBaseBean.pageRedirect(addQuotation);
		return "";
	}

	public void addCompany() {
		company=new Company();
	}
	public void saveCompany() {
		company = sukiBaseBean.saveCompany(company);
		if(company.getCompId()>0) {
			quotationMaster.setCompanyId(company);
			sukiBaseBean.dialogHide("addCompany");
		}
	}
	public String getQuotationDeleteActionEvent(ActionEvent event) {
			quotationMasterRepo.deleteById(sukiBaseBean.actionEvent(event));
		return "";
	}

	public void printQuotation(ActionEvent event) {
		try {
			quotationMaster = quotationMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
			quotationPdfprint(quotationMaster);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void print() {
		quotationPdfprint(quotationMaster);
	}

	public void quotationPdfprint(QuotationMaster quotationMaster) {
		try {
			PdfDocuments.createQuotation(quotationMaster);
			String file = SukiAppConstants.QUOTATION_FOLDER + quotationMaster.getQuotationNo() + ".pdf";
			File pdfFile = new File(file);
			if (pdfFile.exists()) {
				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
				p.waitFor();
			} else {
				System.out.println("File is not exists");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newQuotation() {
		quotationMaster = new QuotationMaster();
		try {
			quotationMaster.setQuotationNo(commonObjects.getAutoNumber("quotationNo", "QuotationMaster"));
		} catch (SukiException e) {
			e.printStackTrace();
		}
		sukiBaseBean.pageRedirect(addQuotation);
	}

	public void getQuotList() {
//		quotationMaster.setQuotTransList(new ArrayList<QuotationTrans>());
		if(selectedProductList!=null && selectedProductList.size()>0) {
			int index=0;
			selectedProductList.parallelStream().forEach(i -> {
			QuotationTrans trans = new QuotationTrans();
			List<QuotationProductUom> quotUomList = new ArrayList<QuotationProductUom>();
//			trans.setSlNo(index);
			trans.setProductId(i);
			trans.setItem(i.getItem());
			trans.setGst(i.getGst());
			for(int j=0; j<i.getQuotUomList().size(); j++ ) {
				QuotationProductUom uomTrans = new QuotationProductUom();
				String searchUom = i.getQuotUomList().get(j);
				ProductUom uom = null;
				Optional<ProductSellPriceDomain> priceDomainOptinal = i.getProductSellPriceList().stream().filter(o->o.getProductUom().getUomId().getUnitName().equals(searchUom)).findFirst();
				ProductSellPriceDomain priceDomain = priceDomainOptinal.isPresent() ?  priceDomainOptinal.get() : null;
				if(priceDomain==null) {
				uom = i.getProdUomTransList().stream().filter(o->o.getUomId().getUnitName().equals(searchUom)).findFirst().get();
				uomTrans.setUomId(uom.getUomId());
				uomTrans.setPrice(uom.getPrice());
				}else {
				uomTrans.setUomId(priceDomain.getProductUom().getUomId());
				uomTrans.setPrice(priceDomain.getSellPrice());
				}
				uomTrans.setGst(i.getGst());
				uomTrans.setQuotTransRowId(trans);
				quotUomList.add(uomTrans);
			}
			trans.setQuotUomList(quotUomList);
//			i.getQuotUomList().parallelStream().forEach(j -> {
//				QuotationProductUom uomTrans = new QuotationProductUom();
//				uomTrans.setUomId(j.getUomId());
//				uomTrans.setPrice(j.getPrice());
//				trans.addTrans(uomTrans);
//			});
			quotationMaster.addTrans(trans);
		});
		sukiBaseBean.dialogHide("dlg1");
		}else {
			sukiBaseBean.errorMessage("Quotation", "Select Products to add");
			return;
		}
	}

	public void quotationSave() {
			if(sukiBaseBean.validateList(quotationMaster.getQuotTransList().size(), "Quotation"))
				return;
			quotationMasterRepo.save(quotationMaster);
			sukiBaseBean.addMessage("Quotation","Saved successfully");
	}
	
	public void deleteQuotItem(ActionEvent event) {
		int i=sukiBaseBean.actionEvent(event);
		QuotationTrans trans= quotationMaster.getQuotTransList().get(i);
		if(trans.getRowId()>0) {
			quotationTransRepo.delete(trans);
			sukiBaseBean.addMessage("Invoice", "Invoice Item deleted successfully");
		}
		quotationMaster.getQuotTransList().remove(i);
	}
	
	public void productOverviewListForQuotation() {
		sukiBaseBean.t = product;
		sukiBaseBean.overviewDialogList();
		selectedProductList = new ArrayList<ProductDomain>();
	}
	
	private LazyDataModel<T> model1;

	@PostConstruct
	public void init() {
		try {
			this.model1 = new LazyDataModel<T>() {
				private static final long serialVersionUID = 1L;

				@Override
				public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
						Map<String, Object> filters) {
						T t = (T) product;
						prodoverview = commonObjects.getAll(t, first, pageSize, sortField,
								sortOrder.toString(), filters);
						if(prodoverview!=null)
							prodoverViewList=prodoverview.getOverviewList();
					return prodoverViewList;
				}

				@Override
				public T getRowData(String rowKey) {
					int intRowKey = Integer.parseInt(rowKey);
					for (Object event : prodoverViewList) {
						ProductDomain p = (ProductDomain) event;
						System.out.println("ROWKEY" + intRowKey + p.getClass().getName() + p.getProdCode());
						if (p.getClass().getName().contains("ProductDomain") && p.getProdCode() == intRowKey) {// Error
																												// here
							return (T) p;
						}
					}
					return null;
				}

				@Override
				public Object getRowKey(T t) {
					if (t.getClass().getName().equals("ProductDomain")) {
						ProductDomain p = (ProductDomain) t;
						return p.getProdCode();
					}
					return null;
				}
			};
			model1.setRowCount(30);
		} catch (Exception e) {
			System.out.println("Exception in CityListProducer " + e);
		}

	}

//	public String getQuotItemActionEvent(ActionEvent event) {
//		quotationMaster = quotationMaster.getQuotTransList().get(actionEvent(event));
//		pageRedirect(addQuotation);
//		return "";
//	}
////
//	public String getQuotItemDeleteActionEvent(ActionEvent event) {
//		quotationMaster = quotationMasterList.get(actionEvent(event));
//		try {
//			SukiServiceAPI.getInstance().QuotationDelete(quotationMaster);
//		} catch (SukiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}

	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}

	public QuotationMaster getQuotationMaster() {
		return quotationMaster;
	}

	public void setQuotationMaster(QuotationMaster quotationMaster) {
		this.quotationMaster = quotationMaster;
	}

	public List<ProductDomain> getSelectedProductList() {
		return selectedProductList;
	}

	public void setSelectedProductList(List<ProductDomain> selectedProductList) {
		this.selectedProductList = selectedProductList;
	}

	public LazyDataModel<T> getModel1() {
		return model1;
	}

	public void setModel1(LazyDataModel<T> model1) {
		this.model1 = model1;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
}
