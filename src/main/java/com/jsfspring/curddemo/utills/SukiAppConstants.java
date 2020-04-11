package com.jsfspring.curddemo.utills;

public class SukiAppConstants {
	
	public static final String SYSTEM_NAME="Suki ERP V2.0";
	public static final String SYSTEM_NAME_HRMS="Suki HRMS V2.0";
	
	public static final String CONTENT_TYPE_WORD="application/msword";

	public static final String CONTENT_TYPE_XL="application/vnd.ms-excel";

	public static final String CONTENT_TYPE_CSS="text/css";
	public static final String CONTENT_TYPE_TEXT="application/text";
	

	public static final String CONTENT_TYPE_JPG="image/jpeg";

	public static final String CONTENT_TYPE_PDF="application/PDF";
	
	public static final String CONTENT_TYPE_PPT="application/ms-powerpoint";
	public static final String CONTENT_TYPE_GIF="image/gif";
	public static final String CONTENT_TYPE_PNG="image/png";
	
//	public static final String COMPANY_NAME=SukiAppUtil.getPreferenceValue(SukiAppPreferenceConstant.COMPANY_NAME)+" "+SukiAppUtil.getPreferenceValue(SukiAppPreferenceConstant.UNIT_NO)+"";
//	public static final String COMPANY_NAME_ONLY=SukiAppUtil.getPreferenceValue(SukiAppPreferenceConstant.COMPANY_NAME);
//	public static final String TCS_PER=SukiAppUtil.getPreferenceValue(SukiAppPreferenceConstant.TCS_PER);

	public static final String datePattern="dd/MM/yyyy";
	public static final String datePattern8="d/MM/yyyy";
	public static final String datePattern6="dd-MM-yyyy";
	public static final String datePattern1="E,dd";
	public static String getMasterModuleName() {
		return MASTER_MODULE_NAME;
	}
	public static final String datePattern7="dd";
	public static final String datePattern2="EEEE, dd MMMM yyyy ";
	public static final String datePattern3="EEE,MMM d,yyyy";
	public static final String datePattern5="dd-MM-yy";
	public static final String datePattern4="dd/MM/yyyy HH:MM";
	
	public static final String datePattern9="dd/MMMM/yyyy";
	public static final String CL="Causal Leave";
	public static final String EL="Earn Leave";
	public static final String SL="Medical Leave";
	public static final String LOP="Loss Of Pay";
	public static final String COMPANY_WEEK_OFF="SUNDAY";
	public static final String COMPANY_MODULE_NAME="COMPANY";	
	public static final String MASTER_MODULE_NAME="MASTER";
	public static final String RATE_MASTER_MODULE_NAME="RATE MASTER";
	public static final String ROUGH_ESTIMATE_MODULE_NAME="ROUGH ESTIMATE";
	public static final String ROUGH_VALUATION_MODULE_NAME="ROUGH VALUATION";
	public static final String SALES_MODULE_NAME="SALES";
	public static final String PURCHASE_MODULE_NAME="PURCHASE";
	public static final String ESTIMATE_BILL_MODULE_NAME="ESTIMATE BILL";
	public static final String VALUATION_CASH_PUR_EXC_MODULE_NAME="VALUATION CASH PUR EXC";
	public static final String STOCK_ENTRY_MODULE_NAME="STOCK ENTRY";
	public static final String STOCK_ISSUE_ENTRY_MODULE_NAME="STOCK ISSUE ENTRY";
	public static final String ORDER_MODULE_NAME="ORDER";
	public static final String CHIT_MODULE_NAME="CHIT";
	public static final String REPAIR_MODULE_NAME="REPAIR";
	public static final String GSM_DEALER_MODULE_NAME="GSM DEALER";
	public static final String APPROVAL_MODULE_NAME="APPROVAL";
	public static final String APPROVAL2_MODULE_NAME="APPROVAL2";
	public static final String MAINTANANCE_MODULE_NAME="MAINTANANCE";
	public static final String STATUS_ACTIVE="ACTIVE";
	public static final String STATUS_INACTIVE="IN ACTIVE";
	public static final String CUST_CODE_PREFIX="C";
	public static final String PROD_CODE_PREFIX="P";
	public static final String USER_ID_PREFIX="S";
	public static final String SUP_CODE_PREFIX="S";
	public static final String PO_CODE_PREFIX="PO";
	public static final String INWARD_CODE_PREFIX="IN";
	public static final String PENDING_STATUS="pending";
	public static final String DELIVERED_STATUS="delivered";
	public static final String CLOSED_STATUS="Closed";
	public static final String DC_FOLDER="C:\\Users\\Wrightoff\\Desktop\\INVOICE\\Dc\\";
	public static final String PO_FOLDER="C:\\Users\\Wrightoff\\Desktop\\INVOICE\\Po\\";
	public static final String QUOTATION_FOLDER="C:\\Users\\Wrightoff\\Desktop\\INVOICE\\Quotation\\";
	public static final String DC_PRINTED_FOLDER="C:\\Users\\Wrightoff\\Desktop\\INVOICE\\PrintedPdf\\";
	public static final String BILL_FOLDER="C:\\Users\\Wrightoff\\Desktop\\INVOICE\\savedBill\\";
	
