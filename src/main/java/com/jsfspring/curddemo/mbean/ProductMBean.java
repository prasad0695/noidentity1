package com.jsfspring.curddemo.mbean;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.ProductUom;
import com.jsfspring.curddemo.entity.QuotationMaster;
import com.jsfspring.curddemo.entity.QuotationTrans;
import com.jsfspring.curddemo.entity.UnitMasterDomain;
import com.jsfspring.curddemo.repositry.ProductRepo;
import com.jsfspring.curddemo.repositry.ProductUomRepo;
import com.jsfspring.curddemo.repositry.UnitMasterRepo;
import com.jsfspring.curddemo.utills.SukiAppUtil;

@Controller("productMBean")
@SessionScope
public class ProductMBean{
	
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public ProductRepo productRepo;
	
	@Autowired
	public ProductUomRepo productUomRepo;
	
	@Autowired
	public UnitMasterRepo unitMasterRepo;

	private static final long serialVersionUID = 1L;

//	private ProductDomain size = new ProductDomain();
	private ProductDomain product;
//	private List<ProductDomain> sizeList = null;
	private List<SelectItem> uomList = new ArrayList<SelectItem>();

	private List<String> selectedUomList = new ArrayList<String>();

	private UnitMasterDomain unitMaster;
	private List<UnitMasterDomain> unitMasterList;
	public List<ProductUom> prodUomList;
	public List<ProductDomain> selectedProductList;
	public QuotationMaster quotationMaster;
	public List<QuotationTrans> quotTransList;
	public List<QuotationMaster> quotationMasterList;

	private int tabIndex = 0;
	boolean ratePrint = false;
	double totalamount = 0;
	double cgst25 = 0;
	double cgst6 = 0;
	double cgst9 = 0;
	double cgst14 = 0;
	double sgst25 = 0;
	double sgst6 = 0;
	double sgst9 = 0;
	double sgst14 = 0;
	double totalBalance = 0;
	double totalTax = 0;
	public String buttonColor;
	public int xx;
	public int billSlNo;
	public boolean cheque;
	private double cashPayment = 0;
	private double ChequePayment = 0;
	private double NeftPayment = 0;
	private double companyTotalBal = 0;
	public boolean poInwardRender = false;
	public boolean inwardBillRender = false;
	public double nettAmountPO;
	public double totalAmountPO;
	public double gst5;
	public double gst12;
	public double gst18;
	public double gst28;
	public double purchaseBalance;
	private double supcashPayment = 0;
	private double supChequePayment = 0;
	private double supNeftPayment = 0;
	public double companyBillAmt;
	public double companyPayAmt;
	public double companyBalAmt;
	private double supplierTotalBal;

	public double salesReportBal;
	public String salesReportMonth;

	public boolean dcEdit;
	public boolean companyEdit;
	public boolean productEdit;

	public String dcNoAndName;

	String addProduct = "/jsfspring/pages/product/addProduct.xhtml";
	String addQuotation = "/jsfspring/pages/Quotation/newQuotation.xhtml";
	String uom = "/jsfspring/pages/product/unitMaster.xhtml";
	String productOverview = "/jsfspring/pages/product/product.xhtml";
	String quotationOverview = "/jsfspring/pages/Quotation/QuotationOverview.xhtml";

