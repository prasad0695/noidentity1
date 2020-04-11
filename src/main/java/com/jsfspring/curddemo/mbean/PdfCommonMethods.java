package com.jsfspring.curddemo.mbean;

import java.io.IOException;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.jsfspring.curddemo.utills.SukiAppUtil;

public class PdfCommonMethods {
	public static PdfFont timesNewRoman=null;
	public static Style font9=null;
	public static Style font10=null;
	public static Style font11=null;
	public static Style font8=null;
	
	public static void fontNames(){
		try {
			timesNewRoman=PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
			font9=new Style().setFont(timesNewRoman).setFontSize(9).setFontColor(Color.BLACK);
			font10=new Style().setFont(timesNewRoman).setFontSize(10).setFontColor(Color.BLACK);
			font11=new Style().setFont(timesNewRoman).setFontSize(11).setFontColor(Color.BLACK);
			font8=new Style().setFont(timesNewRoman).setFontSize(8).setFontColor(Color.BLACK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String replaceNull(String value){
		if(SukiAppUtil.isEmpty(value)){
			return "";
		}
		return value;
	}
	public static Cell tableHeader(String value,Style font,TextAlignment alignment,int colspan){
		Cell cell=new Cell(1,colspan);
		cell.add(value).addStyle(font).setTextAlignment(alignment).setBold();
		return cell;
	}
	public static Cell tableContent(String value,Style font,TextAlignment alignment,int colspan){
		Cell cell=new Cell(1,colspan);
		cell.add(new Paragraph(value)).addStyle(font).setTextAlignment(alignment).setBorder(Border.NO_BORDER);
		return cell;
	}
	public static Cell tableContent(String value,Style font,TextAlignment alignment,int colspan,int height){
		Cell cell=new Cell(1,colspan).setHeight(height);
		cell.add(new Paragraph(value)).addStyle(font).setTextAlignment(alignment).setBorder(Border.NO_BORDER);
		return cell;
	}
	public static Cell table(String value,Style font,TextAlignment alignment,int colspan){
		Cell cell=new Cell(1,colspan);
		cell.add(new Paragraph(value)).addStyle(font).setTextAlignment(alignment).setBorder(new SolidBorder(Color.GRAY,0));
		return cell;
	}
	public static Cell tableWithBorder(String value,Style font,TextAlignment alignment,int colspan){
		Cell cell=new Cell(1,colspan);
		cell.add(new Paragraph(value)).addStyle(font).setTextAlignment(alignment).setBorder(new SolidBorder(Color.BLACK,0.5f));
		return cell;
	}
	public static Cell horizontalLine(int colspan,float height){
		Cell cell=new Cell(1,colspan);
		cell.setBorderBottom(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setHeight(height);
		return cell;
	}
	public static Paragraph cellContent(String value,int fontSize,TextAlignment alignment){
		value=replaceNull(value);
		Paragraph para=new Paragraph(value);
		para.setFontSize(fontSize).setTextAlignment(alignment);
		return para;
	}
}
