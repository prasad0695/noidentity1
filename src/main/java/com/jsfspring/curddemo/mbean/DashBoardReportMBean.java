package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jsfspring.curddemo.entity.*;
import com.jsfspring.curddemo.repositry.BillMasterRepo;
import com.jsfspring.curddemo.repositry.PurchaseBillMasterRepo;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;

import javax.faces.event.ActionEvent;


@Controller("dashBoardReportMBean")
@SessionScope
public class DashBoardReportMBean<T>{
	
	@Autowired
	public SukiBaseBean sukiBaseBean;
	
	@Autowired
	public CommonObjects commonObjects;
	
	@Autowired
	public ReportService reportService;
	
	
	List<CompanyWiseSalesDomain> companySalesList= null;
	List<BillMasterDomain> invoiceList = null;

	List<PurchaseBillMaster> purchaseBillList = null;
	public List<T> outstandingSalesList= null;
	private BarChartModel barModel;
	public double maxPercentage=0;
	public double totalSum=0;
	public String currentMonth;
	public int currentYear;
	public Company companyId;
	public SupplierDomain supplierId;
	public String salesOrPurchase;
	public List<ProductUnitWise> productUnitWiseReport;
	public Map<Integer,List<GstDomain>> gstMap;
	public Map<Integer,List<GstDomain>> purchaseGstMap;
	public GstReportDomain gstReportDomain;

	@Autowired
	public BillMasterRepo billMasterRepo;

	@Autowired
	public PurchaseBillMasterRepo purchaseBillMasterRepo;
	public boolean productBySales;
	int totalRowCount = 0;
	DashBoardReportDomain dashboardReport=new DashBoardReportDomain();

	
	public CommonReportDomain commonReport;
	
	String dashboard="/jsfspring/dashboard.xhtml";
	String sales="/jsfspring/pages/DashboardReports/companyWiseSales.xhtml";
	String salesOutstanding="/jsfspring/pages/DashboardReports/outstandingSalesAmt.xhtml";
	String purchaseOutstanding="/jsfspring/pages/DashboardReports/outstandingPurchaseAmt.xhtml";
	String commonReportPage="/jsfspring/pages/DashboardReports/commonReport.xhtml";
	String gstReport="/jsfspring/pages/DashboardReports/gstReports.xhtml";
	