	public ProductMBean() {
		uomList.add(new SelectItem("-Select-"));
		uomList.add(new SelectItem("Box"));
		uomList.add(new SelectItem("Roll"));
		uomList.add(new SelectItem("Nos"));
		uomList.add(new SelectItem("Cotton"));
		uomList.add(new SelectItem("Cane"));
		uomList.add(new SelectItem("Sets"));
		uomList.add(new SelectItem("Kgs"));

		product = new ProductDomain();
		unitMaster = new UnitMasterDomain();
		// uomMasterList();
		// newQuotation();
		quotationMaster = new QuotationMaster();
		// getQuotMasterList();
		// poBillSearchList.add(new SelectItem("Status"));

		// poSearchList.add(new SelectItem("Status"));

		// getbillList();
		// getSizeDetailsList();
		selectedProductList = new ArrayList<ProductDomain>();
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
	// Product
	public void saveProduct() {
		sukiBaseBean.saveProduct(product);
	}

	public void getProductObjActionEvent(ActionEvent event) {
		product = new ProductDomain();
		product = productRepo.findById(sukiBaseBean.actionEvent(event)).get();
		sukiBaseBean.pageRedirect(addProduct);
	}

	public void getProductDelete(ActionEvent event) {
		productRepo.deleteById(sukiBaseBean.actionEvent(event));
	}

	public void delete() {
		productRepo.delete(product);
		sukiBaseBean.addMessage("Product", "Deleted Successfullly");
		sukiBaseBean.dialogHide();
		product = new ProductDomain();
	}

	public void resetProduct() {
		product = new ProductDomain();
	}
	public void newProduct() {
		resetProduct();
		sukiBaseBean.pageRedirect(addProduct);
	}

	public void productOverview() {
		sukiBaseBean.t = product;
		sukiBaseBean.overviewList();
		sukiBaseBean.pageRedirect(productOverview);
	}

	public void productOverviewListForQuotation() {
		sukiBaseBean.t = product;
		sukiBaseBean.overviewDialogList();
	}

	public void validateGst(FacesContext context, UIComponent componentToValidate, Object value)
			throws ValidatorException {
		if (product.getGst() <= 0) {
			throw new ValidatorException(sukiBaseBean.errorMessage("Enter valid Gst", "Enter valid Gst"));
		}
	}

	public void getProductUomActionEvent(ActionEvent event) {
		product.getProdUomTransList().get(sukiBaseBean.actionEvent(event)).setEditBoolean(true);
		;
	}

	public void getProductUomDelete(ActionEvent event) {
			int i=sukiBaseBean.actionEvent(event);
			ProductUom uom = product.getProdUomTransList().get(i);
			if(uom.getRowId()>0)
			productUomRepo.delete(uom);
			product.getProdUomTransList().remove(i);
	}

	public void updateProductUom(ActionEvent event) {
			int i=sukiBaseBean.actionEvent(event);
			ProductUom uom = product.getProdUomTransList().get(i);
			if(uom.getUomId()==null) {
				sukiBaseBean.addMessage(sukiBaseBean.errorMessage("Product", "Enter valid data in table"));
				return ;
			}
			if (uom.getRowId() > 0) {
				sukiBaseBean.addMessage("Product Uom", "Updated Successfullly");
			} else {
				sukiBaseBean.addMessage("Product Uom", "Saved Successfullly");
			}
			uom=productUomRepo.save(uom);
			uom.setEditBoolean(false);
			product.getProdUomTransList().set(i, uom);
	}

	// UnitMaster
	public void newUnitMaster() {
		unitMaster = new UnitMasterDomain();
	}

	public void unitMasterOverview() {
		sukiBaseBean.t = unitMaster;
		sukiBaseBean.overviewList();
		sukiBaseBean.pageRedirect(uom);
	}

	public void saveUnitMaster() {
			boolean flag = false;
			if (sukiBaseBean.validateString(unitMaster.getUnitName(), "Unit Name"))
				flag = true;
			if (flag)
				return;
			if (unitMaster.getRowId() > 0) {
				sukiBaseBean.addMessage("Unit Master", "Update Successfullly");
			}else {
				sukiBaseBean.addMessage("Unit Master", "Saved Successfullly");
			}
			unitMaster=unitMasterRepo.save(unitMaster);
	}

	public void getUOMObjActionEvent(ActionEvent event) {
		unitMaster = new UnitMasterDomain();
		unitMaster = unitMasterRepo.findById(sukiBaseBean.actionEvent(event)).get();
	}

	public void getUOMDelete() {
			if (unitMaster != null) {
				unitMasterRepo.delete(unitMaster);
				sukiBaseBean.addMessage("Unit Master", "Deleted Successfullly");
			    unitMaster = new UnitMasterDomain();
			}
	}

//	public String getQuotationActionEvent(ActionEvent event) {
//		try {
//			quotationMaster = (QuotationMaster) CommonAPI.getInstance().edit(t, actionEvent(event));
//			/*
//			 * for(int i=0;i<quotationMaster.getQuotTransList().size();i++) {
//			 * System.out.println(quotationMaster.getQuotTransList().get(i).getQuotUomList()
//			 * .get(0).getUomName()+" "+quotationMaster.getQuotTransList().get(i).
//			 * getQuotUomList().get(0).getPrice());
//			 * System.out.println(quotationMaster.getQuotTransList().get(i).getQuotUomList()
//			 * .get(1).getUomName()+" "+quotationMaster.getQuotTransList().get(i).
//			 * getQuotUomList().get(1).getPrice()); }
//			 */
//			pageRedirect(addQuotation);
//		} catch (SukiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	public String getQuotationDeleteActionEvent(ActionEvent event) {
//		try {
//			CommonAPI.getInstance().delete(t, actionEvent(event));
//		} catch (SukiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "";
//	}
//
//	public void printQuotation(ActionEvent event) {
//		try {
//			quotationMaster = (QuotationMaster) CommonAPI.getInstance().edit(t, actionEvent(event));
//			quotationPdfprint(quotationMaster);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	public void print() {
//		quotationPdfprint(quotationMaster);
//	}
//
//	public void quotationPdfprint(QuotationMaster quotationMaster) {
//		try {
//			PdfDocuments.createQuotation(quotationMaster);
//			String file = SukiAppConstants.QUOTATION_FOLDER + quotationMaster.getQuotationNo() + ".pdf";
//			File pdfFile = new File(file);
//			if (pdfFile.exists()) {
//				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
//				p.waitFor();
//			} else {
//				System.out.println("File is not exists");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
	public void addNewUom() {
		ProductUom uom = new ProductUom();
		product.addUomTrans(uom);
		System.out.println("MBEAN LIST SIZE---" + product.getProdUomTransList().size());
	}
	
	public boolean productSearch() {
		try {

			if (!SukiAppUtil.isEmpty(product.getItem())) {
				if (!productEdit) {
					List<ProductDomain> code = productRepo.findByItemContaining(product.getItem());
					if (code.size()>0) {
						sukiBaseBean.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product",
								product.getItem() + " already present"));
						return true;
					}
				}
			} else {
				sukiBaseBean.addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Product", "Product Name not to be Empty"));
				return true;
			}
		} catch (Exception e) {

		}
		return false;
	}
