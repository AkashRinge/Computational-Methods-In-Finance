package com.task.plot.impl;

import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.springframework.stereotype.Component;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.context.HistContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class HistogramGeneratorImpl implements PlotGeneratorAPI<HistContext>{

	private JFrame jFrame;
	
    public void plot(HistContext ctx) {

    	String chartTitle = ctx.getChartTitle();
    	jFrame.setTitle(ctx.getApplicationTitle());
    	double[] data = ctx.getData();
    	int numBins = ctx.getNumBins();
    	
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries(chartTitle, data, numBins);

        JFreeChart chart = ChartFactory.createHistogram(
                chartTitle, null, null, dataset, PlotOrientation.VERTICAL, true, false, false);
        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setForegroundAlpha(0.75f);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setBarPainter(new StandardXYBarPainter());

        ChartPanel chartPanel = new ChartPanel(chart);
        jFrame.setContentPane(chartPanel);
        jFrame.setVisible(true);
    }
}