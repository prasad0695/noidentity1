package com.jsfspring.curddemo.utills;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.DeliveryChalanMaster;
import com.jsfspring.curddemo.entity.InwardMaster;
import com.jsfspring.curddemo.entity.PurchaseBillMaster;
import com.jsfspring.curddemo.entity.PurchaseOrderMaster;
import com.jsfspring.curddemo.entity.QuotationMaster;
import com.jsfspring.curddemo.entity.SalesPaymentsDomain;




public class SukiAppUtil {

	
	public static String generateAutoCode(String prefixStr, String sourseStr) {
		StringBuffer newCode = new StringBuffer();
		int no = 0;
		if (null == sourseStr || sourseStr.equals("")) {
			newCode.append(prefixStr);
			newCode.append("00000");
			newCode.append(++no);
			return newCode.toString();
		}
		no = Integer.parseInt(sourseStr.substring(prefixStr.length()));
		String code = "";
		no++;
		System.out.println("n0 "+no);
		if (no > 1 && no <= 9) {
			code = "00000";
		} else if (no > 9 && no <= 99) {
			code = "0000";
		} else if (no > 99 && no <= 999) {
			code = "000";
		} else if (no > 999 && no <= 9999) {
			code = "00";
		} else if (no > 9999 && no <= 99999) {
			code = "0";
		}
		newCode.append(prefixStr);
		newCode.append(code);
		newCode.append(String.valueOf(no));
		return newCode.toString();
	}
public static int getIntMonthFromStringMonth(String prefixStr){
	if(prefixStr.equalsIgnoreCase("january")){
		return 1;
	}else if(prefixStr.equalsIgnoreCase("febrary")){
		return 2;
	} else if(prefixStr.equalsIgnoreCase("march")){
		return 3;
	}else if(prefixStr.equalsIgnoreCase("april")){
		return 4;
	}else if(prefixStr.equalsIgnoreCase("may")){
		return 5;
	}else if(prefixStr.equalsIgnoreCase("june")){
		return 6;
	}else if(prefixStr.equalsIgnoreCase("july")){
		return 7;
	}else if(prefixStr.equalsIgnoreCase("august")){
		return 8;
	}else if(prefixStr.equalsIgnoreCase("september")){
		return 9;
	}else if(prefixStr.equalsIgnoreCase("october")){
		return 10;
	}else if(prefixStr.equalsIgnoreCase("november")){
		return 11;
	}else if(prefixStr.equalsIgnoreCase("december")){
		return 12;
	}
	return 0;
	
}
	public static String generateAutoCodeTools(String prefixStr,
			String sourseStr) {
		StringBuffer newCode = new StringBuffer();
		int no = 0;
		if (null == sourseStr || sourseStr.equals("")) {
			newCode.append(prefixStr);
			newCode.append("00");
			newCode.append(++no);
			return newCode.toString();
		}
		no = Integer.parseInt(sourseStr.substring(prefixStr.length()));
		String code = "";
		no++;
		if (no > 1 && no <= 9) {
			code = "00";
		} else if (no > 9 && no <= 99) {
			code = "0";
		} else if (no > 99 && no <= 999) {
			code = "";
		}
		newCode.append(prefixStr);
		newCode.append(code);
		newCode.append(String.valueOf(no));
		return newCode.toString();
	}

