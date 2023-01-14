package com.jsfspring.curddemo.mbean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import org.springframework.stereotype.Service;

import com.itextpdf.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.jsfspring.curddemo.entity.BillMasterDomain;
import com.jsfspring.curddemo.entity.BillTransDomain;
import com.jsfspring.curddemo.entity.Company;
import com.jsfspring.curddemo.entity.DeliveryChalanDomain;
import com.jsfspring.curddemo.entity.DeliveryChalanMaster;
import com.jsfspring.curddemo.entity.PurchaseOrderMaster;
import com.jsfspring.curddemo.entity.PurchaseOrderTrans;
import com.jsfspring.curddemo.entity.QuotationMaster;
import com.jsfspring.curddemo.entity.QuotationTrans;
import com.jsfspring.curddemo.entity.SupplierDomain;
import com.jsfspring.curddemo.utills.SukiAppConstants;
import com.jsfspring.curddemo.utills.SukiAppUtil;

@Service
public class PdfDocuments {

	public static byte[] createDc(DeliveryChalanMaster dcmaster){
		int dcNo=dcmaster.getDeliveryNo();
		Company company=dcmaster.getCompanyId();
		List<DeliveryChalanDomain> dcTrans=dcmaster.getDcTransList();
		
		
		byte[] dcPdf=null;
		try{
			PdfCommonMethods.fontNames();
		String dest=SukiAppConstants.DC_FOLDER+dcNo+".pdf";
		ByteArrayOutputStream pdfFileout = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        // Note that it is not necessary to create new PageSize object,
        // but for testing reasons (connected to parallelization) we call constructor here
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(120, 10, 10, 10);
        Table table1 = new Table(3);
        table1.setWidthPercent(100).setMargin(15);
        table1.addCell(PdfCommonMethods.tableContent("GST No: 33KLSPS7376Q1ZJ", PdfCommonMethods.font10, TextAlignment.LEFT, 1));
        table1.addCell(PdfCommonMethods.tableContent("DELIVERY CHALAN", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold().setPaddingTop(-3));
        table1.addCell(PdfCommonMethods.tableContent("Dc.No: "+dcNo, PdfCommonMethods.font10, TextAlignment.RIGHT, 1).setMarginRight(38));
        table1.addCell(PdfCommonMethods.tableContent("Date : "+SukiAppUtil.getDate(dcmaster.getDate()),PdfCommonMethods.font10, TextAlignment.RIGHT,3).setPaddingTop(-3).setMarginRight(38));
       // doc.add(table);
        HeaderHandler handle1=new HeaderHandler(table1);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle1);
        Table table = new Table(2);
        table.setWidthPercent(100).setMargin(40);
        Cell cell=new Cell();
        cell.setHeight(90).setBorder(Border.NO_BORDER);
        cell.add(PdfCommonMethods.cellContent("NO IDENTITY",9,TextAlignment.CENTER).setBold());
        cell.add(PdfCommonMethods.cellContent("(For all the house keeping items that you need)",8,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("No.3/958/1 Bharathiyar Street, Okkiyam Thoraipakkam",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("Chennai-600097. cell:7401534638,9003665600",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("E-mail:noidentity0914@gmail.com",9,TextAlignment.CENTER));
        table.addCell(cell);
        Cell cell1=new Cell();
        cell1.setHeight(90).setBorder(Border.NO_BORDER);
        cell1.add(PdfCommonMethods.cellContent("To.",9,TextAlignment.LEFT));
        cell1.add(PdfCommonMethods.cellContent(company.getCompName(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getArea1(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getArea2(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(PdfCommonMethods.replaceNull(company.getArea3())+","+company.getCity(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getGst(),9,TextAlignment.CENTER));
        table.addCell(cell1);
       
        HeaderHandler handle=new HeaderHandler(table);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle);
    //    doc.add(table);
        Table table3 = new Table(6);
        table3.setWidthPercent(100);
        table3.addCell(PdfCommonMethods.table("S.No", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold());
        table3.addCell(PdfCommonMethods.table("Description", PdfCommonMethods.font9, TextAlignment.CENTER,4).setBold());
        table3.addCell(PdfCommonMethods.table("Quantity", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
        doc.add(table3);
        table3 = new Table(6);
        table3.setWidthPercent(100);
        for(int i=0;i<dcTrans.size();i++){
        	table3.addCell(PdfCommonMethods.table(Integer.toString((i+1)), PdfCommonMethods.font9, TextAlignment.CENTER, 1));
            table3.addCell(PdfCommonMethods.table(dcTrans.get(i).getItemTrans(), PdfCommonMethods.font9, TextAlignment.CENTER,4));
            table3.addCell(PdfCommonMethods.table(SukiAppUtil.roundedOffIntValue(dcTrans.get(i).getNos())+"  "+dcTrans.get(i).getUom().getUomName(), PdfCommonMethods.font9, TextAlignment.CENTER,1));
          }
        doc.add(table3);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
        	 float x = pdfDoc.getPage(i).getPageSize().getWidth();
             float y = pdfDoc.getPage(i).getPageSize().getBottom()+35;
        	 doc.showTextAligned(new Paragraph("Page "+i+"/"+pdfDoc.getNumberOfPages()).setFontSize(9),450, y-26, i,
                     TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	if(i==pdfDoc.getNumberOfPages()){
            doc.showTextAligned(new Paragraph("Customer Signature with seal").setFontSize(10),20, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
            doc.showTextAligned(new Paragraph("(NO IDENTITY)").setFontSize(9).setBold(),450, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	}
        }
        doc.close();
        dcPdf=loadFile(dest);
        return dcPdf;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return dcPdf;
	}
	public static byte[] createPo(PurchaseOrderMaster poMaster){
		int dcNo=poMaster.getPoNo();
		SupplierDomain company=poMaster.getSupId();
		List<PurchaseOrderTrans> dcTrans=poMaster.getPurchaseOrderTransList();
		
		
		byte[] dcPdf=null;
		try{
			PdfCommonMethods.fontNames();
		String dest=SukiAppConstants.PO_FOLDER+dcNo+".pdf";
		// ByteArrayOutputStream pdfFileout = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        // Note that it is not necessary to create new PageSize object,
        // but for testing reasons (connected to parallelization) we call constructor here
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(120, 10, 10, 10);
        Table table1 = new Table(3);
        table1.setWidthPercent(100).setMargin(15);
        table1.addCell(PdfCommonMethods.tableContent("GST No: 33DHSPS0579G1ZT", PdfCommonMethods.font10, TextAlignment.LEFT, 1));
        table1.addCell(PdfCommonMethods.tableContent("PURCHASE ORDER", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold().setPaddingTop(-3));
        table1.addCell(PdfCommonMethods.tableContent("Po.No: "+dcNo, PdfCommonMethods.font10, TextAlignment.RIGHT, 1).setMarginRight(38));
        table1.addCell(PdfCommonMethods.tableContent("Date : "+SukiAppUtil.getDate(poMaster.getDate()),PdfCommonMethods.font10, TextAlignment.RIGHT,3).setPaddingTop(-3).setMarginRight(38));
       // doc.add(table);
        HeaderHandler handle1=new HeaderHandler(table1);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle1);
        Table table = new Table(2);
        table.setWidthPercent(100).setMargin(40);
        Cell cell=new Cell();
        cell.setHeight(90).setBorder(Border.NO_BORDER);
        cell.add(PdfCommonMethods.cellContent("NO IDENTITY",9,TextAlignment.CENTER).setBold());
        cell.add(PdfCommonMethods.cellContent("(For all the house keeping items that you need)",8,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("No.3/958/1 Bharathiyar Street, Okkiyam Thoraipakkam",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("Chennai-600097. cell:7401534638,9003665600",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("E-mail:noidentity0914@gmail.com",9,TextAlignment.CENTER));
        table.addCell(cell);
        Cell cell1=new Cell();
        cell1.setHeight(90).setBorder(Border.NO_BORDER);
        cell1.add(PdfCommonMethods.cellContent("To.",9,TextAlignment.LEFT));
        cell1.add(PdfCommonMethods.cellContent(company.getName(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getArea1(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getArea2(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(PdfCommonMethods.replaceNull(company.getArea3())+","+company.getCity(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getGstIn(),9,TextAlignment.CENTER));
        table.addCell(cell1);
       
        HeaderHandler handle=new HeaderHandler(table);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle);
    //    doc.add(table);
        Table table3 = new Table(6);
        table3.setWidthPercent(100);
        table3.addCell(PdfCommonMethods.table("S.No", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold());
        table3.addCell(PdfCommonMethods.table("Description", PdfCommonMethods.font9, TextAlignment.CENTER,4).setBold());
        table3.addCell(PdfCommonMethods.table("Quantity", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
        doc.add(table3);
        table3 = new Table(6);
        table3.setWidthPercent(100);
        for(int i=0;i<dcTrans.size();i++){
        	table3.addCell(PdfCommonMethods.table(Integer.toString((i+1)), PdfCommonMethods.font9, TextAlignment.CENTER, 1));
            table3.addCell(PdfCommonMethods.table(dcTrans.get(i).getProductTrans(), PdfCommonMethods.font9, TextAlignment.CENTER,4));
            table3.addCell(PdfCommonMethods.table(dcTrans.get(i).getNos()+"  "+dcTrans.get(i).getUom().getUomName(), PdfCommonMethods.font9, TextAlignment.CENTER,1));
          }
        doc.add(table3);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
        	 float x = pdfDoc.getPage(i).getPageSize().getWidth();
             float y = pdfDoc.getPage(i).getPageSize().getBottom()+35;
        	 doc.showTextAligned(new Paragraph("Page "+i+"/"+pdfDoc.getNumberOfPages()).setFontSize(9),450, y-26, i,
                     TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	if(i==pdfDoc.getNumberOfPages()){
            doc.showTextAligned(new Paragraph("(NO IDENTITY)").setFontSize(9).setBold(),450, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	}
        }
        doc.close();
        pdfDoc.close();
        // dcPdf=loadFile(dest);
        return dcPdf;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return dcPdf;
	}
	public static byte[] createQuotation(QuotationMaster quotMaster){
		int dcNo=quotMaster.getQuotationNo();
		Company company=quotMaster.getCompanyId();
		List<QuotationTrans> quotTrans=quotMaster.getQuotTransList();
		
		
		byte[] dcPdf=null;
		try{
			PdfCommonMethods.fontNames();
		String dest=SukiAppConstants.QUOTATION_FOLDER+dcNo+".pdf";
		ByteArrayOutputStream pdfFileout = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        // Note that it is not necessary to create new PageSize object,
        // but for testing reasons (connected to parallelization) we call constructor here
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(120, 10, 10, 10);
        Table table1 = new Table(3);
        table1.setWidthPercent(100).setMargin(15);
        table1.addCell(PdfCommonMethods.tableContent("GST No: 33KLSPS7376Q1ZJ", PdfCommonMethods.font10, TextAlignment.LEFT, 1));
        table1.addCell(PdfCommonMethods.tableContent("QUOTATION", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold().setPaddingTop(-3));
        table1.addCell(PdfCommonMethods.tableContent("Date : "+SukiAppUtil.getDate(quotMaster.getDate()),PdfCommonMethods.font10, TextAlignment.RIGHT,3).setPaddingTop(-3).setMarginRight(38));
       // doc.add(table);
        HeaderHandler handle1=new HeaderHandler(table1);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle1);
        Table table = new Table(2);
        table.setWidthPercent(100).setMargin(40);
        Cell cell=new Cell();
        cell.setHeight(90).setBorder(Border.NO_BORDER);
        cell.add(PdfCommonMethods.cellContent("",9,TextAlignment.CENTER).setBold());
        cell.add(PdfCommonMethods.cellContent("NO IDENTITY",9,TextAlignment.CENTER).setBold());
        cell.add(PdfCommonMethods.cellContent("(For all the house keeping items that you need)",8,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("No.3/958/1 Bharathiyar Street, Okkiyam Thoraipakkam",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("Chennai-600097. cell:7401534638,9003665600",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("E-mail:noidentity0914@gmail.com",9,TextAlignment.CENTER));
        table.addCell(cell);
        if(company!=null) {
        Cell cell1=new Cell();
        cell1.setHeight(90).setBorder(Border.NO_BORDER);
        cell1.add(PdfCommonMethods.cellContent("To.",9,TextAlignment.LEFT));
        cell1.add(PdfCommonMethods.cellContent(company.getCompName(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getArea1(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getArea2(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(PdfCommonMethods.replaceNull(company.getArea3())+","+company.getCity(),9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent(company.getGst(),9,TextAlignment.CENTER));
        table.addCell(cell1);
        }
       
        HeaderHandler handle=new HeaderHandler(table);
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle);
    //    doc.add(table);
        Table table3 = new Table(9);
        table3.setWidthPercent(100);
        table3.addCell(PdfCommonMethods.table("S.No", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold());
        table3.addCell(PdfCommonMethods.table("Description", PdfCommonMethods.font9, TextAlignment.CENTER,4).setBold());
        table3.addCell(PdfCommonMethods.table("UOM", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
        table3.addCell(PdfCommonMethods.table("Rate", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
        table3.addCell(PdfCommonMethods.table("Gst", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
        table3.addCell(PdfCommonMethods.table("Price", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
        doc.add(table3);
        table3 = new Table(9);
        table3.setWidthPercent(100);
        for(int i=0;i<quotTrans.size();i++){
        	 StringJoiner uom=new StringJoiner("/ ","","");
        	 StringJoiner rate=new StringJoiner("/ ","","");
        	 StringJoiner price=new StringJoiner("/ ","","");
        	quotTrans.get(i).getQuotUomList().parallelStream().forEach(j->{uom.add(j.getUomId().getUnitName());rate.add(String.valueOf(SukiAppUtil.formatDouble(j.getPrice())));price.add(String.valueOf(SukiAppUtil.formatDouble(j.getPriceWithGst())));});
        	table3.addCell(PdfCommonMethods.table(Integer.toString((i+1)), PdfCommonMethods.font9, TextAlignment.CENTER, 1));
            table3.addCell(PdfCommonMethods.table(quotTrans.get(i).getItem(), PdfCommonMethods.font9, TextAlignment.CENTER,4));
            table3.addCell(PdfCommonMethods.table(uom.toString(), PdfCommonMethods.font9, TextAlignment.CENTER,1));
            table3.addCell(PdfCommonMethods.table(rate.toString(), PdfCommonMethods.font9, TextAlignment.CENTER,1));
            table3.addCell(PdfCommonMethods.table(String.valueOf(quotTrans.get(i).getGst()), PdfCommonMethods.font9, TextAlignment.CENTER,1));
            table3.addCell(PdfCommonMethods.table(price.toString(), PdfCommonMethods.font9, TextAlignment.CENTER,1));
          }
        doc.add(table3);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
        	 float x = pdfDoc.getPage(i).getPageSize().getWidth();
             float y = pdfDoc.getPage(i).getPageSize().getBottom()+35;
        	 doc.showTextAligned(new Paragraph("Page "+i+"/"+pdfDoc.getNumberOfPages()).setFontSize(9),450, y-26, i,
                     TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	if(i==pdfDoc.getNumberOfPages()){
            doc.showTextAligned(new Paragraph("(NO IDENTITY)").setFontSize(9).setBold(),450, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	}
        }
        doc.close();
        dcPdf=loadFile(dest);
        return dcPdf;
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return dcPdf;
	}
	 
//	public  void OnStartPage(PdfWriter writer, Document document) {
//	    base.OnStartPage(writer, document);
//	    PdfPTable tabHead = new PdfPTable(new float[] { 1F });
//	    PdfPCell cell;
//	    tabHead.WidthPercentage = 100;
//	    cell = new PdfPCell(new Phrase("Header"));
//	    tabHead.AddCell(cell);
//	    tabHead.WriteSelectedRows(0, -1, 150, document.Top, writer.DirectContent);
//	}
		public static byte[] createBill(BillMasterDomain billmaster){
            String userDirectory = Paths.get("")
                    .toAbsolutePath()
                    .toString();
            System.out.println("userDir"+userDirectory);
            System.out.println("userDir"+System.getProperty("user.dir"));
            System.out.println("userhome"+System.getProperty("user.home"));
            String currentDir = System.getProperty("user.dir")+"/src/main/resources/noidentitylogo.png";
            String desktopPath = System.getProperty("user.home");
            String desktopPathModified = desktopPath.replace("\\","/");
//            String desktopPathModified = "/home";
            int billNo=billmaster.getBillNo();
			Company company=billmaster.getCompanyId();
			String invoiceType="";
			if(billmaster.getInvoiceType().equalsIgnoreCase("Direct")){
				invoiceType=billmaster.getInvoiceType()+" Invoice";
			}else{
				invoiceType=billmaster.getDcNos();
			}
			List<BillTransDomain> billTrans=billmaster.getBillTransList();
			
			byte[] dcPdf=null;
			try{
				PdfCommonMethods.fontNames();
			String dest=desktopPathModified+"/INVOICE/savedBill/"+billNo+".pdf";
			ByteArrayOutputStream pdfFileout = new ByteArrayOutputStream();
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
	        // Note that it is not necessary to create new PageSize object,
	        // but for testing reasons (connected to parallelization) we call constructor here
	        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
	        doc.setMargins(210, 10, 30, 10);
	       /* Image image = new Image(ImageDataFactory.create("C:\\Users\\prasad\\Desktop\\WOSlogo.png")).setWidthPercent(100).setHeight(70);
	        Table table = new Table(1);
	        table.setWidthPercent(100);
	        table.addCell(image);
	        doc.add(table);*/
	        Image img = new Image(ImageDataFactory.create(currentDir)).setFixedPosition(12,PageSize.A4.getHeight()-110).setHeight(100).setWidthPercent(95);
	        ImageEventHandler handler = new ImageEventHandler(img);
	        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
	       
	        Table table = new Table(2);
	        table.setWidthPercent(100).setMargin(10).setMarginTop(120);
	        Cell cell1=new Cell(1,1);
	        cell1.setHeight(85);
	        //cell1.setWidthPercent(50);
	        cell1.add(PdfCommonMethods.cellContent("To.",10,TextAlignment.LEFT));
	        cell1.add(PdfCommonMethods.cellContent(company.getCompName(),10,TextAlignment.CENTER).setBold().setPaddingTop(-10));
	        cell1.add(PdfCommonMethods.cellContent(company.getArea1(),10,TextAlignment.CENTER));
	        cell1.add(PdfCommonMethods.cellContent(company.getArea2(),10,TextAlignment.CENTER));
	        cell1.add(PdfCommonMethods.cellContent(PdfCommonMethods.replaceNull(company.getArea3())+","+PdfCommonMethods.replaceNull(company.getCity()),10,TextAlignment.CENTER));
	        cell1.add(PdfCommonMethods.cellContent(company.getGst(),10,TextAlignment.CENTER));
	        table.addCell(cell1);
	        Cell cell=new Cell(1,1);
	        cell.setHeight(85);
	        //cell1.setWidthPercent(50);
	        cell.add(PdfCommonMethods.cellContent("Date          :"+SukiAppUtil.getDate(billmaster.getDate()),10,TextAlignment.LEFT));
	        cell.add(PdfCommonMethods.cellContent("Invoice No  :"+billNo,10,TextAlignment.LEFT).setBold());
	        if(!SukiAppUtil.isEmpty(billmaster.getPoNo()))
	        cell.add(PdfCommonMethods.cellContent("PO No       :"+billmaster.getPoNo(),10,TextAlignment.LEFT));
	        cell.add(PdfCommonMethods.cellContent("Dc            :"+invoiceType,10,TextAlignment.LEFT));
	        table.addCell(cell);
	     //   doc.add(table);
	        HeaderHandler handle1=new HeaderHandler(table);
	        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handle1);
	      //  doc.setMargins(-100 , 36, 36, 36);
	        Table table3;
	        if(billmaster.isGstBill())
	        table3 = new Table(12);
	        else 
	        table3 = new Table(10);
	        table3.setWidthPercent(100);
	        table3.setBorder(new SolidBorder(Color.BLACK,0.5f));
	        table3.addCell(PdfCommonMethods.table("S.No", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold());
	        table3.addCell(PdfCommonMethods.table("ITEM", PdfCommonMethods.font9, TextAlignment.CENTER,4).setBold());
	        table3.addCell(PdfCommonMethods.table("HSN", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
	        table3.addCell(PdfCommonMethods.table("QTY", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
	        table3.addCell(PdfCommonMethods.table("UOM", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
	        table3.addCell(PdfCommonMethods.table("RATE", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
	        if(billmaster.isGstBill()) {
	        table3.addCell(PdfCommonMethods.table("CGST", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
	        table3.addCell(PdfCommonMethods.table("SGST", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
	        }
	        table3.addCell(PdfCommonMethods.table("AMOUNT", PdfCommonMethods.font9, TextAlignment.CENTER,1).setBold());
//	        doc.add(table3);
//	        table3 = new Table(12);
	        table3.setWidthPercent(100);
	        table3.setBorder(new SolidBorder(Color.BLACK,0.5f));
	        for(int i=0;i<billTrans.size();i++){
	        	table3.addCell(PdfCommonMethods.tableWithBorder(Integer.toString((i+1)), PdfCommonMethods.font9, TextAlignment.CENTER, 1));
	            table3.addCell(PdfCommonMethods.tableWithBorder(billTrans.get(i).getProducts(), PdfCommonMethods.font10, TextAlignment.CENTER,4));
	            table3.addCell(PdfCommonMethods.tableWithBorder(String.valueOf(billTrans.get(i).getHsn()), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	            table3.addCell(PdfCommonMethods.tableWithBorder(String.valueOf(SukiAppUtil.formatDoubleNew(billTrans.get(i).getQty(),billTrans.get(i).getUom().getUomId().getDecPattern())), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	            table3.addCell(PdfCommonMethods.tableWithBorder(billTrans.get(i).getUom().getUomName(), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	            table3.addCell(PdfCommonMethods.tableWithBorder(SukiAppUtil.round(billTrans.get(i).getRate()), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	            if(billmaster.isGstBill()) {
	            table3.addCell(PdfCommonMethods.tableWithBorder(String.valueOf(billTrans.get(i).getGst()/2), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	            table3.addCell(PdfCommonMethods.tableWithBorder(String.valueOf(billTrans.get(i).getGst()/2), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	            }
	            table3.addCell(PdfCommonMethods.tableWithBorder(SukiAppUtil.round(billTrans.get(i).getAmount()), PdfCommonMethods.font9, TextAlignment.CENTER,1));
	        }
	        doc.add(table3);
	        table3 = new Table(12);
	        table3.setWidthPercent(100);
	        table3.setBorder(new SolidBorder(Color.BLACK,0.25f));
	        table3.addCell(PdfCommonMethods.table("SUB-TOTAL", PdfCommonMethods.font9, TextAlignment.RIGHT,11));
            table3.addCell(PdfCommonMethods.table(SukiAppUtil.round(SukiAppUtil.roundedOff(billmaster.getTotalWithoutTax())), PdfCommonMethods.font9, TextAlignment.CENTER,1));
          if(billmaster.getFreightCharges()>0)  {
            table3.addCell(PdfCommonMethods.table("Freight Charge", PdfCommonMethods.font9, TextAlignment.RIGHT,11));
            table3.addCell(PdfCommonMethods.table(String.valueOf(billmaster.getFreightCharges()), PdfCommonMethods.font9, TextAlignment.CENTER,1));
          }
	        doc.add(table3);
	        table3 = new Table(12);
	        Table gstTable = new Table(12);
	        table3.setWidthPercent(100).setBorder(new SolidBorder(Color.BLACK,0.25f));
	        doc.add(table3);
	        if(billmaster.isGstBill()) {
	        Map<Double,Double> map=billmaster.getGstValue();
	        for (Map.Entry<Double,Double> entry : map.entrySet()) {
	        	gstTable = new Table(12);
	        	gstTable.addCell(PdfCommonMethods.table(String.valueOf(SukiAppUtil.round(billTrans.stream().filter(i -> i.getGst() == entry.getKey()).mapToDouble(j -> j.getAmount()).sum())),PdfCommonMethods.font9, TextAlignment.RIGHT,3).setBorder(Border.NO_BORDER).setBold());
	        	gstTable.addCell(PdfCommonMethods.table("  CGST "+entry.getKey()/2+"        "+SukiAppUtil.round(entry.getValue()/2),PdfCommonMethods.font9, TextAlignment.LEFT,3).setBorder(Border.NO_BORDER));
	        	gstTable.addCell(PdfCommonMethods.table("  SGST "+entry.getKey()/2+"        "+SukiAppUtil.round(entry.getValue()/2),PdfCommonMethods.font9, TextAlignment.LEFT,3).setBorder(Border.NO_BORDER));
	        	gstTable.addCell(PdfCommonMethods.table("GST "+entry.getKey()+"     ",PdfCommonMethods.font9, TextAlignment.CENTER,2).setBorder(Border.NO_BORDER));
	        	
//	        	gstTable.addCell(PdfCommonMethods.table(String.valueOf(billTrans.stream().filter(i -> i.getGst() == entry.getKey()).mapToDouble(j -> j.getAmount()).sum()),PdfCommonMethods.font9, TextAlignment.RIGHT,3).setBorder(Border.NO_BORDER).setBold());
//	        	gstTable.addCell(PdfCommonMethods.table("  IGST "+entry.getKey()+"        "+SukiAppUtil.round(entry.getValue()),PdfCommonMethods.font9, TextAlignment.LEFT,3).setBorder(Border.NO_BORDER));
//	        	gstTable.addCell(PdfCommonMethods.table(" ",PdfCommonMethods.font9, TextAlignment.LEFT,3).setBorder(Border.NO_BORDER));
//	        	gstTable.addCell(PdfCommonMethods.table("IGST "+entry.getKey()+"     ",PdfCommonMethods.font9, TextAlignment.CENTER,2).setBorder(Border.NO_BORDER));
	        	
	        	gstTable.addCell(PdfCommonMethods.table(SukiAppUtil.round(entry.getValue()),PdfCommonMethods.font9, TextAlignment.CENTER,1).setBorder(Border.NO_BORDER));
		        doc.add(gstTable);
	        }  
	        }
	       /* table3.addCell(PdfCommonMethods.table("1250 ",PdfCommonMethods.font9, TextAlignment.RIGHT,3).setBorder(Border.NO_BORDER).setBold());
	        table3.addCell(PdfCommonMethods.table("  CGST 2.5%    "+trans.getTotalCgst25()+"  SGST 2.5%    "+trans.getTotalSgst25(),PdfCommonMethods.font9, TextAlignment.LEFT,6).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table("GST 5% ",PdfCommonMethods.font9, TextAlignment.CENTER,2).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table(String.valueOf(billmaster.getTotalGst25()),PdfCommonMethods.font9, TextAlignment.CENTER,1).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table("1250 ",PdfCommonMethods.font9, TextAlignment.RIGHT,3).setBorder(Border.NO_BORDER).setBold());
            table3.addCell(PdfCommonMethods.table("  CGST 6%     "+trans.getTotalCgst6()+"  SGST 6%    "+trans.getTotalSgst6(), PdfCommonMethods.font9, TextAlignment.LEFT,6).setBorder(Border.NO_BORDER));
            table3.addCell(PdfCommonMethods.table("GST 12% ",PdfCommonMethods.font9, TextAlignment.CENTER,2).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table(String.valueOf(billmaster.getTotalGst6()),PdfCommonMethods.font9, TextAlignment.CENTER,1).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table("1250 ",PdfCommonMethods.font9, TextAlignment.RIGHT,3).setBorder(Border.NO_BORDER).setBold());
            table3.addCell(PdfCommonMethods.table("  CGST 9%    "+trans.getTotalCgst9()+"  SGST 9%    "+trans.getTotalSgst9(), PdfCommonMethods.font9, TextAlignment.LEFT,6).setBorder(Border.NO_BORDER));
            table3.addCell(PdfCommonMethods.table("GST 18%",PdfCommonMethods.font9, TextAlignment.CENTER,2).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table(String.valueOf(billmaster.getTotalGst9()),PdfCommonMethods.font9, TextAlignment.CENTER,1).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table("1250 ",PdfCommonMethods.font9, TextAlignment.RIGHT,3).setBorder(Border.NO_BORDER).setBold());
	        table3.addCell(PdfCommonMethods.table("  CGST 14%    "+trans.getTotalCgst14()+"  SGST 14%    "+trans.getTotalSgst14(), PdfCommonMethods.font9, TextAlignment.LEFT,6).setBorder(Border.NO_BORDER));
            table3.addCell(PdfCommonMethods.table("GST 28%",PdfCommonMethods.font9, TextAlignment.CENTER,2).setBorder(Border.NO_BORDER));
	        table3.addCell(PdfCommonMethods.table(String.valueOf(billmaster.getTotalGst14()),PdfCommonMethods.font9, TextAlignment.CENTER,1).setBorder(Border.NO_BORDER));
	        doc.add(table3);*/
	        table3 = new Table(12);
	        table3.addCell(PdfCommonMethods.table("("+billmaster.getAmountString()+")", PdfCommonMethods.font10, TextAlignment.CENTER,9).setBold());
            table3.addCell(PdfCommonMethods.table("TOTAL",PdfCommonMethods.font10, TextAlignment.CENTER,2).setBold());
	        table3.addCell(PdfCommonMethods.table(SukiAppUtil.round(SukiAppUtil.roundedOff(billmaster.getTotalAmount())),PdfCommonMethods.font10, TextAlignment.CENTER,1).setBold());
	        table3.setWidthPercent(100);
	        doc.add(table3);
	        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
	        	 // float x = pdfDoc.getPage(i).getPageSize().getWidth();
		        // float y = pdfDoc.getPage(i).getPageSize().getBottom()+35;
	        	// doc.showTextAligned(new Paragraph("Page "+i+"/"+pdfDoc.getNumberOfPages()).setFontSize(9),450, y-27, i,
	            //         TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
	        	if(i==pdfDoc.getNumberOfPages()){
	        	doc.showTextAligned(new Paragraph("Bank Details").setFontSize(9).setBold(),50, 55, i,
		                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);	
	        	doc.showTextAligned(new Paragraph("Acc No     :  10056226509").setFontSize(9),50, 40, i,
	                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
	        	doc.showTextAligned(new Paragraph("IFSC       :  IDFB0080105").setFontSize(9),50, 25, i,
	                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
	        	doc.showTextAligned(new Paragraph("Acc Holder :  NO IDENTITY").setFontSize(9),50, 10 , i,
	                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
	            doc.showTextAligned(new Paragraph("(NO IDENTITY)").setFontSize(9).setBold(),450, 10, i,
	                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
	        	}
	        }
	        doc.close();
	        dcPdf=loadFile(dest);
	        return dcPdf;
			}catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return dcPdf;
		}
		
	public static byte[] createDc1(){
		byte[] dcPdf=null;
		try{
			PdfCommonMethods.fontNames();
		String dest=SukiAppConstants.DC_FOLDER+"sample"+"1"+".pdf";
		ByteArrayOutputStream pdfFileout = new ByteArrayOutputStream();
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        // Note that it is not necessary to create new PageSize object,
        // but for testing reasons (connected to parallelization) we call constructor here
        Document doc = new Document(pdfDoc, new PageSize(PageSize.A4));
        doc.setMargins(10, 10, 10, 10);
        Table table = new Table(3);
        table.setWidthPercent(100);
        table.addCell(PdfCommonMethods.tableContent("GST No: 33456365178", PdfCommonMethods.font10, TextAlignment.LEFT, 1));
        table.addCell(PdfCommonMethods.tableContent("DELIVERY CHALAN", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold().setPaddingTop(-3));
        table.addCell(PdfCommonMethods.tableContent("Dc.No: "+1, PdfCommonMethods.font10, TextAlignment.RIGHT, 1).setMarginRight(38));
        table.addCell(PdfCommonMethods.tableContent("Date : ",PdfCommonMethods.font10, TextAlignment.RIGHT,3).setPaddingTop(-3));
        doc.add(table);
        table = new Table(2);
        table.setWidthPercent(100);
        Cell cell=new Cell();
        cell.setHeight(80).setBorder(Border.NO_BORDER);
        cell.add(PdfCommonMethods.cellContent("WRIGHT OFFICE SOLUTIONS",9,TextAlignment.CENTER).setBold());
        cell.add(PdfCommonMethods.cellContent("(A Complete Stationery)",8,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("No.178,M.G.R Main Road,kandanchavadi",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("Chennai-600096. cell:9940665721,9940582622",9,TextAlignment.CENTER));
        cell.add(PdfCommonMethods.cellContent("E-mail:wrightofficesolution@gmail.com",9,TextAlignment.CENTER));
        table.addCell(cell);
        Cell cell1=new Cell();
        cell1.setHeight(80).setBorder(Border.NO_BORDER);
        cell1.add(PdfCommonMethods.cellContent("To.",9,TextAlignment.LEFT));
        cell1.add(PdfCommonMethods.cellContent("xxx",9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent("yyy",9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent("zzz",9,TextAlignment.CENTER));
        cell1.add(PdfCommonMethods.cellContent("aaa",9,TextAlignment.CENTER));
        table.addCell(cell1);
        doc.add(table);
        Table table3 = new Table(6);
        table3.setWidthPercent(100);
        table3.addCell(PdfCommonMethods.table("S.No", PdfCommonMethods.font9, TextAlignment.CENTER, 1).setBold());
        table3.addCell(PdfCommonMethods.table("Description", PdfCommonMethods.font9, TextAlignment.CENTER,3).setBold());
        table3.addCell(PdfCommonMethods.table("Quantity", PdfCommonMethods.font9, TextAlignment.CENTER,2).setBold());
        doc.add(table3);
        table3 = new Table(6);
        table3.setWidthPercent(100);
        for(int i=0;i<3;i++){
        	table3.addCell(PdfCommonMethods.table("1", PdfCommonMethods.font9, TextAlignment.CENTER, 1));
            table3.addCell(PdfCommonMethods.table("Description", PdfCommonMethods.font9, TextAlignment.CENTER,3));
            table3.addCell(PdfCommonMethods.table("Quantity", PdfCommonMethods.font9, TextAlignment.CENTER,2));
        }
        doc.add(table3);
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
        	if(i==pdfDoc.getNumberOfPages()){
            float x = pdfDoc.getPage(i).getPageSize().getWidth();
            float y = pdfDoc.getPage(i).getPageSize().getBottom()+35;
            doc.showTextAligned(new Paragraph("Customer Signature with seal").setFontSize(10),20, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
            doc.showTextAligned(new Paragraph("NO IDENTITY").setFontSize(9).setBold(),450, y, i,
                    TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        	}
        }
        doc.close();
        dcPdf=loadFile(dest);
        return dcPdf;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return dcPdf;
	}
	public static byte[] readFully(FileInputStream stream) throws IOException
	{
	    byte[] buffer = new byte[8192];
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();

	    int bytesRead;
	    try {
			while ((bytesRead = stream.read(buffer)) != -1)
			{
			    baos.write(buffer, 0, bytesRead);
			}
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return baos.toByteArray();
	}
	public static byte[] loadFile(String sourcePath) throws IOException
	{
	    FileInputStream inputStream = null;
	        try {
				inputStream = new FileInputStream(sourcePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return readFully(inputStream);
	   
	   
	}
	 public static void main(String[] args){
//		 createBill(1,"","");
	 }
}