//
//	public void newQuotation() {
//		quotationMaster = new QuotationMaster();
//		try {
//			quotationMaster
//					.setQuotationNo(SukiServiceAPI.getInstance().getAutoNumber("quotationNo", "QuotationMaster"));
//		} catch (SukiException e) {
//			e.printStackTrace();
//		}
//		pageRedirect(addQuotation);
//	}
//
//	public void getQuotList() {
//		quotationMaster.setQuotTransList(new ArrayList<QuotationTrans>());
//		if(selectedProductList!=null && selectedProductList.size()>0) {
//		selectedProductList.parallelStream().forEach(i -> {
//			QuotationTrans trans = new QuotationTrans();
//			List<QuotationProductUom> quotUomList = new ArrayList<QuotationProductUom>();
//			trans.setProductId(i);
//			trans.setItem(i.getItem());
//			i.getProdUomTransList().parallelStream().forEach(j -> {
//				QuotationProductUom uomTrans = new QuotationProductUom();
//				uomTrans.setUomId(j.getUomId());
//				uomTrans.setPrice(j.getPrice());
//				trans.addTrans(uomTrans);
//			});
//			quotationMaster.addTrans(trans);
//		});
//		dialogHide("dlg1");
//		}else {
//			errorMessage("Quotation", "Select Products to add");
//			return;
//		}
//	}
//
//	public void quotationSave() {
//		try {
//			if(validateList(quotationMaster.getQuotTransList().size(), "Quotation"))
//				return;
//			SukiServiceAPI.getInstance().QuotationSave(quotationMaster);
//			addMessage("Quotation","Saved successfully");
//		} catch (SukiException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

