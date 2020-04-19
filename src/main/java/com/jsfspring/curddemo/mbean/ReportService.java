package com.jsfspring.curddemo.mbean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.jsfspring.curddemo.entity.DashBoardReportDomain;
import com.jsfspring.curddemo.entity.GstDomain;
import com.jsfspring.curddemo.entity.GstReportDomain;
import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;
import com.jsfspring.curddemo.utills.SukiException;


@Service
public class ReportService {

	
	@PersistenceContext
    private EntityManager entityManager;

	public List<Object[]> getCommonReport(int month, int year,String type) throws SukiException {
		String sql = null;
		if(type.equalsIgnoreCase(SukiAppConstants.SALES))
			sql=SukiAppConstants.COMPANY_SALES;
		if(type.equalsIgnoreCase(SukiAppConstants.PURCHASE))
			sql=SukiAppConstants.SUPPLIER_PURCHASE;
		if(type.equalsIgnoreCase(SukiAppConstants.SALES_PAYMENT))
			sql=SukiAppConstants.COMPANY_PAYMENT;
		if(type.equalsIgnoreCase(SukiAppConstants.PURCHASE_PAYMENT))
			sql=SukiAppConstants.SUPPLIER_PAYMENT;
		if(type.equalsIgnoreCase(SukiAppConstants.PRODUCT_SALES))
			sql=sql.format(SukiAppConstants.PRODUCT_WISE_SALES_PURCHASE, "SALES INVOICE");
		if(type.equalsIgnoreCase(SukiAppConstants.PRODUCT_PURCHASE))
			sql=sql.format(SukiAppConstants.PRODUCT_WISE_SALES_PURCHASE, "PURCHASE INVOICE");
		System.out.println("sql---"+sql);
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, month);
		query.setParameter(2, year);
		List<Object[]> list = query.getResultList();
		System.out.println("list------------"+list);
		return list;
	}

	public <T> List<T> outstandingAmt(T ObjectType) throws SukiException {
		StringBuffer sql = new StringBuffer();
		sql.append(String.format("from %s order By outStandingAmt desc", ObjectType.getClass().getName()));
		System.out.println("sql"+sql);
		Query query = entityManager.createQuery(sql.toString());
		List<T> list = query.getResultList();
		System.out.println("list"+list.size());
		return list;
	}
	public <T> DashBoardReportDomain dashBoardReport(int Year, int id,String type) throws SukiException {
		DashBoardReportDomain dashBoardReport=new DashBoardReportDomain();
		dashBoardReport.setSalesByMonth(monthWiseReportForYear(Year,id,type));
//		StringBuffer sql = new StringBuffer();
		Query query = entityManager.createQuery("select sum(totalAmount-paidAmt) from PurchaseBillMaster");
		double purchasePending=0;
		double salesPending=0;
		try {
			purchasePending=(double) query.getSingleResult();
		}catch(Exception e) {
			purchasePending=0;
		}
		dashBoardReport.setPurchasePending(purchasePending);
		query = entityManager.createQuery("select sum(totalAmount-paidAmt) from BillMasterDomain");
		try {
			salesPending=(double) query.getSingleResult();
		}catch(Exception e) {
			salesPending=0;
		}
		dashBoardReport.setSalesPending(salesPending);
		return dashBoardReport;
	}
	public Map<String, Double> monthWiseReportForYear(int Year, int id, String type) throws SukiException{
		Double value = (double) 0;
		String sqlQuery = null;
		Map<String, Double> map = new LinkedHashMap<String, Double>();
		for (int i = 1; i <= 12; i++) {
			if(type.equalsIgnoreCase(SukiAppConstants.SALES)) {
				if(id>0)
				sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT_WITHID,"BillMasterDomain","companyId.compId");
				else
				sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT,"BillMasterDomain");
			}else if(type.equalsIgnoreCase(SukiAppConstants.PURCHASE)) {
				if(id>0)
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT_WITHID,"PurchaseBillMaster","supId.supCode");
					else
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT,"PurchaseBillMaster");
			}else if(type.equalsIgnoreCase(SukiAppConstants.SALES_PAYMENT)) {
				if(id>0)
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT_WITHID,"SalesPaymentsDomain","companyId.compId");
					else
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT,"SalesPaymentsDomain");
			}else if(type.equalsIgnoreCase(SukiAppConstants.PURCHASE_PAYMENT)) {
				if(id>0)
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT_WITHID,"PurchasePaymentsDomain","supId.supCode");
					else
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_REPORT,"PurchasePaymentsDomain");
			}
			else if(type.equalsIgnoreCase(SukiAppConstants.PRODUCT_SALES)) {
				if(id>0)
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_PRODUCT_REPORT_WITHID,"SALES INVOICE");
					else
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_PRODUCT_REPORT,"SALES INVOICE");
			}
			else if(type.equalsIgnoreCase(SukiAppConstants.PRODUCT_PURCHASE)) {
				if(id>0)
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_PRODUCT_REPORT_WITHID,"PURCHASE INVOICE");
					else
					sqlQuery=String.format(SukiAppConstants.MONTH_WISE_PRODUCT_REPORT,"PURCHASE INVOICE");
			}
			System.out.println("sqlQuery"+sqlQuery);
			System.out.println("Year"+Year);
			System.out.println("i"+i);
			System.out.println("id"+id);
			Query query = entityManager.createQuery(sqlQuery);
				query.setParameter(1, Year);
				query.setParameter(2, i);
				if(id>0)
				query.setParameter(3, id);
			value = (Double) query.getSingleResult();
			if (value == null)
				value = (double) 0;
			System.out.println(i + " " + SukiAppUtil.getMonthOfYear(i));
			map.put(SukiAppUtil.getMonthOfYear(i), value);
		}
		System.out.println(map);
		return map;
	}
	public GstReportDomain getGstReport(int month, int year) throws SukiException {
		GstReportDomain gstReport=new GstReportDomain();
		String sql="SELECT A.GST,SUM(A.QUANTITY*A.RATE/100),B.INVOICE_NO AS GSTAMOUNT FROM BILL_TRANS A,BILL_MASTER B" + 
				" WHERE A.BILL_NO=B.INVOICE_NO AND MONTH(B.DATE)=? AND YEAR(B.DATE)=? GROUP BY A.GST,B.INVOICE_NO";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, month);
		query.setParameter(2, year);
		List<Object[]> list = query.getResultList();
		GstDomain gst=new GstDomain();
		List<GstDomain> gstReportList=new ArrayList<GstDomain>();
		for(int i=0;i<list.size();i++) {
			gst=new GstDomain();
			gst.setGstPercentage(Double.parseDouble(list.get(i)[0].toString()));
			gst.setGstAmount(Double.parseDouble(list.get(i)[1].toString()));
			gst.setBillNo((int)list.get(i)[2]);
			gstReportList.add(gst);
		}
		gstReport.setGstReportList(gstReportList);
		query = entityManager.createNativeQuery("SELECT SUM(A.QUANTITY*A.RATE/100)AS GSTAMOUNT FROM BILL_TRANS A,BILL_MASTER B \r\n" + 
				"WHERE A.BILL_NO=B.INVOICE_NO  AND MONTH(B.DATE)=? AND YEAR(B.DATE)=?");
		query.setParameter(1, month);
		query.setParameter(2, year);
		double  gstAmt=0;
		try {
		  gstAmt= Double.parseDouble(query.getSingleResult().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		query = entityManager.createNativeQuery("select sum(TOTAL_AMOUNT) from PURCHASE_BILL_MASTER where MONTH(DATE)=? AND YEAR(DATE)=?");
		query.setParameter(1, month);
		query.setParameter(2, year);
		double  purchaseAmount=0;
		try {
			purchaseAmount= Double.parseDouble(query.getSingleResult().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		gstReport.setTotalPurchaseAmount(purchaseAmount);
		gstReport.setTotalGstAmount(gstAmt);
		return gstReport;
	}
}
