/*
 * Copyright 2009-2014 PrimeTek.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.ultima.view.chart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;
import org.primefaces.model.chart.MeterGaugeChartModel;
import org.primefaces.model.chart.OhlcChartModel;
import org.primefaces.model.chart.OhlcChartSeries;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class ChartView implements Serializable {

    private LineChartModel lineModel1;
    private LineChartModel lineModel2;
    private LineChartModel zoomModel;
    private CartesianChartModel combinedModel;
    private CartesianChartModel fillToZero;
    private LineChartModel areaModel;
    private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private DonutChartModel donutModel1;
    private DonutChartModel donutModel2;
    private MeterGaugeChartModel meterGaugeModel1;
    private MeterGaugeChartModel meterGaugeModel2;
    private BubbleChartModel bubbleModel1;
    private BubbleChartModel bubbleModel2;
    private OhlcChartModel ohlcModel;
    private OhlcChartModel ohlcModel2;
    private PieChartModel livePieModel;
    private LineChartModel animatedModel1;
    private BarChartModel animatedModel2;
    private LineChartModel multiAxisModel;
    private LineChartModel dateModel;

    @PostConstruct
    public void init() {
        createBarModels();
	}

    private void createBarModels() {
        createBarModel();
       // createHorizontalBarModel();
    }
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        
        
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);
 
        model.addSeries(boys);
        model.addSeries(girls);
        
        model.setExtender("skinChart");
        return model;
    }
    private void createBarModel() {
        barModel = initBarModel();
        
        barModel.setTitle("Bar Chart");
        
       
    }
    
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);

        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);
        
        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        horizontalBarModel.setStacked(true);
        
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Births");
        xAxis.setMin(0);
        xAxis.setMax(200);
        
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Gender");        
    }

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public void setLineModel1(LineChartModel lineModel1) {
		this.lineModel1 = lineModel1;
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	public void setLineModel2(LineChartModel lineModel2) {
		this.lineModel2 = lineModel2;
	}

	public LineChartModel getZoomModel() {
		return zoomModel;
	}

	public void setZoomModel(LineChartModel zoomModel) {
		this.zoomModel = zoomModel;
	}

	public CartesianChartModel getCombinedModel() {
		return combinedModel;
	}

	public void setCombinedModel(CartesianChartModel combinedModel) {
		this.combinedModel = combinedModel;
	}

	public CartesianChartModel getFillToZero() {
		return fillToZero;
	}

	public void setFillToZero(CartesianChartModel fillToZero) {
		this.fillToZero = fillToZero;
	}

	public LineChartModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(LineChartModel areaModel) {
		this.areaModel = areaModel;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public HorizontalBarChartModel getHorizontalBarModel() {
		return horizontalBarModel;
	}

	public void setHorizontalBarModel(HorizontalBarChartModel horizontalBarModel) {
		this.horizontalBarModel = horizontalBarModel;
	}

	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}

	public DonutChartModel getDonutModel1() {
		return donutModel1;
	}

	public void setDonutModel1(DonutChartModel donutModel1) {
		this.donutModel1 = donutModel1;
	}

	public DonutChartModel getDonutModel2() {
		return donutModel2;
	}

	public void setDonutModel2(DonutChartModel donutModel2) {
		this.donutModel2 = donutModel2;
	}

	public MeterGaugeChartModel getMeterGaugeModel1() {
		return meterGaugeModel1;
	}

	public void setMeterGaugeModel1(MeterGaugeChartModel meterGaugeModel1) {
		this.meterGaugeModel1 = meterGaugeModel1;
	}

	public MeterGaugeChartModel getMeterGaugeModel2() {
		return meterGaugeModel2;
	}

	public void setMeterGaugeModel2(MeterGaugeChartModel meterGaugeModel2) {
		this.meterGaugeModel2 = meterGaugeModel2;
	}

	public BubbleChartModel getBubbleModel1() {
		return bubbleModel1;
	}

	public void setBubbleModel1(BubbleChartModel bubbleModel1) {
		this.bubbleModel1 = bubbleModel1;
	}

	public BubbleChartModel getBubbleModel2() {
		return bubbleModel2;
	}

	public void setBubbleModel2(BubbleChartModel bubbleModel2) {
		this.bubbleModel2 = bubbleModel2;
	}

	public OhlcChartModel getOhlcModel() {
		return ohlcModel;
	}

	public void setOhlcModel(OhlcChartModel ohlcModel) {
		this.ohlcModel = ohlcModel;
	}

	public OhlcChartModel getOhlcModel2() {
		return ohlcModel2;
	}

	public void setOhlcModel2(OhlcChartModel ohlcModel2) {
		this.ohlcModel2 = ohlcModel2;
	}

	public PieChartModel getLivePieModel() {
		return livePieModel;
	}

	public void setLivePieModel(PieChartModel livePieModel) {
		this.livePieModel = livePieModel;
	}

	public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}

	public void setAnimatedModel1(LineChartModel animatedModel1) {
		this.animatedModel1 = animatedModel1;
	}

	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}

	public void setAnimatedModel2(BarChartModel animatedModel2) {
		this.animatedModel2 = animatedModel2;
	}

	public LineChartModel getMultiAxisModel() {
		return multiAxisModel;
	}

	public void setMultiAxisModel(LineChartModel multiAxisModel) {
		this.multiAxisModel = multiAxisModel;
	}

	public LineChartModel getDateModel() {
		return dateModel;
	}

	public void setDateModel(LineChartModel dateModel) {
		this.dateModel = dateModel;
	}
        
}