	public  DashBoardReportMBean() {
		companySalesList= new ArrayList<CompanyWiseSalesDomain>();
		outstandingSalesList=new ArrayList<T>();
		commonReport=new CommonReportDomain();
		productUnitWiseReport=new ArrayList<ProductUnitWise>();
	    currentYear=SukiAppUtil.getCurrentYear();
	    currentMonth=SukiAppUtil.getPreviousMonth();
	}
	public void onCurrentMonthChange() {
		
	}
    public List<CompanyWiseSalesDomain> onCurrentYearChange() {
    	companySalesList= new ArrayList<CompanyWiseSalesDomain>();
    	companySalesList=getCommonReport(SukiAppUtil.getMonthOfYear(currentMonth),currentYear,salesOrPurchase);
    	createBarModels();
    	return companySalesList;
	}
	public List<CompanyWiseSalesDomain> getCompanyWiseSales() {
		    currentYear=SukiAppUtil.getCurrentYear();
		    currentMonth=SukiAppUtil.getPreviousMonth();
		    companySalesList= new ArrayList<CompanyWiseSalesDomain>();
		    companySalesList=getCommonReport(SukiAppUtil.getMonthOfYear(SukiAppUtil.getPreviousMonth()),SukiAppUtil.getCurrentYear(),salesOrPurchase);
		    sukiBaseBean.pageRedirect(sales);
		return companySalesList;
	}
	public void dashboard() {
		currentYear=SukiAppUtil.getCurrentYear();
		salesOrPurchase=SukiAppConstants.SALES;
		createBarModel();
		sukiBaseBean.pageRedirect(dashboard);
	}
	public void sales() {
		salesOrPurchase=SukiAppConstants.SALES;
		supplierId=null;
		companyId=null;
		getCompanyWiseSales();
		createBarModel();
	}
	public void purchase() {
		salesOrPurchase=SukiAppConstants.PURCHASE;
		companyId=null;
		supplierId=null;
		getCompanyWiseSales();
		createBarModel();
	}
	public void salesPayment() {
		salesOrPurchase=SukiAppConstants.SALES_PAYMENT;
		supplierId=null;
		companyId=null;
		getCompanyWiseSales();
		createBarModel();
	}
	public void purchasePayment() {
		salesOrPurchase=SukiAppConstants.PURCHASE_PAYMENT;
		companyId=null;
		supplierId=null;
		getCompanyWiseSales();
		createBarModel();
	}
	public void productSales() {
		salesOrPurchase=SukiAppConstants.PRODUCT_SALES;
		supplierId=null;
		companyId=null;
		getCompanyWiseSales();
		createBarModel();
		productBySales=false;
	}
	public void productPurchase() {
		salesOrPurchase=SukiAppConstants.PRODUCT_PURCHASE;
		companyId=null;
		supplierId=null;
		getCompanyWiseSales();
		createBarModel();
		productBySales=false;
	}
	public List<CompanyWiseSalesDomain> getCommonReport(int month,int year,String salesOrPurchase){
		try {
		List<Object[]> list=reportService.getCommonReport(month,year,salesOrPurchase);
		companySalesList=getTotalSalesAndPurchase(list);
		System.out.println("companySalesList---"+companySalesList);
		System.out.println(month+"---------"+year);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companySalesList;
	}
	public List<CompanyWiseSalesDomain> getTotalSalesAndPurchase(List<Object[]> list){
		try {
		CompanyWiseSalesDomain temp=null;
		companySalesList= new ArrayList<CompanyWiseSalesDomain>();
		for(int i=0;i<list.size();i++) {
			temp=new CompanyWiseSalesDomain();
			temp.setCompanyId((int) list.get(i)[1]);
			temp.setCompanyName((String) list.get(i)[2]);
			temp.setTotalSales(Double.parseDouble(list.get(i)[3].toString()));
			companySalesList.add(temp);
		}
		double max=companySalesList.parallelStream().collect(Collectors.summarizingDouble(i->i.getTotalSales())).getMax();
		double sum=companySalesList.parallelStream().mapToDouble(i->i.getTotalSales()).sum();
		totalSum=sum;
		double maxPercentage=(max/sum)*100;
		companySalesList.forEach(i->{i.setPercentage((i.getTotalSales()/sum)*100);i.setWidthPercentage((i.getPercentage()/maxPercentage)*100);});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return companySalesList;
	}
	public void outStandingSalesAmt() {
		try {
			sukiBaseBean.t=new SalesOutstanding();
			outstandingSalesList=(List<T>) reportService.outstandingAmt(sukiBaseBean.t);
			double max=outstandingSalesList.parallelStream().collect(Collectors.summarizingDouble(i->((SalesOutstanding)i).getOutStandingAmt())).getMax();
			totalSum=outstandingSalesList.parallelStream().mapToDouble(i->((SalesOutstanding) i).getOutStandingAmt()).sum();
			maxPercentage=(max/totalSum)*100;
		//	outstandingSalesList.forEach(i->{i.setPercentage((i.getTotalSales()/sum)*100);i.setWidthPercentage((i.getPercentage()/maxPercentage)*100);});
			System.out.println("List Size---"+outstandingSalesList.size());
			sukiBaseBean.pageRedirect(salesOutstanding);
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void outStandingPurchaseAmt() {
		try {
			sukiBaseBean.t=new PurchaseOutstanding();
			outstandingSalesList=(List<T>) reportService.outstandingAmt(sukiBaseBean.t);
			double max=outstandingSalesList.parallelStream().collect(Collectors.summarizingDouble(i->((PurchaseOutstanding)i).getOutStandingAmt())).getMax();
			totalSum=outstandingSalesList.parallelStream().mapToDouble(i->((PurchaseOutstanding) i).getOutStandingAmt()).sum();
			maxPercentage=(max/totalSum)*100;
		//	outstandingSalesList.forEach(i->{i.setPercentage((i.getTotalSales()/sum)*100);i.setWidthPercentage((i.getPercentage()/maxPercentage)*100);});
			System.out.println("List Size---"+outstandingSalesList.size());
			sukiBaseBean.pageRedirect(purchaseOutstanding);
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getPendingBillForCompanyId(ActionEvent event) {
		invoiceList = new ArrayList<BillMasterDomain>();
		invoiceList = billMasterRepo.findInvByPendingStatus(sukiBaseBean.actionEvent(event));
		sukiBaseBean.dialogShow("dlg1");
	}

	public void getPendingBillForSupplierId(ActionEvent event) {
		purchaseBillList = new ArrayList<PurchaseBillMaster>();
		purchaseBillList = purchaseBillMasterRepo.findInvByPendingStatus(sukiBaseBean.actionEvent(event));
		sukiBaseBean.dialogShow("dlg1");
	}

    public void onCompanySelect() {
    	createBarModel();
    	companySalesList= new ArrayList<CompanyWiseSalesDomain>();
    }
    private void createBarModels() {
        createBarModel();
       // createHorizontalBarModel();
    }
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        Map<String,Double> salesByMonth=new HashMap<String,Double>();
		try {
			int compId=0;
			if(companyId!=null) 
				compId = companyId.getCompId();
			if(supplierId!=null) 
				compId = supplierId.getSupCode();
			dashboardReport=reportService.dashBoardReport(currentYear,compId,salesOrPurchase);
			if(dashboardReport!=null) 
				salesByMonth=dashboardReport.getSalesByMonth();
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        for (Map.Entry<String, Double> entry : salesByMonth.entrySet()) {
        boys.set(entry.getKey(),entry.getValue());
        }
        model.addSeries(boys);
        
        model.setExtender("skinChart");
        return model;
    }
    private void createBarModel() {
        barModel = initBarModel();
        barModel.setTitle("Bar Chart");
    }
    public void overviewList(Map<String, Object> specificFilters) {
		sukiBaseBean.model = new LazyDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
					System.out.println("object---" + filters);
					filters.putAll(specificFilters);
					OverviewListAndCount overview = commonObjects.getAll(sukiBaseBean.t, first, pageSize, sortField, sortOrder.toString(),
							filters);
					if(overview!=null)
						sukiBaseBean.overViewList=overview.getOverviewList();
				return sukiBaseBean.overViewList;
			}
		};
		try {
			totalRowCount = commonObjects.getCountQuery(sukiBaseBean.t,specificFilters);
			System.out.println("count---" + totalRowCount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sukiBaseBean.model.setRowCount(totalRowCount);
	}
    public List<String> getMonthList(){
    	List<String> list =SukiAppUtil.getMonthList();
    	return list;
    }
    public List<Integer> getYearList(){
    	List<Integer> list =SukiAppUtil.getYearList();
    	return list;
    }
    public String currentMonth() {
    	return SukiAppUtil.getMonthOfYear(SukiAppUtil.getMonthOfYear(SukiAppUtil.getCurrentMonth())-1);
    }
    public int currentYear() {
    	if(SukiAppUtil.getMonthOfYear(SukiAppUtil.getCurrentMonth())==1)
    	return SukiAppUtil.getCurrentYear()-1;
    	else
    	return SukiAppUtil.getCurrentYear();
    }
    public void OnSelect() {
    	Map<String,Object> map=new HashMap<String,Object>();
    	if(commonReport.getType()!=null) {
    	if(commonReport.getType().equalsIgnoreCase("Company")) {
    		sukiBaseBean.t=new VWCompany();
    		commonReport.getCompany();
    		map.put("id", commonReport.getCompany().getCompId());
    	//	map.put("type", commonReport.getCompany().getCompId());
    		overviewList(map);
    	}else if(commonReport.getType().equalsIgnoreCase("Supplier")) {
    		sukiBaseBean.t=new VWSupplier();
    		map.put("id", commonReport.getSupplier().getSupCode());
    	//	map.put("type", commonReport.getCompany().getCompId());
    		overviewList(map);
    	}else if(commonReport.getType().equalsIgnoreCase("Product")) {
    		sukiBaseBean.t=new VWProduct();
    		map.put("items", commonReport.getProduct().getProdCode());
    	//	map.put("type", commonReport.getCompany().getCompId());
    		overviewList(map);
    	}}else {
    		getProductWiseSales();
    	}
    }
    public void getProductWiseSales() {
						productUnitWiseReport = commonObjects.getUnitWiseProductInwardOutward(SukiAppUtil.getMonthOfYear(currentMonth),currentYear,"SALES INVOICE");	
						System.out.println("Size---" + productUnitWiseReport.size());
						productBySales=true;
    }
    public void getProductWisePurchase() {
			productUnitWiseReport = commonObjects.getUnitWiseProductInwardOutward(SukiAppUtil.getMonthOfYear(currentMonth),currentYear,"PURCHASE INVOICE");	
			System.out.println("Size---" + productUnitWiseReport.size());
			productBySales=true;
    }
    public void getGstReport() {
    	List<GstDomain> gstList;
    	gstReportDomain=new GstReportDomain();
		try {
			Map<String, Object> filters=new HashMap<>();
			filters.put("year(date)", currentYear);
			filters.put("month(date)", SukiAppUtil.getMonthOfYear(currentMonth));
			filters.put("gstBill", 1);
			PurchaseOverviewList(filters);
			sukiBaseBean.t=new BillMasterDomain();
			overviewList(filters);
			gstReportDomain = reportService.getGstReport(SukiAppUtil.getMonthOfYear(currentMonth), currentYear);
			if(gstReportDomain!=null)
			gstMap=gstReportDomain.getGstReportList().parallelStream().collect(Collectors.groupingBy(GstDomain::getBillNo, Collectors.toList()));
			purchaseGstMap=gstReportDomain.getPurchaseGstReportList().parallelStream().collect(Collectors.groupingBy(GstDomain::getBillNo, Collectors.toList()));
			System.out.println("gstMap---"+gstMap);
			
			sukiBaseBean.pageRedirect(gstReport);
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	 // map.keySet();
    }
    public void PurchaseOverviewList(Map<String, Object> filter) {
		PurchaseBillMaster purchaseBill=new PurchaseBillMaster();
		sukiBaseBean.model1 = new LazyDataModel<T>() {
			@Override
			public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
					filters=filter;
					OverviewListAndCount overview  = commonObjects.getAll(purchaseBill, first, pageSize, sortField, sortOrder.toString(),
							filters);
					if(overview!=null)
						sukiBaseBean.overViewList1=overview.getOverviewList();
				return sukiBaseBean.overViewList1;
			}
		};
		try {
			totalRowCount = commonObjects.getCountQuery(purchaseBill,filter);
		} catch (SukiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sukiBaseBean.model1.setRowCount(totalRowCount);
	}
    
    public GstDomain getBillDetails(int billNo) {
    	return gstMap.get(billNo).get(0);
    }
    
    public double getGstValue(int billNo,double gstPercentage) {
    	System.out.println("billNo"+billNo);
    	System.out.println("gstMap"+gstMap);
    	List<GstDomain> gstList=gstMap.get(billNo);
    	System.out.println("gstList"+gstList);
    	for(int i=0;i<gstList.size();i++) {
    		if(gstList.get(i).getGstPercentage()==gstPercentage) {
    			return gstList.get(i).getGstAmount();
    		}
    	}
    	return 0;
    }
    public double getPurchaseGstValue(int billNo,long gstPercentage) {
    	List<GstDomain> gstList=purchaseGstMap.get(billNo);
    	System.out.println("purchasegstList"+gstList.size());
    	for(int i=0;i<gstList.size();i++) {
    		if(gstList.get(i).getGstPercentage()==gstPercentage) {
    			return gstList.get(i).getGstAmount();
    		}
    	}
    	return 0;
    }
    public double getTotalGstAmount(int billNo) {
    	List<GstDomain> gstList=gstMap.get(billNo);
    	double gstAmount=gstList.parallelStream().mapToDouble(i->i.getGstAmount()).sum();
    	return gstAmount;
    }
    public void commonReportRoute() {
    	sukiBaseBean.pageRedirect(commonReportPage);
    }
	public List<CompanyWiseSalesDomain> getCompanySalesList() {
		return companySalesList;
	}

	public List<BillMasterDomain> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<BillMasterDomain> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public void setCompanySalesList(List<CompanyWiseSalesDomain> companySalesList) {
		this.companySalesList = companySalesList;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	
	public double getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(double totalSum) {
		this.totalSum = totalSum;
	}

	public double getMaxPercentage() {
		return maxPercentage;
	}

	public void setMaxPercentage(double maxPercentage) {
		this.maxPercentage = maxPercentage;
	}

	public List<T> getOutstandingSalesList() {
		return outstandingSalesList;
	}

	public void setOutstandingSalesList(List<T> outstandingSalesList) {
		this.outstandingSalesList = outstandingSalesList;
	}

	public String getCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(String currentMonth) {
		this.currentMonth = currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	public Company getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}
	public String getSalesOrPurchase() {
		return salesOrPurchase;
	}
	public void setSalesOrPurchase(String salesOrPurchase) {
		this.salesOrPurchase = salesOrPurchase;
	}
	public SupplierDomain getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(SupplierDomain supplierId) {
		this.supplierId = supplierId;
	}
	public CommonReportDomain getCommonReport() {
		return commonReport;
	}
	public void setCommonReport(CommonReportDomain commonReport) {
		this.commonReport = commonReport;
	}
	public LazyDataModel getModel() {
		return sukiBaseBean.model;
		}
	public LazyDataModel getModel1() {
		return sukiBaseBean.model1;
		}
	public List<T> getOverViewList(){
		return sukiBaseBean.overViewList;
	}
	public List<ProductUnitWise> getProductUnitWiseReport() {
		return productUnitWiseReport;
	}
	public void setProductUnitWiseReport(List<ProductUnitWise> productUnitWiseReport) {
		this.productUnitWiseReport = productUnitWiseReport;
	}
	public GstReportDomain getGstReportDomain() {
		return gstReportDomain;
	}
	public void setGstReportDomain(GstReportDomain gstReportDomain) {
		this.gstReportDomain = gstReportDomain;
	}
	public boolean isProductBySales() {
		return productBySales;
	}
	public void setProductBySales(boolean productBySales) {
		this.productBySales = productBySales;
	}
	public DashBoardReportDomain getDashboardReport() {
		return dashboardReport;
	}
	public void setDashboardReport(DashBoardReportDomain dashboardReport) {
		this.dashboardReport = dashboardReport;
	}

	public List<PurchaseBillMaster> getPurchaseBillList() {
		return purchaseBillList;
	}

	public void setPurchaseBillList(List<PurchaseBillMaster> purchaseBillList) {
		this.purchaseBillList = purchaseBillList;
	}
}
