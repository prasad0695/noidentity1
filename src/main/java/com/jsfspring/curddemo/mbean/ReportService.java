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
		double salesProfit=0;
		double salesGst=0;
		double purchaseGst=0;
		try {
			purchasePending=(double) query.getSingleResult();
		}catch(Exception e) {
			purchasePending=0;
		}
		dashBoardReport.setPurchasePending(purchasePending);
		query = entityManager.createQuery(""
				+ "select sum(totalAmount-paidAmt) from BillMasterDomain");
		try {
			salesPending=(double) query.getSingleResult();
		}catch(Exception e) {
			salesPending=0;
		}
		dashBoardReport.setSalesPending(salesPending);
		query=entityManager.createQuery("select (sum(qty*rate)-sum(qty*purchaseRate)) as Rate from BillTransDomain");
		try {
			salesProfit=(double) query.getSingleResult();
		}catch(Exception e) {
			salesProfit=0;
		}
		dashBoardReport.setTotalProfit(SukiAppUtil.roundOffHigherSideNew(salesProfit));
		query=entityManager.createQuery("Select sum(gstAmount) from BillMasterDomain");
		try {
			salesGst=(double) query.getSingleResult();
		}catch(Exception e) {
			salesGst=0;
		}
		query=entityManager.createQuery("Select sum(gstAmount) from PurchaseBillMaster");
		try {
			purchaseGst=(double) query.getSingleResult();
		}catch(Exception e) {
			purchaseGst=0;
		}
		dashBoardReport.setGstCredit(SukiAppUtil.roundOffHigherSideNew(purchaseGst-salesGst));
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
		String sql="SELECT A.GST,SUM((A.QUANTITY*A.RATE)*A.GST/100),B.INVOICE_NO AS GSTAMOUNT,C.COMPANY_NAME,C.GST AS GSTNO,B.TOTAL_AMOUNT FROM BILL_TRANS A,BILL_MASTER B,COMPANY C WHERE A.BILL_NO=B.INVOICE_NO AND B.COMPANY_ID=C.COMPANY_ID AND MONTH(B.DATE)=? AND YEAR(B.DATE)=? AND B.GST_BILL='1' GROUP BY A.GST,B.INVOICE_NO,C.COMPANY_NAME,C.GST,B.TOTAL_AMOUNT";
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
			gst.setCompanyName(list.get(i)[3].toString());
			gst.setGst(list.get(i)[4].toString());
			gst.setTotalAmt(Double.parseDouble(list.get(i)[5].toString()));
			gstReportList.add(gst);
		}
		gstReport.setGstReportList(gstReportList);
		sql="SELECT A.GST,SUM((A.QUANTITY*A.RATE)*A.GST/100),B.ROW_ID AS GSTAMOUNT FROM PURCHASE_BILL_TRANS A,PURCHASE_BILL_MASTER B \r\n" + 
				"WHERE A.MASTER_ROW_ID=B.ROW_ID AND MONTH(B.DATE)=? AND YEAR(B.DATE)=? AND B.GST_BILL='1' GROUP BY A.GST,B.ROW_ID";
		query = entityManager.createNativeQuery(sql);
		query.setParameter(1, month);
		query.setParameter(2, year);
		list = query.getResultList();
		gst=new GstDomain();
		gstReportList=new ArrayList<GstDomain>();
		for(int i=0;i<list.size();i++) {
			gst=new GstDomain();
			gst.setGstPercentage(Double.parseDouble(list.get(i)[0].toString()));
			gst.setGstAmount(Double.parseDouble(list.get(i)[1].toString()));
			gst.setBillNo((int)list.get(i)[2]);
			gstReportList.add(gst);
		}
		gstReport.setPurchaseGstReportList(gstReportList);
		query = entityManager.createNativeQuery("SELECT SUM((A.QUANTITY*A.RATE)*A.GST/100)AS GSTAMOUNT FROM BILL_TRANS A,BILL_MASTER B \r\n" + 
				"WHERE A.BILL_NO=B.INVOICE_NO  AND MONTH(B.DATE)=? AND YEAR(B.DATE)=? AND B.GST_BILL='1'");
		query.setParameter(1, month);
		query.setParameter(2, year);
		double  gstAmt=0;
		try {
		  gstAmt= Double.parseDouble(query.getSingleResult().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		query = entityManager.createNativeQuery("SELECT SUM((A.QUANTITY*A.RATE)*A.GST/100)AS GSTAMOUNT FROM PURCHASE_BILL_TRANS A,PURCHASE_BILL_MASTER B WHERE A.MASTER_ROW_ID=B.ROW_ID AND MONTH(B.DATE)=? AND YEAR(B.DATE)=? AND B.GST_BILL='1'");
		query.setParameter(1, month);
		query.setParameter(2, year);
		double purchaseGstAmt=0;
		try {
			purchaseGstAmt= Double.parseDouble(query.getSingleResult().toString());
		}catch(Exception e){
			e.printStackTrace();
		}

		//SELECT SUM((A.QUANTITY*A.RATE)*A.GST/100)AS GSTAMOUNT FROM PURCHASE_BILL_TRANS A,PURCHASE_BILL_MASTER B WHERE A.MASTER_ROW_ID=B.ROW_ID AND MONTH(B.DATE)=5 AND YEAR(B.DATE)=2020 AND B.GST_BILL='1'
		query = entityManager.createNativeQuery("select sum(TOTAL_AMOUNT) from PURCHASE_BILL_MASTER where MONTH(DATE)=? AND YEAR(DATE)=?");
		query.setParameter(1, month);
		query.setParameter(2, year);
		double  purchaseAmount=0;
		try {
			purchaseAmount= Double.parseDouble(query.getSingleResult().toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		gstReport.setTotalPurchaseGstAmount(purchaseGstAmt);
		gstReport.setTotalPurchaseAmount(purchaseAmount);
		gstReport.setTotalGstAmount(gstAmt);
		return gstReport;
	}
}