	public static java.sql.Date getCurrentSQLDate() {
		Calendar cal = Calendar.getInstance();
		return new java.sql.Date(cal.getTime().getTime());
	}
	public static java.sql.Timestamp getCurrentDateAndTime() {
		Calendar cal = Calendar.getInstance();
		return new java.sql.Timestamp(cal.getTime().getTime());
	}
	public static long getTime() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime().getTime();
	}

	public static java.util.Date getCurrentUtilDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(SukiAppConstants.datePattern);
		int dd, mm, yyyy;
		dd = cal.get(Calendar.DATE);
		mm = cal.get(Calendar.MONTH);
		mm++;
		yyyy = cal.get(Calendar.YEAR);
		String dateStr = String.valueOf(dd) + "/" + String.valueOf(mm) + "/"
				+ String.valueOf(yyyy);
		java.util.Date newDate = null;
		try {
			newDate = format.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		 return new java.util.Date( cal.getTime().getTime());
		//return newDate;
	}
	
	public static java.util.Date getCurrentUtilDateDDmmyyyy() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(SukiAppConstants.datePattern);
		int dd, mm, yyyy;
		dd = cal.get(Calendar.DATE);
		mm = cal.get(Calendar.MONTH);
		mm++;
		yyyy = cal.get(Calendar.YEAR);
		String dateStr = String.valueOf(dd) + "/" + String.valueOf(mm) + "/"
				+ String.valueOf(yyyy);
		java.util.Date newDate = null;
		try {
			newDate = format.parse(dateStr);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		 return new java.util.Date( cal.getTime().getTime());
		//return newDate;
	}

	public static String buildQueryString(String domainClassName, String colName) {
		StringBuffer queryString = new StringBuffer(" FROM ");
		queryString.append(domainClassName);
		queryString.append(" WHERE UPPER(");
		queryString.append(domainClassName);
		queryString.append(".");
		queryString.append(colName);
		queryString.append(")LIKE UPPER(?)");
		return queryString.toString();
	}



	public static String getContentType(String fileName) {
		fileName = fileName.toLowerCase();
		if (fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
			return SukiAppConstants.CONTENT_TYPE_WORD;
		} else if (fileName.endsWith(".xls") || fileName.endsWith(".xls")) {
			return SukiAppConstants.CONTENT_TYPE_XL;
		} else if (fileName.endsWith(".pdf")) {
			return SukiAppConstants.CONTENT_TYPE_PDF;
		} else if (fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
			return SukiAppConstants.CONTENT_TYPE_PPT;
		} else if (fileName.endsWith(".gif")) {
			return SukiAppConstants.CONTENT_TYPE_GIF;
		} else if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
			return SukiAppConstants.CONTENT_TYPE_JPG;
		} else if (fileName.endsWith(".png")) {
			return SukiAppConstants.CONTENT_TYPE_PNG;
		} else {
			return SukiAppConstants.CONTENT_TYPE_CSS;
		}
	}

	public static List<SelectItem> getPriority() {
		List<SelectItem> listPriority;
		listPriority = new ArrayList<SelectItem>();
		listPriority.add(new SelectItem("1"));
		listPriority.add(new SelectItem("2"));
		listPriority.add(new SelectItem("3"));
		listPriority.add(new SelectItem("4"));
		listPriority.add(new SelectItem("5"));
		listPriority.add(new SelectItem("6"));
		listPriority.add(new SelectItem("7"));
		listPriority.add(new SelectItem("8"));
		listPriority.add(new SelectItem("9"));
		listPriority.add(new SelectItem("10"));
		return listPriority;
	}

	
	public static String replaceWithNull(String value) {
		if (null == value) {
			return "";
		} else if (value.equalsIgnoreCase("Any")) {
			return "";
		}
		else if (value.equalsIgnoreCase("null")) {
			return "";
		}
		return value;
	}

	public static String replaceWithNull(String value, String defaultStr) {
		if (null == value) {
			return defaultStr;
		} else if (value.equalsIgnoreCase("Any")) {
			return defaultStr;
		}
		return value;
	}

	public static String getShortShiftName(String shift) {
		if (null == shift) {
			return "";
		} else if (shift.equalsIgnoreCase("Shift 1")) {
			return "S1";
		} else if (shift.equalsIgnoreCase("Shift 2")) {
			return "S2";
		} else if (shift.equalsIgnoreCase("Shift 3")) {
			return "S3";
		}
		return "";
	}

	public static String addCharInprefx(String value, String charStr) {
		if (null == value) {
			return "";
		} else if (value.equalsIgnoreCase("Any")) {
			return "";
		} else {
			value = charStr + value + charStr;
		}
		return value;
	}

	public static String replaceWithNull(double value) {
		if (value == 0) {
			return "";
		}
		return String.valueOf(value);
	}

	public static String replaceWithNull(Date value) {
		if (null == value) {
			return "";
		} else {
			return value.toString();
		}
	}

	public static String createMaterialName(String rawType, String rawShape,
			String lengthAndBreath, String rawSize,double rawSizeinMM ,String rawSizeUom,
			String rawGrade,String condition,String condition2) {
		StringBuffer matName = new StringBuffer("");
		if (null != rawType && !SukiAppUtil.isEmpty(rawType)) {
			matName.append(rawType.trim());
			matName.append(" ");
		}
		if (null != rawShape && !SukiAppUtil.isEmpty(rawShape)) {
			matName.append(rawShape.trim());
			matName.append(" ");
		}
		if (null != lengthAndBreath && !SukiAppUtil.isEmpty(lengthAndBreath)	) {
			matName.append(lengthAndBreath.trim());
			matName.append(" ");
		}
		if (rawSizeinMM>0) {
			matName.append(rawSizeinMM);
			matName.append(" mm ");
		}
		if (null != rawSize && !SukiAppUtil.isEmpty(rawSize)) {
			matName.append(rawSize.trim());
			matName.append(" inch ");
		}
		//if (null != rawSizeUom && !ERPAppUtil.isEmpty(rawSizeUom)) {
			//matName.append(rawSizeUom.trim());
		//	matName.append(" ");
		//}
		if (null != rawGrade && !SukiAppUtil.isEmpty(rawGrade)) {
			matName.append(rawGrade.trim());
			matName.append(" ");
		}
		if (null != condition && !SukiAppUtil.isEmpty(condition)) {
			matName.append(condition.trim());
			matName.append(" ");
		}
		if (null != condition2 && !SukiAppUtil.isEmpty(condition2)) {
			matName.append(condition2.trim());
		}
		return matName.toString();
	}

	public static String getProcessString(List<SelectItem> detailList) {
		Iterator<SelectItem> iterator = detailList.iterator();
		int ctr = 0;
		StringBuffer sourceString = new StringBuffer();
		while (iterator.hasNext()) {
			if (ctr > 0)
				sourceString.append(",");
			ctr++;
			SelectItem item = iterator.next();
			sourceString.append(item.getValue());
		}
		return sourceString.toString();
	}

	public static List<SelectItem> getProcessString(String source) {
		List<SelectItem> detailList = new ArrayList<SelectItem>();
		if (null != source && !source.equals("")) {
			String str1[] = source.split(",");
			for (int i = 0; i < str1.length; i++) {
				SelectItem item = new SelectItem(str1[i]);
				detailList.add(item);
			}
		}
		return detailList;
	}

	

	public static String getEachWordInitChar(String value) {
		// String value="FORM TOOLS";
		StringBuffer code = new StringBuffer("");
		for (int i = 0; i < value.length(); i++) {
			if (i == 0) {
				code.append(value.charAt(i));
			}
			if (value.charAt(i) == ' ') {
				code.append(value.charAt(i + 1));
			}
		}
		return code.toString();

	}

	

	public static int findString(String source, String valueToFind) {
		if (valueToFind != null && source != null)
			return source.indexOf(valueToFind);
		else
			return -1;

	}

	public static double calculateMM(String value) {
		double a = 0, b = 0, c = 0;
		if (value == null || value.equals("")) {
			return 0;
		}
		try {

			if (value.indexOf("-") > 0) {
				a = Double.parseDouble(value.substring(0, value.indexOf("-")));
				b = Double.parseDouble(value.substring(value.indexOf("-") + 1,
						value.indexOf("/")));
				c = Double.parseDouble(value.substring(value.indexOf("/") + 1,
						value.length()));
				return ((a * 25.4) + ((b / c) * 25.4));
			}

			if (value.indexOf("-") < 0 && value.indexOf("/") > 0) {
				b = Double.parseDouble(value.substring(0, value.indexOf("/")));
				c = Double.parseDouble(value.substring(value.indexOf("/") + 1,
						value.length()));
				return ((b / c) * 25.4);
			}

			if (value.indexOf("-") < 0 && value.indexOf("/") < 0) {
				a = Double.parseDouble(value);
				return a * 25.4;
			}
		} catch (Exception e) {
			return 0;
		}

		return 0;
	}

	public static String formatDouble(double value) {
		NumberFormat formatter = new DecimalFormat("0.00");
		return formatter.format(value);
	}

	public static String formatDoubleOnePrecision(double value) {
		NumberFormat formatter = new DecimalFormat("0.0");
		return formatter.format(value);
	}
	public static String formatDoubleTwoPrecision(double value) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(value);
	}

	public static String formatDoubleNoPrecision(double value) {
		NumberFormat formatter = new DecimalFormat("0");
		return formatter.format(value);
	}

	public static String formatRawSizeQty(double value) {
		NumberFormat formatter = new DecimalFormat("#0.000");
		return formatter.format(value);
	}

	public static String format(String matType, double value) {
		if (matType != null && matType.equalsIgnoreCase("Rod")) {
			NumberFormat formatter = new DecimalFormat("#0.000");
			return formatter.format(value);
		} else {
			NumberFormat formatter = new DecimalFormat("#0");
			return formatter.format(value);
		}
	}
	
	

	public static String formatInt(int value) {
		NumberFormat formatter = new DecimalFormat("#0");
		return formatter.format(value);
	}

	public static String extractString(String source, String searchString) {
		if (source != null && source.indexOf(searchString) > 0) {
			return source.substring(0, source.indexOf(searchString));
		} else {
			return source;
		}
	}

	public static String getAccountYear() {
		int month = SukiAppUtil.getMonth(SukiAppUtil.getCurrentSQLDate());
		// System.out.println(" month " +month);
		String accYearStr = "";
		if (month <= 3) {
			accYearStr = SukiAppUtil.getCurrentYear() - 1 + "-"
					+ (SukiAppUtil.getCurrentYear());
		} else {
			accYearStr = SukiAppUtil.getCurrentYear() + "-"
					+ (SukiAppUtil.getCurrentYear() + 1);
		}
		return accYearStr;
	}
		
	public static double calculatePOValue(double qty, double price,
			double disper) {
		double discountValue = 0;
		double poValue = 0;
		poValue = qty * price;
		if (disper > 0) {
			discountValue = (disper / 100);
			discountValue = poValue * discountValue;
			poValue = poValue - discountValue;
		}
		return poValue;
	}

	public static java.util.Date getUtilDateFromSQLDate(java.sql.Date date) {
		if (date != null) {
			return new java.util.Date(date.getTime());
		} else {
			return null;
		}
	}
	
	public static java.util.Date getUtilDateFromSQLDate(java.util.Date date) {
		if (date != null) {
			return new java.util.Date(date.getTime());
		} else {
			return null;
		}
	}

	public static java.sql.Date getSQLDateFromUtilDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}
	public static java.sql.Timestamp getTimeStampFromUtilDate(java.util.Date date) {
		if (date != null) {
			return new java.sql.Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	public static java.sql.Date getDateFromString1(String date) {
		java.sql.Date newDate = null;
		if (date == null || date.equals(""))
			return null;
		try {
			DateFormat formatter;
			//dd-MM-yyyy
			formatter = new SimpleDateFormat(SukiAppConstants.datePattern6);
			java.util.Date newDate1 = (java.util.Date) formatter.parse(date);
			newDate = getSQLDateFromUtilDate(newDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static java.sql.Date getDateFromString(String date) {
		java.sql.Date newDate = null;
		if (date == null || date.equals(""))
			return null;
		try {
			DateFormat formatter;
			// System.out.println(date.substring(0, date.indexOf("/")));
			//"dd/MM/yyyy";
			formatter = new SimpleDateFormat(SukiAppConstants.datePattern);
			java.util.Date newDate1 = (java.util.Date) formatter.parse(date);
			newDate = getSQLDateFromUtilDate(newDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static java.sql.Date getDateFromString2(String date) {
		java.sql.Date newDate = null;
		if (date == null || date.equals(""))
			return null;
		try {
			DateFormat formatter;
			// System.out.println(date.substring(0, date.indexOf("/")));
			formatter = new SimpleDateFormat(SukiAppConstants.datePattern9);
			java.util.Date newDate1 = (java.util.Date) formatter.parse(date);
			newDate = getSQLDateFromUtilDate(newDate1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static String getDate(java.sql.Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(SukiAppConstants.datePattern);
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	public static String getDate(java.util.Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(SukiAppConstants.datePattern);
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	public static String getDate1(java.util.Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(SukiAppConstants.datePattern1);
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	public static String getDate2(java.util.Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat();
			dateFormat.applyPattern(SukiAppConstants.datePattern7);
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	public static double roundedOff(double d) {
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		NumberFormat formatter = new DecimalFormat("#0");
		return Double.valueOf(formatter.format(d));
	}

	public static double formatDoubleNew(double d) {
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		NumberFormat formatter = new DecimalFormat("#0.00");
		return Double.valueOf(formatter.format(d));
	}

	public static String getTaxPer(double tax) {
		double cst = tax;
		double remainder = (cst % 1);
		StringBuffer taxPer = new StringBuffer();
		if (remainder > 0) {
			taxPer.append(formatDoubleNew(tax));
		} else {
			taxPer.append(SukiAppUtil.formatDoubleNoPrecision(tax));
		}
		return taxPer.toString();
	}

	public static int roundedOffInt(double d) {
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		NumberFormat formatter = new DecimalFormat("#0");
		return Integer.valueOf(formatter.format(d));
	}

	public static String roundedOffIntValue(double d) {
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		NumberFormat formatter = new DecimalFormat("#0");
		return formatter.format(d);
	}

	public static String roundedOffIntValue(int d) {
		// DecimalFormat twoDForm = new DecimalFormat("#.##");
		NumberFormat formatter = new DecimalFormat("#0");
		return formatter.format(d);
	}

	public static String getSubString(String source, int lenghReq) {
		if (source != null && source.length() > lenghReq) {
			return source.substring(0, lenghReq) + "..";
		} else {
			return source;
		}

	}

	public static String getSubString1(String source, int lenghReq) {
		try{
			if (source != null && source.length() > lenghReq) {
		
			return source.substring(0, lenghReq);
		} else {
			return source;
		}
		}catch(Exception e){
			//e.printStackTrace();
			return "";
		}

	}
	
	

	public static int isDateGreaterThanCurrent(java.sql.Date date) {
		return SukiAppUtil.getCurrentSQLDate().compareTo(date);
	}

	public static boolean isDate1EqualToDate2(java.sql.Date date1,
			java.sql.Date date2) {
		int result = date1.compareTo(date2);
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1GreaterThanDate2(java.sql.Date date1,
			java.sql.Date date2) {
		int result = date1.compareTo(date2);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1LessThanDate2(java.sql.Date date1,
			java.sql.Date date2) {
		int result = date1.compareTo(date2);

		if (result < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1LessThanOrEqualToDate2(java.sql.Date date1,
			java.sql.Date date2) {
		int result = date1.compareTo(date2);

		if (result < 0 || result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1GreaterThanOrEqualToDate2(java.sql.Date date1,
			java.sql.Date date2) {
		int result = date1.compareTo(date2);
		if (result > 0 || result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1EqualToDate2(java.util.Date date1,
			java.util.Date date2) {
		int result = date1.compareTo(date2);
		if (result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1GreaterThanDate2(java.util.Date date1,
			java.util.Date date2) {
		int result = date1.compareTo(date2);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1LessThanDate2(java.util.Date date1,
			java.util.Date date2) {
		int result = date1.compareTo(date2);
		if (result < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1LessThanOrEqualToDate2(java.util.Date date1,
			java.util.Date date2) {
		int result = date1.compareTo(date2);

		if (result < 0 || result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isDate1GreaterThanOrEqualToDate2(
			java.util.Date date1, java.util.Date date2) {
		int result = date1.compareTo(date2);
		if (result > 0 || result == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static List<SelectItem> removeSelectItem(String processName,
			List<SelectItem> srclist) {
		List<SelectItem> returnList = new ArrayList<SelectItem>();
		if (srclist != null && srclist.size() >= 0) {
			Iterator<SelectItem> iterator = srclist.iterator();
			while (iterator.hasNext()) {
				SelectItem item = iterator.next();
				if (!processName.equals(item.getValue().toString())) {
					returnList.add(item);
				}
			}
		}
		return returnList;
	}

	public static List<SelectItem> addSelectItem(String processName,
			List<SelectItem> srclist) {
		if (processName != null) {
			srclist.add(new SelectItem(processName));
		}
		return srclist;
	}

	public static List<Date> getDates(int year, int month, int fromDay,
			int toDay) {
		Calendar calendar = Calendar.getInstance();
		List<java.util.Date> dates = new ArrayList<java.util.Date>();
		for (int i = fromDay; i <= toDay; i++) {
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DATE, i);
			calendar.set(Calendar.YEAR, year);
			dates.add(calendar.getTime());
			// System.out.println(" Date "+calendar.getTime());
		}
		return dates;
	}

	public static String getDay(java.util.Date date) {
		if (date == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		if (day == 1) {
			return "Sunday";
		} else if (day == 2) {
			return "Monday";
		} else if (day == 3) {
			return "Tuesday";
		} else if (day == 4) {
			return "Wednesday";
		} else if (day == 5) {
			return "Thursday";
		} else if (day == 6) {
			return "Friday";
		} else if (day == 7) {
			return "Saturday";
		} else {
			return "";
		}
	}

	public static int getMaxDayOfMonth(int year, String month) {
		if (month.equalsIgnoreCase("January")) {
			return 31;
		} else if (month.equalsIgnoreCase("February")) {// TOOD leap year
			if(isLeafYear(year)){
				return 29; 
			 }else{
				return 28;
			 }
		} else if (month.equalsIgnoreCase("March")) {
			return 31;
		} else if (month.equalsIgnoreCase("April")) {
			return 30;
		} else if (month.equalsIgnoreCase("May")) {
			return 31;
		} else if (month.equalsIgnoreCase("June")) {
			return 30;
		} else if (month.equalsIgnoreCase("July")) {
			return 31;
		} else if (month.equalsIgnoreCase("August")) {
			return 31;
		} else if (month.equalsIgnoreCase("September")) {
			return 30;
		} else if (month.equalsIgnoreCase("October")) {
			return 31;
		} else if (month.equalsIgnoreCase("November")) {
			return 30;
		} else if (month.equalsIgnoreCase("December")) {
			return 31;
		}

		return -1;
	}
	
	public static boolean isLeafYear(int year){
		 boolean leafYear = ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
		 return leafYear;
	}

	public static int getMaxDayOfMonth(int year, int month) {
		if (month == 1) {
			return 31;
		} else if (month == 2) {// TOOD leap year
			
			 if(isLeafYear(year)){
				return 29; 
			 }else{
				return 28;
			 }
		} else if (month == 3) {
			return 31;
		} else if (month == 4) {
			return 30;
		} else if (month == 5) {
			return 31;
		} else if (month == 6) {
			return 30;
		} else if (month == 7) {
			return 31;
		} else if (month == 8) {
			return 31;
		} else if (month == 9) {
			return 30;
		} else if (month == 10) {
			return 31;
		} else if (month == 11) {
			return 30;
		} else if (month == 12) {
			return 31;
		}

		return -1;
	}

	public static int getMonthOfYear(String month) {
		if (month.equalsIgnoreCase("January")) {
			return 1;
		} else if (month.equalsIgnoreCase("February")) {
			return 2;
		} else if (month.equalsIgnoreCase("March")) {
			return 3;
		} else if (month.equalsIgnoreCase("April")) {
			return 4;
		} else if (month.equalsIgnoreCase("May")) {
			return 5;
		} else if (month.equalsIgnoreCase("June")) {
			return 6;
		} else if (month.equalsIgnoreCase("July")) {
			return 7;
		} else if (month.equalsIgnoreCase("August")) {
			return 8;
		} else if (month.equalsIgnoreCase("September")) {
			return 9;
		} else if (month.equalsIgnoreCase("October")) {
			return 10;
		} else if (month.equalsIgnoreCase("November")) {
			return 11;
		} else if (month.equalsIgnoreCase("December")) {
			return 12;
		}

		return -1;
	}
	
	

	public static String getMonthOfYear(int month) {
		if (month == 1) {
			return "January";
		} else if (month == 2) {// TOOD leap year
			return "February";
		} else if (month == 3) {
			return "March";
		} else if (month == 4) {
			return "April";
		} else if (month == 5) {
			return "May";
		} else if (month == 6) {
			return "June";
		} else if (month == 7) {
			return "July";
		} else if (month == 8) {
			return "August";
		} else if (month == 9) {
			return "September";
		} else if (month == 10) {
			return "October";
		} else if (month == 11) {
			return "November";
		} else if (month == 12) {
			return "December";
		}

		return "";
	}
	public static List<String> getMonthList(){
		List<String> list=new ArrayList<String>();
		list.add("January");
		list.add("February");
		list.add("March");
		list.add("April");
		list.add("May");
		list.add("June");
		list.add("July");
		list.add("August");
		list.add("September");
		list.add("October");
		list.add("November");
		list.add("December");
		return list;
	}
	public static List<Integer> getYearList(){
		List<Integer> list=new ArrayList<Integer>();
		list.add(2017);
		list.add(2018);
		list.add(2019);
		list.add(2020);
		list.add(2021);
		return list;
	}
	public static String getShortMonthOfYear(int month) {
		if (month == 1) {
			return "Jan";
		} else if (month == 2) {// TOOD leap year
			return "Feb";
		} else if (month == 3) {
			return "Mar";
		} else if (month == 4) {
			return "Apr";
		} else if (month == 5) {
			return "May";
		} else if (month == 6) {
			return "Jun";
		} else if (month == 7) {
			return "Jul";
		} else if (month == 8) {
			return "Aug";
		} else if (month == 9) {
			return "Sep";
		} else if (month == 10) {
			return "Oct";
		} else if (month == 11) {
			return "Nov";
		} else if (month == 12) {
			return "Dec";
		}

		return "";
	}
	
	public static String getMonthAndYear(int month,int year){
		year=(year - 2000);
		String monthYear=SukiAppUtil.getShortMonthOfYear(month)+"'"+year;
		return monthYear;
	}

	public static int getDayOfTheMonth(java.util.Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DATE);
	}

	public static int getMonth(java.util.Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		month++;
		return month;
	}

	public static int getMonth(java.sql.Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getUtilDateFromSQLDate(date));
		int month = calendar.get(Calendar.MONTH);
		month++;
		return month;
	}

	public static String getMonthOfDate(java.sql.Date date) {
		if (date == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getUtilDateFromSQLDate(date));
		int month = calendar.get(Calendar.MONTH);
		month++;
		return getMonthOfYear(month);
	}

	public static String getMonthOfDate(java.util.Date date) {
		if (date == null) {
			return "";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getSQLDateFromUtilDate(date));
		int month = calendar.get(Calendar.MONTH);
		month++;
		return getMonthOfYear(month);
	}

	public static int getYear(java.util.Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	public static int getYear(java.sql.Date date) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getUtilDateFromSQLDate(date));
		int year = calendar.get(Calendar.YEAR);
		return year;
	}

	public static String getCurrentMonth() {
		return SukiAppUtil.getMonthOfYear(SukiAppUtil.getMonth(SukiAppUtil.getCurrentSQLDate()));
	}
	public static String getPreviousMonth() {
		return SukiAppUtil.getMonthOfYear(SukiAppUtil.getMonth(SukiAppUtil.getCurrentSQLDate())-1);
	}
//	public static String getPreviousMonth() {
//		Calendar calendar = Calendar.getInstance();
//		return getMonthOfYear(calendar.get(Calendar.MONTH));
//	}

	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	public static String getFinancialYears() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = getMonth(SukiAppUtil.getCurrentSQLDate());
		if (month > 3) {
			String value = String.valueOf(year) + "-"
					+ String.valueOf((year + 1));
			return value;
		} else {
			String value = String.valueOf((year - 1)) + "-"
					+ String.valueOf(year);
			return value;
		}
	}

	public static String getVCEventNewValue(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null
				&& event.getNewValue().toString() != null) {
			return event.getNewValue().toString();
		} else {
			return "";
		}

	}

	public static String getProductTableColName(String domainColName) {
		if (domainColName.equalsIgnoreCase("partNumber")) {
			return "PART_NO";
		} else if (domainColName.equalsIgnoreCase("partName")) {
			return "PART_NAME";
		} else if (domainColName.equalsIgnoreCase("rmType")) {
			return "RM_TYPE";
		} else if (domainColName.equalsIgnoreCase("exciseChapterNo")) {
			return "EXCISE_CHAPTER_NO";
		} else if (domainColName.equalsIgnoreCase("location")) {
			return "LOCATION";
		} else if (domainColName.equalsIgnoreCase("prodStatus")) {
			return "PROD_STATUS";
		} else if (domainColName.equalsIgnoreCase("inputMaterial")) {
			return "INPUT_MATERIAL";
		} else if (domainColName.equalsIgnoreCase("productCategory")) {
			return "PROD_CATEGORY";
		} else if (domainColName.equalsIgnoreCase("typeProduct")) {
			return "TYPE_OF_PRODUCT";
		} else if (domainColName.equalsIgnoreCase("lock")) {
			return "LOCK";
		} else if (domainColName.equalsIgnoreCase("stockTagToAs")) {
			return "STOCK_TAG_TO_AS";
		}
		return null;
	}

	public static String cleanUp(String value, int expSize) {
		if (value == null) {
			return "";
		}
		if (value.length() > expSize) {
			value = value.substring(0, expSize);
			return value;
		} else {
			return value;
		}
	}

	public static java.util.Date addUtilDate(int noOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, noOfDays);
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(SukiAppConstants.datePattern);
		int dd, mm, yyyy;
		dd = cal.get(Calendar.DATE);
		mm = cal.get(Calendar.MONTH);
		mm++;
		yyyy = cal.get(Calendar.YEAR);
		String dateStr = String.valueOf(dd) + "/" + String.valueOf(mm) + "/"
				+ String.valueOf(yyyy);
		java.util.Date newDate = null;
		try {
			newDate = format.parse(dateStr);

		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		// return new java.util.Date( cal.getTime().getTime());
		return newDate;
	}

	public static java.sql.Date addSqlDate(int noOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, noOfDays);
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(SukiAppConstants.datePattern);
		java.sql.Date newDate = null;
		newDate = getSQLDateFromUtilDate(cal.getTime());
		cal.getTime();
		return newDate;
	}

	public static java.sql.Date addSqlDate(java.sql.Date date, int noOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, noOfDays);
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(SukiAppConstants.datePattern);
		java.sql.Date newDate = null;
		newDate = getSQLDateFromUtilDate(cal.getTime());
		cal.getTime();
		return newDate;
	}

	public static boolean isWeeklyOff(java.sql.Date date) {
		// System.out.println(" ERPAppUtil.getDay(date) "
		// +ERPAppUtil.getDay(date));
		if (SukiAppUtil.getDay(date).equalsIgnoreCase(
				SukiAppConstants.COMPANY_WEEK_OFF)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWeeklyOff(java.util.Date date) {
		// System.out.println(" ERPAppUtil.getDay(date) "
		// +ERPAppUtil.getDay(date));
		if (SukiAppUtil.getDay(date).equalsIgnoreCase(
				SukiAppConstants.COMPANY_WEEK_OFF)) {
			return true;
		} else {
			return false;
		}
	}

	public static java.util.Date addUtilDate(java.util.Date date, int noOfDays) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, noOfDays);
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(SukiAppConstants.datePattern);
		java.util.Date newDate = null;
		newDate = getSQLDateFromUtilDate(cal.getTime());
		cal.getTime();
		return newDate;
	}

	public static long getNoOfDaysFromCurrentDate(java.util.Date purDate) {
		Calendar purchaseDate = Calendar.getInstance();
		purchaseDate.setTime(purDate);
		// purchaseDate.add(Calendar.DATE, -700);
		Calendar currentDate = Calendar.getInstance();

		long milliseconds1 = purchaseDate.getTimeInMillis();
		long milliseconds2 = currentDate.getTimeInMillis();

		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		return diffDays;
	}

	public static long getNoOfDaysBetweenDates(java.util.Date date1,
			java.util.Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		// System.out.println(date1);
		// System.out.println(date2);
		// purchaseDate.add(Calendar.DATE, -700);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		long milliseconds1 = cal1.getTimeInMillis();
		long milliseconds2 = cal2.getTimeInMillis();

		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		// System.out.println(diffDays);
		return diffDays;
	}

	public static int getNoOfDaysBetweenDates1(java.util.Date date1,
			java.util.Date date2) {

		// System.out.println(date1);
		// System.out.println(date2);
		// purchaseDate.add(Calendar.DATE, -700);
		Date toDate = date1;
		int diffDays = 1;
		if (date1.compareTo(date2) == 0) {
			return 1;
		}
		while (toDate.compareTo(date2) != 0) {
			// System.out.println("sdf" +toDate);
			toDate = addUtilDate(toDate, 1);
			diffDays++;
		}
		return diffDays;
	}

	public static double getDepreciationValue(java.util.Date purDate,
			double value, double dipPer) {
		double noOfDays = getNoOfDaysFromCurrentDate(purDate);
		double dipValue = 0;
		dipValue = value * (dipPer / 100);
		if (noOfDays > 0) {
			dipValue = (noOfDays / 365) * dipValue;
		}
		return dipValue;
	}

	public static double calculateDipValue(java.util.Date purDate,
			double purValue, double dipPer) {
		if (purDate != null && purValue > 0 && dipPer > 0) {
			return SukiAppUtil.getDepreciationValue(purDate, purValue, dipPer);
		} else {
			return 0;
		}
	}

	public static double totalMinutes(double hours) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(hours);
		String value1 = value.substring(0, value.indexOf("."));
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		double hour = Double.parseDouble(value1);
		double mm = Double.parseDouble(value2);
		double totMM = (hour * 60) + mm;
		return totMM;
	}

	public static int getTotalMinutes(double hours) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(hours);
		String value1 = value.substring(0, value.indexOf("."));
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		int hour = Integer.parseInt(value1);
		int mm = Integer.parseInt(value2);
		int totMM = (hour * 60) + mm;
		return totMM;
	}

	public static int getTotalMinutes(int hours) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(hours);
		String value1 = value.substring(0, value.indexOf("."));
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		int hour = Integer.parseInt(value1);
		int mm = Integer.parseInt(value2);
		int totMM = (hour * 60) + mm;
		return totMM;
	}

	public static double getDifferneceTime(double time1, double time2) {
		int mm1 = SukiAppUtil.getTotalMinutes(time1);
		int mm2 = SukiAppUtil.getTotalMinutes(time2);
		// System.out.println("mm1 " +mm1);
		// System.out.println("mm2 " +mm2);
		int diff = mm1 - mm2;
		return getHours(diff);
	}

	public static int getHoursFromTime(double hours) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(hours);
		String value1 = value.substring(0, value.indexOf("."));
		int hour = Integer.parseInt(value1);
		return hour;
	}

	public static int getMinuteFromTime(double hours) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(hours);
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		int mm = Integer.parseInt(value2);
		return mm;
	}

	public static double getPrdTimeAfterBuffer(double prodHours,
			double buffTimePer) {
		double prdMM = totalMinutes(prodHours);
		// System.out.println(" prdMM "+prdMM);
		if (buffTimePer > 0) {
			double prdTimeAfterBuffer = prdMM - (prdMM * (buffTimePer / 100));
			// System.out.println(" prdTimeAfterBuffer "+prdTimeAfterBuffer);
			return getHours(prdTimeAfterBuffer);
		}
		return prodHours;
	}

	
	public static double getMinuteFromTime1(double hours) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(hours);
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		return Double.parseDouble(value2);
	}

	public static double roundOffHigherSide(double amt) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(amt);
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		int decimal = Integer.parseInt(value2);
		// String value=String.valueOf(hours);
		NumberFormat formatter1 = new DecimalFormat("#0.00");
		String value3 = formatter1.format(amt);
		String value4 = value3.substring(0, value.indexOf("."));
		double value5 = Integer.parseInt(value4);
		if (decimal > 0) {
			value5 = value5 + 1;
		}

		return value5;
	}

	public static double roundOffHigherSideNew(double value) {
		int value2 = (int) (value + .50);
		return Double.parseDouble(String.valueOf(value2));
	}

	public static double roundOffLowerSideNew(double value) {
		int value2 = (int) (value + .49);
		return Double.parseDouble(String.valueOf(value2));
	}

	// this is wrong need to check
	public static double roundOffLowerSide(double amt) {
		// String value=String.valueOf(hours);
		NumberFormat formatter = new DecimalFormat("#0.00");
		String value = formatter.format(amt);
		String value2 = value.substring(value.indexOf(".") + 1, value.length());
		int decimal = Integer.parseInt(value2);
		// String value=String.valueOf(hours);
		NumberFormat formatter1 = new DecimalFormat("#0.00");
		String value3 = formatter1.format(amt);
		String value4 = value3.substring(0, value.indexOf("."));
		double value5 = Integer.parseInt(value4);
		// System.out.println(decimal);
		if (decimal > 0 && decimal < 0.5) {
			return value5;
		} else if (decimal > 0 && decimal >= 0.5) {
			value5 = value5 + 1;
		}
		// System.out.println(value5);
		return value5;
	}

	public static double getHours(double mm) {
		if (mm > 0) {
			double hour = mm / 60;
			String hoursStr = String.valueOf(hour);
			hoursStr = hoursStr.substring(0, hoursStr.indexOf("."));
			double a = mm % 60;
			NumberFormat formatter = new DecimalFormat("#0");
			String mmStr = formatter.format(a);
			StringBuffer value = new StringBuffer();
			value.append(hoursStr);
			value.append(".");
			if (a < 10) {
				value.append("0");
				value.append(mmStr);
			} else {
				value.append(mmStr);
			}
			return Double.parseDouble(value.toString());
		} else {
			return 0;
		}
	}

	public static double getHours(int mm) {
		if (mm > 0) {
			double hour = mm / 60;
			String hoursStr = String.valueOf(hour);
			hoursStr = hoursStr.substring(0, hoursStr.indexOf("."));
			double a = mm % 60;
			// System.out.println(" a ->"+a);
			NumberFormat formatter = new DecimalFormat("#0");
			String mmStr = formatter.format(a);
			StringBuffer value = new StringBuffer();
			value.append(hoursStr);
			value.append(".");
			if (a < 10) {
				value.append("0");
				value.append(mmStr);
			} else {
				value.append(mmStr);
			}

			return Double.parseDouble(value.toString());
		} else {
			return 0;
		}
	}

	public static double addTime(double time1, double time2) {
		int time1MM = getTotalMinutes(time1);
		int time2MM = getTotalMinutes(time2);
		// System.out.println(time1MM);
		// System.out.println(time2MM);
		return getHours(time1MM + time2MM);
	}

	public static int calculateDifference(Date a, Date b) {
		int tempDifference = 0;
		int difference = 0;
		Calendar earlier = Calendar.getInstance();
		Calendar later = Calendar.getInstance();
		if (a.compareTo(b) < 0) {
			earlier.setTime(a);
			later.setTime(b);
		} else {
			earlier.setTime(b);
			later.setTime(a);
		}

		while (earlier.get(Calendar.YEAR) != later.get(Calendar.YEAR)) {
			tempDifference = 365 * (later.get(Calendar.YEAR) - earlier
					.get(Calendar.YEAR));
			difference += tempDifference;
			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		if (earlier.get(Calendar.DAY_OF_YEAR) != later
				.get(Calendar.DAY_OF_YEAR)) {
			tempDifference = later.get(Calendar.DAY_OF_YEAR)
					- earlier.get(Calendar.DAY_OF_YEAR);
			difference += tempDifference;
			earlier.add(Calendar.DAY_OF_YEAR, tempDifference);
		}

		return difference;
	}

	public static long dateDifference(java.sql.Date date1, java.sql.Date date2) {
		Calendar calDate1 = Calendar.getInstance();
		calDate1.setTime(date1);
		Calendar calDate2 = Calendar.getInstance();
		calDate2.setTime(date2);
		long milliseconds1 = calDate1.getTimeInMillis();
		long milliseconds2 = calDate2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		long diffDays = diff / (24 * 60 * 60 * 1000);
		//System.out.println("  Date 1 " +date1 +"\t Date 2 "+date2);
		//System.out.println("  diffDays " +diffDays);
		return diffDays;
	}

	public static long dateDifference(java.util.Date date1, java.util.Date date2) {
		if(date1!=null && date2!=null){
			Calendar calDate1 = Calendar.getInstance();
			calDate1.setTime(date1);
			Calendar calDate2 = Calendar.getInstance();
			calDate2.setTime(date2);
			long milliseconds1 = calDate1.getTimeInMillis();
			long milliseconds2 = calDate2.getTimeInMillis();
			long diff = milliseconds2 - milliseconds1;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			//System.out.println("  diffDays " +diffDays);
			return diffDays;
		}else{
			return 0;
		}
	
	}

	public static boolean isNumeric(String value) {
		if (value != null && value.equals("")) {
			return false;
		}
		if (value.matches("[0-9]*")) {
			return true;
		} else {
			return false;
		}
	}

	public static double convertToNum(String value) {
	try{
		if (value != null && value.equals("")) {
			return 0;
		}else{
			double value1=Double.parseDouble(value);
			return value1;
		}
	}catch(Exception e){
		return 0;
	}

	}

	public static boolean isAlphaNumeric(String value) {
		if (value.matches("[a-zA-Z0-9]*")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isAlphaChars(String value) {
		if (value.matches("[a-zA-Z]*")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isAlphaNA(String value) {
		if (value.matches("[[A-Z]{5} d{4}[A-Z]{1}[0-9]]*")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmailValidate(String email) {
		Pattern p = Pattern.compile("[a-zA-Z]*[0-9]*@[a-zA-Z]*\\.[a-zA-Z]*");
		Matcher m = p.matcher(email);
		boolean b = m.matches();
		if (b == true) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmailIdValid(String emailId) {
		boolean isEmailIdValid = false;
		if (emailId != null && emailId.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(emailId);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	public static String getNumericWords(int val) {
		// Defining variables q is quotient, r is remainder
		int len, q = 0, r = 0;
		String ltr = " ";
		String Str = "";
		ConsNumToLetter n = new ConsNumToLetter();
		int num = val;
		// if (num <= 0){
		// System.out.println("Zero or Negative number not for conversion");
		// break;
		// }
		while (num > 0) {
			len = n.numberCount(num);
			// Take the length of the number and do letter conversion
			switch (len) {
			case 8:
				q = num / 10000000;
				r = num % 10000000;
				ltr = n.twonum(q);
				Str = Str + ltr + n.digit[4];
				num = r;
				break;
			case 7:
			case 6:
				q = num / 100000;
				r = num % 100000;
				ltr = n.twonum(q);
				Str = Str + ltr + n.digit[3];
				num = r;
				break;
			case 5:
			case 4:
				q = num / 1000;
				r = num % 1000;
				ltr = n.twonum(q);
				Str = Str + ltr + n.digit[2];
				num = r;
				break;
			case 3:
				if (len == 3)
					r = num;
				ltr = n.threenum(r);
				Str = Str + ltr;
				num = 0;
				break;
			case 2:

				ltr = n.twonum(num);
				Str = Str + ltr;
				num = 0;
				break;
			case 1:
				Str = Str + n.unitdo[num];
				num = 0;
				break;
			default:
				num = 0;
				System.exit(1);
			}
		}
		return Str;
	}
	
	
	public static String getNumericWords(double val) {
		// Defining variables q is quotient, r is remainder
		int len, q = 0, r = 0;
		String ltr = " ";
		String Str = "";
		ConsNumToLetter n = new ConsNumToLetter();
		int num =(int)val;
		// if (num <= 0){
		// System.out.println("Zero or Negative number not for conversion");
		// break;
		// }
		while (num > 0) {
			len = n.numberCount(num);
			// Take the length of the number and do letter conversion
			switch (len) {
			case 8:
				q = num / 10000000;
				r = num % 10000000;
				ltr = n.twonum(q);
				Str = Str + ltr + n.digit[4];
				num = r;
				break;
			case 7:
			case 6:
				q = num / 100000;
				r = num % 100000;
				ltr = n.twonum(q);
				Str = Str + ltr + n.digit[3];
				num = r;
				break;
			case 5:
			case 4:
				q = num / 1000;
				r = num % 1000;
				ltr = n.twonum(q);
				Str = Str + ltr + n.digit[2];
				num = r;
				break;
			case 3:
				if (len == 3)
					r = num;
				ltr = n.threenum(r);
				Str = Str + ltr;
				num = 0;
				break;
			case 2:

				ltr = n.twonum(num);
				Str = Str + ltr;
				num = 0;
				break;
			case 1:
				Str = Str + n.unitdo[num];
				num = 0;
				break;
			default:
				num = 0;
				System.exit(1);
			}
		}
		int val1=getRemainder(val);
		if(val1>0){
			String remStr=getNumericWords(val1);
			StringBuffer result=new StringBuffer();
			result.append(Str);
			result.append(" &  ");
			result.append(remStr);
			result.append(" Paise");
			return result.toString();
		}
		return Str;
	}
	
	

	public static byte[] getFileImage(String filename) {
		byte[] result = null;
		String fileLocation = filename;
		File fileName = new File(fileLocation);
		if (fileName.exists()) {
			result = new byte[(int) fileName.length()];
			try {
				FileInputStream in = new FileInputStream(fileLocation);
				in.read(result);
				in.close();
			} catch (Exception ex) {
				// ex.printStackTrace();
			}
			return result;
		} else {
			result = "File not exist".getBytes();
			return result;
		}
	}

	public static void saveFile(String fileName, byte[] fileContents)
			throws Exception {
		File newFile = new File(fileName);
		if (newFile.exists()) {
			newFile.delete();
		}
		newFile.setReadOnly();
		newFile.setExecutable(false);
		OutputStream out = new FileOutputStream(newFile);
		out.write(fileContents);
		out.close();
	}

	public static void deleteFile(String fileName) throws Exception {
		File newFile = new File(fileName);
		newFile.deleteOnExit();
	}

	

	

	

	public static String StringSplit(String source) {
		if (source != null && source != "") {
			String sourceAfterSplit[] = source.split(",");
			StringBuffer value = new StringBuffer();
			for (int i = 0; i < sourceAfterSplit.length; i++) {
				if (sourceAfterSplit[i].equalsIgnoreCase("I")
						|| sourceAfterSplit[i].equalsIgnoreCase("II")
						|| sourceAfterSplit[i].equalsIgnoreCase("III")
						|| sourceAfterSplit[i].equalsIgnoreCase("IV")) {
					// do nothing
				} else {
					value.append(sourceAfterSplit[i]);
					value.append("\n\n");
				}
			}

			return value.toString();
		} else {
			return "";

		}
	}

	

	public static String getDateFormatDDMMMYYYY(java.sql.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMYYYY(java.sql.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.format(date);
		}
		return "";

	}

	public static String getDateFormatDDMMYYYY(java.util.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MM-yyyy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMMYY(java.util.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MMM-yy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMM(java.util.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MMM");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMMYY(java.sql.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MMM-yy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMYY(java.sql.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MM-yy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMMYYYY(java.util.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MMM-yyyy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getDateFormatDDMMMYYYYHHMM() {

		Format formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy_HH_MM");
		return formatter.format(Calendar.getInstance().getTime());
	}

	public static String getDateFormatDDMMMYYYYHHMMSS() {

		Format formatter;
		formatter = new SimpleDateFormat("dd-MMM-yyyy_HH_MM_ss");
		return formatter.format(Calendar.getInstance().getTime());
	}

	public static String getDateFormatDDMMYY(java.util.Date date) {
		if (date != null) {
			Format formatter;
			formatter = new SimpleDateFormat("dd-MM-yy");
			return formatter.format(date);
		}
		return "";
	}

	public static String getUOMShortName(String uom) {
		if (uom != null && uom.equalsIgnoreCase("Numbers")) {
			return "Nos";
		} else {
			return uom;
		}

	}

	public static String toProperCase(String value) {
		if (value != null && value.length() > 0) {
			value = value.toLowerCase();
			String value1 = value.substring(1, value.length());
			value = value.substring(0, 1).toUpperCase() + value1;
		}
		return value;
	}

	public static String[] getSplitOfStrings(String value) {
		String returnValue[] = new String[2];
		if (value != null) {
			String str1[] = value.split(" ", 100);
			StringBuffer str2 = new StringBuffer();
			StringBuffer str3 = new StringBuffer();

			boolean lenghtAlreadyMet = false;
			for (int i = 0; i < str1.length; i++) {
				if (str2.toString().length() + str1[i].length() <= 40
						&& !lenghtAlreadyMet) {
					str2.append(str1[i]);
					str2.append(" ");
				} else {
					lenghtAlreadyMet = true;
					str3.append(str1[i]);
					str3.append(" ");
				}
			}
			if (str3 != null && str3.toString().length() > 0) {
				str3.append("Only");
			} else {
				str3.append("Only");
			}
			returnValue[0] = str2.toString();
			returnValue[1] = str3.toString();
		}
		return returnValue;
	}

	public static String companyNameDisplay(String value) {
		StringBuffer returnValue = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			returnValue.append(value.charAt(i));
			returnValue.append(" ");
		}

		return returnValue.toString().toUpperCase();
	}

	public static String[] getSplitOfStrings(String value, int length) {
		String str1[] = value.split(" ", 100);
		StringBuffer str2 = new StringBuffer();
		StringBuffer str3 = new StringBuffer();
		String returnValue[] = new String[2];
		boolean lenghtAlreadyMet = false;
		for (int i = 0; i < str1.length; i++) {
			// System.out.println(str1[i]);
			if ((str2.toString().length() + str1[i].length()) <= length
					&& !lenghtAlreadyMet) {
				str2.append(str1[i]);
				str2.append(" ");
			} else {
				lenghtAlreadyMet = true;
				str3.append(str1[i]);
				str3.append(" ");
			}
		}

		returnValue[0] = str2.toString();
		returnValue[1] = str3.toString();

		return returnValue;
	}

	public static String[] getSplitOfStrings(String value, String delimeter) {
		if (value != null) {
			String str1[] = value.split(delimeter);
			return str1;
		} else {
			return null;

		}
	}
	
	public static String addValues(String value, String delimeter,String newValue) {
		if (value != null) {
			String str1[] = value.split(delimeter);
			StringBuffer newString=new StringBuffer();
			for(int i=0;i<str1.length;i++){
				if(i>0){
					newString.append(",");
				}
				newString.append(str1[i]);
			}
			if(str1!=null && str1.length>0){
				newString.append(",");
			}
			newString.append(newValue);
			return newString.toString();
		} else {
			return newValue;
		}
	}
	
	public static String removeValues(String value, String delimeter,String delValue) {
		if (value != null) {
			String str1[] = value.split(delimeter);
			StringBuffer newString=new StringBuffer();
			for(int i=0;i<str1.length;i++){
				if(!str1[i].equalsIgnoreCase(delValue)){
					if(i>0 && !newString.toString().isEmpty()){
						newString.append(",");
					}
					newString.append(str1[i]);
				}
			}
			
			return newString.toString();
		} else {
			return null;
		}
	}

	public static String getRemoveFirstSetOfChars(String value, char ch) {
		StringBuffer valueNew = new StringBuffer();
		if (value != null) {
			int flag = 0;
			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) == ch && flag == 0) {
				} else {
					flag++;
					valueNew.append(value.charAt(i));
				}
			}
		} else {
			return null;
		}
		System.out.println(valueNew.toString());
		return valueNew.toString();
	}

	public static double doubleValue(int hh, int mm) {
		StringBuffer value = new StringBuffer();
		value.append(hh);
		value.append(".");
		if (mm < 9) {
			value.append("0" + mm);
		}
		if (mm > 9) {
			value.append(mm);
		}
		// System.out.println("value.toString() " +value.toString());
		return Double.valueOf(value.toString());
	}

	public static double doubleValue(double hh, double mm) {
		StringBuffer value = new StringBuffer();
		value.append(hh);
		value.append(".");
		if (mm < 9) {
			value.append("0" + mm);
		}
		if (mm > 9) {
			value.append(mm);
		}
		// System.out.println("value.toString() " +value.toString());
		return Double.valueOf(value.toString());
	}

	public static double calculateEff(double achQty, double normsPerHour,
			double prdHours) {
		int hr = SukiAppUtil.getHoursFromTime(prdHours);
		double mm = SukiAppUtil.getMinuteFromTime1(prdHours);
		// System.out.println(" mm  "+mm);
		// System.out.println("(mm/60 "+(mm/60));
		int m = 0;
		if (mm > 0) {
			double a = (mm / 60);
			m = (int) (a * 100);
			// System.out.println(" temp  "+m);
		}
		double prdHrsTemp = SukiAppUtil.doubleValue(hr, m);
		// System.out.println(" prdHrs  "+prdHrsTemp);
		// System.out.println(" normsPerHour  "+normsPerHour);

		if (prdHrsTemp > 0 && achQty > 0) {
			double temp1 = (achQty / (getExpQty(normsPerHour, prdHours))) * 100;
			// System.out.println(" temp1  "+temp1);
			return temp1;
		} else {
			return 0;
		}
	}

	public static double getExpQty(double normsPerHour, double prdHours) {
		int hr = SukiAppUtil.getHoursFromTime(prdHours);
		double mm = SukiAppUtil.getMinuteFromTime1(prdHours);
		// System.out.println(" mm  "+mm);
		// System.out.println("(mm/60 "+(mm/60));
		int m = 0;
		if (mm > 0) {
			double a = (mm / 60);
			m = (int) (a * 100);
			// System.out.println(" temp  "+m);
		}
		double prdHrsTemp = SukiAppUtil.doubleValue(hr, m);
		// System.out.println(" prdHrs  "+prdHrsTemp);
		// System.out.println(" normsPerHour  "+normsPerHour);

		return (prdHrsTemp * normsPerHour);

	}

	public static String getNextValueFromList(List<SelectItem> list,
			String curValue) {
		Iterator<SelectItem> iterator = list.iterator();
		String nextValue = null;
		while (iterator.hasNext()) {
			SelectItem item = iterator.next();
			String value = (String) item.getValue();
			if (curValue.equalsIgnoreCase(value)) {
				if (iterator.hasNext()) {
					SelectItem nextItem = iterator.next();
					// System.out.println(" Value Next "+nextItem.getValue());
					nextValue = (String) nextItem.getValue();
					return nextValue;
				}
			}
		}
		if (nextValue == null) {
			nextValue = curValue;
		}
		return nextValue;

	}

	public static List<Date> getDates(Date fromDate, Date toDate) {
		List<java.util.Date> dates = new ArrayList<java.util.Date>();
		//int noOfDays = (int) dateDifference(fromDate, toDate);
		/*
		 * for(int i=1;i<=noOfDays;i++) {
		 * System.out.println(addUtilDate(fromDate, i)); }
		 */
		return dates;
	}

	public static List<java.sql.Date> getDates(java.sql.Date fromDate,
			java.sql.Date toDate) {
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		//int noOfDays = (int) dateDifference(fromDate, toDate);
		/*
		 * for(int i=1;i<=noOfDays;i++) {
		 * System.out.println(addUtilDate(fromDate, i)); }
		 */
		return dates;
	}

	public static String getFileContent(String fileName) {
		FileReader fr;

		StringBuffer value = new StringBuffer();
		try {
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String s = "";
			while ((s = br.readLine()) != null) {
				value.append(s);
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value.toString();
	}

	

	public static double getEstimatePrdTime(double planQtyTemp, double norms) {
		if (planQtyTemp > 0 && norms > 0) {
			int estTime = (int) (planQtyTemp / norms);
			int estTimeMM = (int) (planQtyTemp % norms);
			return doubleValue(estTime, estTimeMM);
		} else
			return 0;

	}

	public static List<String> removeDuplicate(List<String> valueList) {
		List<String> retList = new ArrayList<String>();
		if (valueList != null) {
			for (int i = 0; i < valueList.size(); i++) {
				boolean exist = false;
				for (int j = 0; j < retList.size(); j++) {
					if (retList.get(j) != null
							&& retList.get(j)
									.equalsIgnoreCase(valueList.get(i))) {
						exist = true;
					}
				}
				if (!exist) {
					retList.add(valueList.get(i));
				}
			}
		}
		return retList;
	}

	public static String[] getSplitOfStrings(String value, int perLineLength,
			int noOfLines) {
		if (value == null || value.equals("")) {
			return new String[] { "" };
		}
		int noOfTimes = value.length() / perLineLength;
		++noOfTimes;
		// System.out.println(" noOfTimes "+noOfTimes);
		if (noOfTimes > noOfLines) {
			noOfTimes = noOfLines;
		}
		// System.out.println(" noOfTimes "+noOfTimes);
		String str1[] = new String[noOfTimes];
		int ctr = 0;
		StringBuilder temp = new StringBuilder();

		if (value.length() <= perLineLength) {
			str1[0] = value;
			return str1;
		} else {
			for (int i = 0; i < value.length(); i++) {
				temp.append(value.charAt(i));
				if (temp.length() == perLineLength && i < value.length()) {
					temp.append("-");
					str1[ctr] = temp.toString();
					ctr++;
					temp = new StringBuilder();
				}
				if (i + 1 >= value.length()) {
					str1[ctr] = temp.toString();
					temp = new StringBuilder();
					break;
				}
				if (ctr >= noOfTimes) {
					break;
				}
			}

			return str1;
		}
	}

	

	public static String getViewCardDetailsQueryNew(String partNo,
			Date fromDate, Date toDate, String considerDate, String status,
			String cardType) {
		StringBuilder queryStr = new StringBuilder();
		queryStr.append(" select a.cardNo from JobCardDomain a,ProductDomain b where ");
		if (cardType != null && !cardType.equalsIgnoreCase("All")) {
			if (cardType.equalsIgnoreCase("Rework Card")) {
				queryStr.append(" a.reworkCard='Yes'");
			} else if (cardType.equalsIgnoreCase("Normal Card")) {
				queryStr.append(" a.reworkCard='No' and a.devCard='No'");
			} else if (cardType.equalsIgnoreCase("Development Card")) {
				queryStr.append("  a.devCard='Yes'");
			}
		} else {
			queryStr.append(" a.oldCardIssue='No'");
		}

		if (partNo != null && !partNo.equalsIgnoreCase("All")) {
			queryStr.append(" and a.prodCd=b.prodCode and b.partNumber='");
			queryStr.append(partNo);
			queryStr.append("' ");
		} else {
			queryStr.append(" and a.prodCd=b.prodCode ");
		}

		if (considerDate != null && considerDate.equalsIgnoreCase("Yes")) {
			queryStr.append(" and a.issueDate>='");
			queryStr.append(fromDate);
			queryStr.append("'");
			queryStr.append(" and a.issueDate<='");
			queryStr.append(toDate);
			queryStr.append("'");
		}
		if (status != null && !status.equalsIgnoreCase("ALL")) {
			queryStr.append(" and a.cardStatus='");
			queryStr.append(status);
			queryStr.append("'");
		}
		queryStr.append(" order by a.cardNo");
		// System.out.println("SQL \n"+queryStr.toString());
		return queryStr.toString();
	}

	public static String getTariffWiseInvoiceQuery(String custName,
			String tariff, Date fromDate, Date toDate) {
		StringBuilder queryStr = new StringBuilder();
		queryStr.append(" select a from InvoiceMasterDomain a");
		int i = 0;
		if (custName != null && !custName.equalsIgnoreCase("All")) {
			queryStr.append(",CustomerDomain b where a.custCode=b.custCode  and ");
			queryStr.append(" b.custName='");
			queryStr.append(custName);
			i++;
		}

		if (tariff != null && !tariff.equalsIgnoreCase("All")) {
			if (i > 0) {
				queryStr.append(" and ");
				queryStr.append("  a.tariffSubHead='");
				queryStr.append(tariff);
				queryStr.append("'");
			} else {
				queryStr.append(" where a.tariffSubHead='");
				queryStr.append(tariff);
				queryStr.append("'");
				i++;
			}
		}

		if (i > 0) {
			queryStr.append(" and ");
			queryStr.append("a.invDate>=?");
			queryStr.append(" and  a.invDate<=?");
		} else {

			queryStr.append(" where ");
			queryStr.append("a.invDate>=?");
			queryStr.append(" and  a.invDate<=?");
		}

		queryStr.append(" order by  a.invDate");

		// System.out.println("SQL \n"+queryStr.toString());
		return queryStr.toString();
	}

	public static String getTariffGroupList(String custName, String tariff,
			Date fromDate, Date toDate) {
		StringBuilder sql = new StringBuilder();
		sql.append("select a.tariffSubHead from InvoiceMasterDomain a ,CustomerDomain b where a.custCode=b.custCode and a.invDate>=? and a.invDate<=?");

		if (custName != null && !custName.equalsIgnoreCase("All")) {
			sql.append(" and b.custName='");
			sql.append(custName);
			sql.append("'");
		}
		if (tariff != null && !tariff.equalsIgnoreCase("All")) {
			sql.append(" and a.tariffSubHead='");
			sql.append(tariff);
			sql.append("'");
		}
		sql.append(" group by a.tariffSubHead order by  a.tariffSubHead");
		/*
		 * queryStr.append(" select a.tariffSubHead from InvoiceMasterDomain a");
		 * int i=0; if(custName!=null && !custName.equalsIgnoreCase("All")){
		 * queryStr
		 * .append(",CustomerDomain b where a.custCode=b.custCode  and ");
		 * queryStr.append(" b.custName='"); queryStr.append(custName); i++; }
		 * 
		 * if(tariff!=null && !tariff.equalsIgnoreCase("All")){ if(i>0){
		 * queryStr.append(" and "); queryStr.append("  a.tariffSubHead='");
		 * queryStr.append(tariff); queryStr.append("'"); }else{
		 * queryStr.append(" where a.tariffSubHead='"); queryStr.append(tariff);
		 * queryStr.append("'"); i++; } }
		 * 
		 * if(i>0){ queryStr.append(" and "); queryStr.append("a.invDate>=?");
		 * queryStr.append(" and  a.invDate<=?");
		 * queryStr.append(" group by a.tariffSubHead order by  a.tariffSubHead"
		 * ); }else{ queryStr.append(" where a.invDate>=?");
		 * queryStr.append(" and  a.invDate<=?");
		 * queryStr.append(" group by a.tariffSubHead order by  a.tariffSubHead"
		 * ); }
		 */
		System.out.println("SQL \n" + sql.toString());
		return sql.toString();
	}

	public static String dayColorLabelNew(int year, String month, int day,
			double hours) {
		String temp = "";
		StringBuilder date = new StringBuilder();
		date.append(day);
		date.append("/");
		date.append(month);
		date.append("/");
		date.append(year);
		// System.out.println(" Date is " +date.toString());
		// System.out.println(" Date is "
		// +ERPAppUtil.getDateFromString2(date.toString()));
		StringBuilder buff = new StringBuilder();
		String lableDay = "";
		try {
			lableDay = SukiAppUtil.getDay(SukiAppUtil.getDateFromString2(date
					.toString()));
		} catch (Exception e) {
		}
		if (lableDay.startsWith("Sun")) {
			buff.append("width:30px;text-align:right;background:pink");
			temp = buff.toString();
		} else if (hours > 8) {
			buff.append("width:30px;text-align:right;background:red");
			temp = buff.toString();
		} else if (hours == 8) {
			buff.append("width:30px;text-align:right;background:green;color:white");
			temp = buff.toString();
		} else if (hours < 8) {
			buff.append("width:30px;text-align:right;background:yellow");
			temp = buff.toString();
		}
		// else{
		// buff.append("width:30px;text-align:right;background:RGB(234,190,220)");//Blue
		// temp=buff.toString();
		// }
		return temp;
	}

	public static int getEmpCode(String value, String delimeter) {
		String retValue = null;
		if (value != null && !value.equalsIgnoreCase("Any")&& !value.equalsIgnoreCase("ALL")) {
			retValue = value.substring(value.indexOf("/") + 1);
		}
		//System.out.println(" retValue ->" + retValue);
		if (retValue != null && retValue.length() > 0
				&& isAlphaNumeric(retValue)) {
			return Integer.parseInt(retValue);
		}
		return 0;
	}
	
	public static String getEmpName(String value, String delimeter) {
		String retValue = null;
		if (value != null && !value.equalsIgnoreCase("ALL")&& !value.equalsIgnoreCase("Any")) {
			retValue = value.substring(0,value.indexOf("/"));
		}
		//System.out.println(" retValue ->" + retValue);
		
		return retValue;
	}
	
	public static int getEmpCodeNew(String value, String delimeter) {
		String retValue = null;
		if (value != null && !value.equalsIgnoreCase("Any")&& !value.equalsIgnoreCase("ALL")) {
			retValue = value.substring(value.indexOf(delimeter) + 1);
		}
		//System.out.println(" retValue ->" + retValue);
		if (retValue != null && retValue.length() > 0 && isAlphaNumeric(retValue)) {
			return Integer.parseInt(retValue);
		}
		return 0;
	}
	
	public static String getEmpNameNew(String value, String delimeter) {
		String retValue = null;
		if (value != null && !value.equalsIgnoreCase("ALL")&& !value.equalsIgnoreCase("Any")) {
			retValue = value.substring(0,value.indexOf(delimeter));
		}
		//System.out.println(" retValue ->" + retValue);
		
		return retValue;
	}

	public static void getSystemProperties(String md) throws Exception {
		Properties properties = System.getProperties();
		Set<Object> sysPropertiesKeys = properties.keySet();
		for (Object key : sysPropertiesKeys) {
			System.out.println(key + " ="
					+ properties.getProperty((String) key));
		}

		InetAddress ip = InetAddress.getLocalHost();
		// System.out.println("Current IP address : " + ip.getHostAddress());
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		// System.out.print("Current MAC address : ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i],
					(i < mac.length - 1) ? "-" : ""));
		}
		//System.out.println(sb.toString());
		if (!sb.toString().equalsIgnoreCase(md)) {
			System.out.print("Invalid License");
		} else {
			System.out.print("SUKI License Validated Sucessfully");
		}
	}

	public static String getMacAddress() throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		//System.out.println("Current IP address : " + ip.getHostAddress());
		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();
		
		//System.out.println("Current IP address : " + network.getDisplayName());;
		//System.out.println("Current IP address : " + mac.length);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i],
					(i < mac.length - 1) ? "-" : ""));
			 //System.out.println(mac[i]);
		}
		 //System.out.println(sb.toString());
		return sb.toString();
	}

	public static String geIpcAddress(String md) throws Exception {
		InetAddress ip = InetAddress.getLocalHost();
		// System.out.println("Current IP address : " + ip.getHostAddress());
		return ip.getHostAddress();
	}

	
	

	public static String getMergeString(String value1, String value2,
			String delimeter) {
		StringBuilder value3 = new StringBuilder();

		if (value1 != null && value2 != null) {
			value3.append(value1);
			value3.append(delimeter);
			value3.append(value2);
		} else {
			value3.append(SukiAppUtil.replaceWithNull(value1));
			value3.append(SukiAppUtil.replaceWithNull(value2));
		}

		return value3.toString();
	}

	

	public static void createLicFiles(List<String> content, String fileName) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("D:\\ERPTextFiles\\Lic\\2017\\EncriptLIC2017\\" + fileName,
					"UTF-8");
			for (int i = 0; i < content.size(); i++) {
				writer.println(content.get(i));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	public static void getMacAddressOldway() throws Exception {
		String command = "ipconfig /all";
		Process p = Runtime.getRuntime().exec(command);

		BufferedReader inn = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		Pattern pattern = Pattern.compile(".*Physical Addres.*: (.*)");

		while (true) {
			String line = inn.readLine();

			if (line == null)
				break;

			Matcher mm = pattern.matcher(line);
			if (mm.matches()) {
				System.out.println(mm.group(1));
			}
		}
	}

	

	public static boolean isValidExciseChpNo(String tariffNo) {
		if (tariffNo != null
				&& (!tariffNo.equalsIgnoreCase("Any") || !tariffNo
						.equalsIgnoreCase(""))) {
			return true;
		} else {
			return false;
		}

	}
	public static int getRemainder(double value){
		
		String val=SukiAppUtil.formatDouble(value);
		if(val!=null){
			int startLen = val.indexOf(".");
			System.out.println(startLen);
			
			String value1=val.substring(++startLen, (val.length()));
			//System.out.println(value1);
			int result=Integer.parseInt(value1);
			return result;
		}else{
			return 0;
		}
		
	}
	public static Date getDate(int dd,int mm,int year){
		StringBuffer fromDateStr=new StringBuffer();
		if(dd<10){
			fromDateStr.append("0");
			fromDateStr.append(dd);
		}else{
			fromDateStr.append(dd);
		}
		
		fromDateStr.append("/");
		if(mm<10){
			fromDateStr.append("0");
			fromDateStr.append(mm);
		}else{
			fromDateStr.append(mm);
		}
		fromDateStr.append("/");
		fromDateStr.append(year);
		return getDateFromString(fromDateStr.toString());
		
	}
	
	public static java.sql.Date getDateSql(int dd,int mm,int year){
		StringBuffer fromDateStr=new StringBuffer();
		if(dd<10){
			fromDateStr.append("0");
			fromDateStr.append(dd);
		}else{
			fromDateStr.append(dd);
		}
		
		fromDateStr.append("/");
		if(mm<10){
			fromDateStr.append("0");
			fromDateStr.append(mm);
		}else{
			fromDateStr.append(mm);
		}
		fromDateStr.append("/");
		fromDateStr.append(year);
		return getDateFromString(fromDateStr.toString());
		
	}
	
	public static String createBatchCode(String girNo, int rowId){
		StringBuffer codes=new StringBuffer();
		codes.append(girNo);
		codes.append("/");
		codes.append(rowId);
		return codes.toString();
		
	}
	
	
	
	public static int getOtherReasonHashmapValue(HashMap<String, Integer> otherReasonMap,String key){
		try{
			if(otherReasonMap!=null){
					int value= otherReasonMap.get(key);
					//System.out.println("Key "+key  +" value "+value);
					return value;
			}else{
				return 0;
			}
		}catch(Exception e){
			return 0;
		}
	}
	public static boolean isAlreadyExist(List<String>list,String newValue){
		if(list!=null){
		 for(int j=0;j<list.size();j++){
			 if(list.get(j)!=null && list.get(j).equalsIgnoreCase(newValue)){
				 return true;
			 }
		 }
		}
		
		return false;
	}
	
	public static String getSqlCondition(List<String>list,String colName,String andOr){
		StringBuffer sql=null;
		if(list!=null && list.size()>0){
			sql=new StringBuffer();
			if(list.size()>1){
				sql.append("( ");
			}else{
				andOr=" And ";
				sql.append(andOr);
			}
		 for(int j=0;j<list.size();j++){
			 if(j>0){
				 sql.append(" ");
				 sql.append(andOr);
				 sql.append(" ");
			 }
			 sql.append(colName);
			 sql.append("=");
			 sql.append("'");
			 sql.append(list.get(j));
			 sql.append("'");
		 }
		 if(list.size()>1){
			sql.append( " )");
		 }
	
		}
		if(sql!=null){
			return sql.toString();
		}else{
			return null;
		}
	}
	
	public static boolean isEmpty(String value){
		if(value==null){
			return true;
		}else if(value!=null && (value.equalsIgnoreCase("Any") ||value.equalsIgnoreCase("-Select-") || value.equalsIgnoreCase("-") || value.isEmpty())){
			return true;
		}
		return false;
	}
	
	public static String getEmpName(String empName,String oldEmdcd,int empId){
			StringBuilder name=new StringBuilder();
			name.append(empName);
			name.append("-");
			name.append(oldEmdcd);
			name.append("/");
			name.append(empId);
			return name.toString();	
	}
	
	
	 
	 public static double getValueFromString(String valueStr){
		 double value=0;
		 try{
			 if(!isEmpty(valueStr)){
				 value=Double.parseDouble(valueStr);
			 }else{
				 return 0;
			 }
		 }catch(Exception e){
			// System.out.println(" valueStr " +valueStr);
		 }
		 return value;
	 }
	 public static int getValueIntFromString(String valueStr){
		 int value=0;
		 try{
			 value=Integer.parseInt(valueStr);
		 }catch(Exception e){}
		 return value;
	 }
	 
	 public static double getWeight(double no1, double finalWt){
		 double value=0;
		 value=no1 * finalWt;
		 if(value>0){
			 value=value/1000;
		 }
		 return value;
	 }

//	 public static String sendEmailDc(String contactPerson,String emailId,String fileName){
//			try {
//				StringBuilder message=new StringBuilder();
//				message.append("<h2><font color='blue'> Dear "+contactPerson+",</h2><br/>");
//				message.append("<h3><font color='blue'> Greetings of the day! </h3>");
//			//	message.append("<p> Thanks for your booking.</a>");
//			//	message.append("<p> Your Booking for the Puja "+SukiAppUtil.replaceWithNull(pujaName) +" is confirmed and Booking id is "+bookingId+"</p>");
//			//	message.append("<p> Benifits of this Puja are ... "+SukiAppUtil.replaceWithNull(details) +"</p>");
//				message.append("<p>your order products will be delivered within 50 mins</p>");
//				message.append("<p><font color='red'> NOTE : This is a system generated mail. Please do not reply to this email ID.</p>");
//				message.append("<p><font color='blue'> Please write to your suggestion/feedback to wrightOfficeSolutions@gmail.com.</p>");
//				message.append("<br/><br/><p> Thanks & Regards, </p>");
//			//	message.append("<p>www.panditonline.com </p>");
//		//		String emailids[] = SukiAppUtil.getSplitOfStrings(emailId, ",");
//				try {
//					System.out.println("email id"+emailId);
//					SUKIEMail.sendMailNew(emailId, "Delivery Chalan", message.toString(),fileName, true);
//					System.out.println("Email has been sent ");
//					//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email has been sent"));
//				} catch (MessagingException e) {
//					// TODO Auto-generated catch block
//					System.out.println("Email is not sent, please check your internet connection");
//					//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Email is not sent, please check your internet connection"));
//					e.printStackTrace();
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			return "";
//		}
	
	/*public static void generateExcel(){
		 WritableWorkbook myFirstWbook = null;
	        try {
	            myFirstWbook = Workbook.createWorkbook(new File("C:\\Users\\prasad\\Desktop\\MyFirstExcel.xls"));

	            // create an Excel sheet
	            WritableSheet excelSheet = myFirstWbook.createSheet("Sheet 1", 0);

	            // add something into the Excel sheet
	            Label label = new Label(0, 0, "Test Count");
	            excelSheet.addCell(label);

	            Number number = new Number(0, 1, 1);
	            excelSheet.addCell(number);

	            label = new Label(1, 0, "Result");
	            excelSheet.addCell(label);

	            label = new Label(1, 1, "Passed");
	            excelSheet.addCell(label);

	            number = new Number(0, 2, 2);
	            excelSheet.addCell(number);

	            label = new Label(1, 2, "Passed 2");
	            excelSheet.addCell(label);

	            myFirstWbook.write();


	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {

	            if (myFirstWbook != null) {
	                try {
	                    myFirstWbook.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }


	        }

	    }*/
	 public static String getDatestring(Object value) {
		 String sqlString=new String();
		 if(value!=null) {
			 if(value.toString().contains("/")) {
				String[] dateArray=value.toString().split("/");
				if(dateArray!=null && dateArray.length==2) {
					sqlString="month(date)=%d and year(date)=%d";
					sqlString=sqlString.format(sqlString, Integer.parseInt(dateArray[0]),Integer.parseInt("20"+dateArray[1]));
				}
				if(dateArray!=null && dateArray.length==3) {
					sqlString="day(date)=%d and month(date)=%d and year(date)=%d";
					sqlString=sqlString.format(sqlString, Integer.parseInt(dateArray[0]),Integer.parseInt(dateArray[1]),Integer.parseInt("20"+dateArray[2]));
				}
			 }else {
				 sqlString="year(date)=%d";
				 String year="20"+value.toString();
				 sqlString=sqlString.format(sqlString, Integer.parseInt(year));
			 }
			}
		 return sqlString;
	 }
	public static <T> String filterFormat(T ObjectType,String sortField,String sortOrder,Map<String,Object> filters) {
		StringBuffer sql=new StringBuffer();
		int count=0;
			
		if(ObjectType.getClass().equals(DeliveryChalanMaster.class) || ObjectType.getClass().equals(BillMasterDomain.class) || ObjectType.getClass().equals(QuotationMaster.class) || ObjectType.getClass().equals(SalesPaymentsDomain.class)) {
			count=0;
			List<String> keyList = Arrays.asList("companyId.compId", "supId.supCode");
		for(Map.Entry<String, Object> map:filters.entrySet()) {
			if(map.getKey().equalsIgnoreCase("Date")) {
				sql.append(getDatestring(map.getValue()));
			}else if(map.getKey().equalsIgnoreCase("company")) {
				sql.append("companyId in(select compId from Company where compName like '%");
				sql.append(map.getValue().toString());
				sql.append("%')");
			}else {
				sql.append(map.getKey());
				if(keyList.contains(map.getKey())) sql.append(" = '");
				else sql.append(" like '%");
				sql.append(map.getValue().toString());
				if(keyList.contains(map.getKey())) sql.append("'");
				else sql.append("%'");
			}
			if(count!=filters.size()-1)
				sql.append(" and ");
			count++;
		}
	//	if(sortField==null)
	//		sql.append(" order by date desc");
		}else if(ObjectType.getClass().equals(PurchaseOrderMaster.class) || ObjectType.getClass().equals(PurchaseBillMaster.class) || ObjectType.getClass().equals(InwardMaster.class)) {
			count=0;
		for(Map.Entry<String, Object> map:filters.entrySet()) {
			if(map.getKey().equalsIgnoreCase("Date")) {
				sql.append(getDatestring(map.getValue()));
			}else if(map.getKey().equalsIgnoreCase("supplier")) {
				sql.append("supId in(select supCode from SupplierDomain where name like '%");
				sql.append(map.getValue().toString());
				sql.append("%')");
			}else {
				sql.append(map.getKey());
				sql.append("='");
				sql.append(map.getValue().toString());
				sql.append("'");
			}
			if(count!=filters.size()-1)
				sql.append(" and ");
			count++;
		}
	//	if(sortField==null)
	//		sql.append(" order by date desc");
		}else {
			count=0;
			System.out.println("FILTERS-----"+filters);
			for(Map.Entry<String, Object> map:filters.entrySet()) {
				if (map.getValue() instanceof String[]) {
			        String[] strArray = (String[]) map.getValue();
			        if(strArray.length>0) {
			        	if(count!=0)
							sql.append(" and ");
			        	sql.append("type in (");
			        	StringJoiner joiner=new StringJoiner("','","'","'");
			        	for(int i=0;i<strArray.length;i++) {
			        		joiner.add(strArray[i].toString());
			        	}
			        	sql.append(joiner.toString());
			        	sql.append(")");
			        	count++;
			        }
			    }else {
			    	if(count!=0)
						sql.append(" and ");
					sql.append(map.getKey());
					sql.append(" like '%");
					sql.append(String.valueOf(map.getValue()));
					System.out.println("MAP-----"+String.valueOf(map.getValue()));
					sql.append("%'");
					count++;
			    }
				}
		}
		
//		if(sortField!=null) {
//			sql.append(" order by ");
//			sql.append(sortField);
//			sql.append(" ");
//			if(sortOrder.equalsIgnoreCase("ASCENDING"))
//			sql.append(sortOrder.substring(0, 3));
//			else
//			sql.append(sortOrder.substring(0, 4));
//		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	public static String decimalPattern(int decimal) {
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
	public static String round(double value) {
	    DecimalFormat df = new DecimalFormat("####0.00");
	    return df.format(value);
	}
	public static void main(String a[]) throws Exception {
	//	generateExcel();
//		System.out.println(getDatestring("2018"));
//		StringJoiner joiner=new StringJoiner("/","","");
//		joiner.add("prasad");
//		joiner.add("mani");
//		System.out.println(joiner);
		
//		DecimalFormat df = new DecimalFormat("#,###,##0.00");
//        System.out.println(df.format(0));
//        double d=Double.valueOf(df.format(0));
//        System.out.println(d);
//        System.out.println(df.format(364565.1454));
		System.out.println(round(1.99));
 
}
}