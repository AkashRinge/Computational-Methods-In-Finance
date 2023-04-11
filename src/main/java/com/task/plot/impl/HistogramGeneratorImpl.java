package com.task.plot.impl;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.context.HistContext;

public class HistogramGeneratorImpl implements PlotGeneratorAPI<HistContext>{

    public void plot(HistContext ctx) {

    	String chartTitle = ctx.getChartTitle();
    	String applicationTitle = ctx.getApplicationTitle();
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

        JFrame frame = new JFrame(applicationTitle);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        ChartPanel chartPanel = new ChartPanel(chart);
        frame.setContentPane(chartPanel);
        frame.setVisible(true);
    }
}