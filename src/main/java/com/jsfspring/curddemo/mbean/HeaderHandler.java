package com.jsfspring.curddemo.mbean;

import java.awt.Canvas;

import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Table;

public class HeaderHandler implements IEventHandler{
	
	protected Table table;
	
	public HeaderHandler(Table table) {
		this.table=table;
	}
	public void handleEvent(Event event){
		PdfDocumentEvent docEvent=(PdfDocumentEvent) event;
		PdfPage page=docEvent.getPage();
		PdfDocument pdfDoc=docEvent.getDocument();
		int pageNum=docEvent.getDocument().getPageNumber(page);
		PdfCanvas aboveCanvas=new PdfCanvas(page.newContentStreamAfter(),page.getResources(),pdfDoc);
		Rectangle area=page.getPageSize();
		new com.itextpdf.layout.Canvas(aboveCanvas,pdfDoc,area).add(table);
	}

}