//	private LazyDataModel<T> model1;
//
//	@PostConstruct
//	public void init() {
//		try {
//			this.model1 = new LazyDataModel<T>() {
//				private static final long serialVersionUID = 1L;
//
//				@Override
//				public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
//						Map<String, Object> filters) {
//					try {
//						T t = (T) product;
//						overview = CommonAPI.getInstance().getAll(t, first, pageSize, sortField,
//								sortOrder.toString(), filters);
//						if(overview!=null)
//							overViewList=overview.getOverviewList();
//					} catch (SukiException e) {
//						e.printStackTrace();
//					}
//					return overViewList;
//				}
//
//				@Override
//				public T getRowData(String rowKey) {
//					int intRowKey = Integer.parseInt(rowKey);
//					for (Object event : overViewList) {
//						ProductDomain p = (ProductDomain) event;
//						System.out.println("ROWKEY" + intRowKey + p.getClass().getName() + p.getProdCode());
//						if (p.getClass().getName().contains("ProductDomain") && p.getProdCode() == intRowKey) {// Error
//																												// here
//							return (T) p;
//						}
//					}
//					return null;
//				}
//
//				@Override
//				public Object getRowKey(T t) {
//					if (t.getClass().getName().equals("ProductDomain")) {
//						ProductDomain p = (ProductDomain) t;
//						return p.getProdCode();
//					}
//					return null;
//				}
//			};
//			model1.setRowCount(30);
//		} catch (Exception e) {
//			System.out.println("Exception in CityListProducer " + e);
//		}
//
//	}

//	public String getQuotItemActionEvent(ActionEvent event) {
//		quotationMaster = quotationMaster.getQuotTransList().get(actionEvent(event));
//		pageRedirect(addQuotation);
//		return "";
//	}
//
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

	public String createUser() {
		return "billPrint";
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public ProductDomain getProduct() {
		return product;
	}

	public void setProduct(ProductDomain product) {
		this.product = product;
	}

	public boolean isRatePrint() {
		return ratePrint;
	}

	public void setRatePrint(boolean ratePrint) {
		this.ratePrint = ratePrint;
	}

	public String isButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(String buttonColor) {
		this.buttonColor = buttonColor;
	}

	public double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public boolean isCheque() {
		return cheque;
	}

	public void setCheque(boolean cheque) {
		this.cheque = cheque;
	}

	public double getCashPayment() {
		return cashPayment;
	}

	public void setCashPayment(double cashPayment) {
		this.cashPayment = cashPayment;
	}

	public double getChequePayment() {
		return ChequePayment;
	}

	public void setChequePayment(double chequePayment) {
		ChequePayment = chequePayment;
	}

	public double getNeftPayment() {
		return NeftPayment;
	}

	public void setNeftPayment(double neftPayment) {
		NeftPayment = neftPayment;
	}

	
	public List<SelectItem> getUomList() {
		return uomList;
	}

	public void setUomList(List<SelectItem> uomList) {
		this.uomList = uomList;
	}

	public List<String> getSelectedUomList() {
		return selectedUomList;
	}

	public void setSelectedUomList(List<String> selectedUomList) {
		this.selectedUomList = selectedUomList;
	}

	public UnitMasterDomain getUnitMaster() {
		return unitMaster;
	}

	public void setUnitMaster(UnitMasterDomain unitMaster) {
		this.unitMaster = unitMaster;
	}

	public List<UnitMasterDomain> getUnitMasterList() {
		return unitMasterList;
	}

	public void setUnitMasterList(List<UnitMasterDomain> unitMasterList) {
		this.unitMasterList = unitMasterList;
	}

	public List<ProductUom> getProdUomList() {
		return prodUomList;
	}

	public void setProdUomList(List<ProductUom> prodUomList) {
		this.prodUomList = prodUomList;
	}

	public List<ProductDomain> getSelectedProductList() {
		return selectedProductList;
	}

	public void setSelectedProductList(List<ProductDomain> selectedProductList) {
		this.selectedProductList = selectedProductList;
	}

	public List<QuotationTrans> getQuotTransList() {
		return quotTransList;
	}

	public void setQuotTransList(List<QuotationTrans> quotTransList) {
		this.quotTransList = quotTransList;
	}

	public QuotationMaster getQuotationMaster() {
		return quotationMaster;
	}

	public void setQuotationMaster(QuotationMaster quotationMaster) {
		this.quotationMaster = quotationMaster;
	}

	public List<QuotationMaster> getQuotationMasterList() {
		return quotationMasterList;
	}

	public void setQuotationMasterList(List<QuotationMaster> quotationMasterList) {
		this.quotationMasterList = quotationMasterList;
	}

	public LazyDataModel getModel() {
		return sukiBaseBean.model;
	}
}