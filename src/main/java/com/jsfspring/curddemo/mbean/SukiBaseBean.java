package com.jsfspring.curddemo.mbean;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.OverviewListAndCount;
import com.jsfspring.curddemo.entity.ProductDomain;
import com.jsfspring.curddemo.entity.SupplierDomain;
import com.jsfspring.curddemo.entity.UnitMasterDomain;
import com.jsfspring.curddemo.repositry.CompanyRepo;
import com.jsfspring.curddemo.repositry.ProductRepo;
import com.jsfspring.curddemo.repositry.SupplierRepo;
import com.jsfspring.curddemo.repositry.UnitMasterRepo;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

@Service
public  class SukiBaseBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public LazyDataModel<T> model;
	public LazyDataModel<T> model1;
	public List<T> overViewList;
	public List<T> overViewList1;
	T t;
	ProductDomain productId;
	int totalRowCount;
	OverviewListAndCount overview;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public CompanyRepo companyRepo;
	
	@Autowired
	public SupplierRepo supplierRepo;
	
	@Autowired
	public UnitMasterRepo unitMasterRepo;
	
	@Autowired
	public ProductRepo productRepo;

	public SukiBaseBean() {
		overViewList = new ArrayList<T>();
		overViewList1 = new ArrayList<T>();
	}

	public void overviewDialogList() {
		model1 = new LazyDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
					overview = commonObjects.getAll(t, first, pageSize, sortField, sortOrder.toString(),
							filters);
					if(overview!=null)
						overViewList=overview.getOverviewList();
				
				return overViewList;
			}
			public Object getRowKey(ProductDomain product) {
				return product != null ? product : null;
			}
		};
			overview = commonObjects.getAll(t, 0, 1, null, null,
					null);
		
		model1.setRowCount(overview.getCount());
		System.out.println("RowCount---"+model1.getRowCount());

	}

	public void overviewList() {
		System.out.println("object---" + t);
		model = new LazyDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
					System.out.println("object---" + filters);
					 overview = commonObjects.getAll(t, first, pageSize, sortField, sortOrder.toString(),
							filters);
					if(overview!=null)
						overViewList=overview.getOverviewList();
					System.out.println("Size---" + overViewList.size());
				
				return overViewList;
			}
		};
			overview = commonObjects.getAll(t, 0, 1, null, null,
					null);
		
		model.setRowCount(overview.getCount());
		System.out.println("RowCount   2---"+model.getRowCount());
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	public FacesMessage errorMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
		return message;
	}
	public boolean validateString(String string,String fieldName) {
		if(SukiAppUtil.isEmpty(string)) {
			addMessage(errorMessage(fieldName,"Enter "+ fieldName));
			return true;
		}
		return false;
	}
	public boolean validateInteger(double number,String fieldName) {
		if(number<=0) {
			addMessage(errorMessage(fieldName,"Enter "+ fieldName));
			return true;
		}
		return false;
	}
	public boolean validateList(int listSize,String fieldName) {
		if(listSize<=0) {
			addMessage(errorMessage(fieldName,"List should not be Empty"));
			return true;
		}
		return false;
	}
//	public boolean containsName(final List<Object> list, final String name){
//	    return list.stream().filter(o -> o.).findFirst().isPresent();
//	}

	public void dialogShow(int id) {
		if(id>0)
		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('confirmDialog').show()");
	}
	public void dialogHide() {
		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('confirmDialog').hide()");
	}
	public void dialogShow(String dialogName) {
		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('"+dialogName+"').show()");
	}
	public void dialogHide(String dialogName) {
		org.primefaces.context.RequestContext.getCurrentInstance().execute("PF('"+dialogName+"').hide()");
	}
	public boolean buttonDisable(int id) {
		if(id > 0)
			return false;
		else 
			return true;
	}
	public void pageRedirect(String pageName) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(pageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int actionEvent(ActionEvent event) {
		HtmlInputHidden component = (HtmlInputHidden) event.getComponent().findComponent("slNo");
		int i = (int) component.getValue();
		return i;
	}

	public int ajaxEvent(AjaxBehaviorEvent event) {
		HtmlInputHidden component = (HtmlInputHidden) event.getComponent().findComponent("slNo");
		int i = (int) component.getValue();
		return i;
	}

	public int selectEvent(SelectEvent event) {
		HtmlInputHidden component = (HtmlInputHidden) event.getComponent().findComponent("slNo");
		System.out.println("component.getValue()"+component.getValue());
		int i = Integer.parseInt(component.getValue().toString());
		return i;
	}

	public List<SupplierDomain> supplierListAutoComplete(String query) {
		return  supplierRepo.findByNameContaining(query);
	}

	public List<ProductDomain> productListAutoComplete(String query) {
		return productRepo.findByItemContaining(query);
	}
//
	public List<Company> companyListAutoComplete(String query) {
			return  companyRepo.findByCompNameContaining(query);
		}
//
	public List<UnitMasterDomain> uomListAutoComplete(String query) {
		return unitMasterRepo.findByUnitNameContaining(query);
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


	public List<SelectItem> getSelectIems(List<String> listStr) {
		List<SelectItem> selectItemsList = new ArrayList<SelectItem>();
		if (listStr != null) {
			;
			for (int i = 0; i < listStr.size(); i++) {
				selectItemsList.add(new SelectItem(listStr.get(i)));
			}
		}
		return selectItemsList;
	}


	

	
}