	public static final String OVERVIEW_QUERY="from %s";
	public static final String COUNT_QUERY="select count(a) FROM %s a";
	
	
	public static final String COMPANY_SALES="select month(a.DATE) as month,a.COMPANY_ID,b.COMPANY_NAME,sum(a.TOTAL_AMOUNT) as salesAmt from BILL_MASTER a,"
			+ "COMPANY b where a.COMPANY_ID=b.COMPANY_ID and month(a.DATE)=? and year(DATE)=? group by a.COMPANY_ID,B.COMPANY_NAME,month(a.DATE) order by salesAmt desc";
	public static final String SUPPLIER_PURCHASE="select month(a.DATE),a.SUPPLIER_ID,b.NAME,sum(a.TOTAL_AMOUNT) as purchaseAmt from PURCHASE_BILL_MASTER a,SUPPLIER b where a.SUPPLIER_ID=b.SUP_CODE and month(a.DATE)=? and year(DATE)=?" + 
			" group by a.SUPPLIER_ID,B.NAME,month(a.DATE) order by purchaseAmt desc";
	public static final String COMPANY_PAYMENT="select month(a.DATE),a.COMPANY_ID,b.COMPANY_NAME,sum(a.AMOUNT) as paymentAmt from SALES_PAYMENT a,"
			     + "COMPANY b where a.COMPANY_ID=b.COMPANY_ID and month(a.DATE)=? and year(DATE)=? group by a.COMPANY_ID,B.COMPANY_NAME,month(a.DATE) order by paymentAmt desc";
	public static final String SUPPLIER_PAYMENT="select month(a.DATE),a.SUPPLIER_ID,b.NAME,sum(a.AMOUNT) as paymentAmt from PURCHASE_PAYMENTS a,"
			+ "SUPPLIER b where a.SUPPLIER_ID=b.SUP_CODE and month(a.DATE)=? and year(DATE)=? group by a.SUPPLIER_ID,B.NAME,month(a.DATE) order by paymentAmt desc";
	
	
	public static final String SALES="SALES";
	public static final String PURCHASE="PURCHASE";
	public static final String SALES_PAYMENT="SALES_PAYMENT";
	public static final String PURCHASE_PAYMENT="PURCHASE_PAYMENT";
	public static final String PRODUCT_SALES="PRODUCT_SALES";
	public static final String PRODUCT_PURCHASE="PRODUCT_PURCHASE";
	public static final String SALES_OUTSTANDING="SALES_OUTSTANDING";
	public static final String PURCHASE_OUTSTANDING="PURCHASE_OUTSTANDING";
	
	public static final String MONTH_WISE_REPORT_WITHID="select sum(totalAmount) as amt from %s where year(date)=? and month(date)=? and %s=?";
	public static final String MONTH_WISE_REPORT="select sum(totalAmount) as amt from %s where year(date)=? and month(date)=?";
	public static final String PRODUCT_WISE_SALES_PURCHASE="select 0,A.ITEMS,B.ITEM,sum(A.NOS*A.PRICE) as amount from VW_PRODUCT A, PRODUCT B where A.TYPE='%s' and month(A.DATE)=? and year(A.DATE)=?"
			+ " and A.ITEMS=B.PROD_CODE GROUP BY B.ITEM,A.ITEMS,YEAR(A.DATE) order by amount desc";
	public static final String MONTH_WISE_PRODUCT_REPORT="select sum(nos*price) as amount from VWProduct where year(date)=? and month(date)=? and type='%s'";
	public static final String MONTH_WISE_PRODUCT_REPORT_WITHID="select sum(nos*price) as amount from VWProduct where year(date)=? and month(date)=? and items=? and type='%s'";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
