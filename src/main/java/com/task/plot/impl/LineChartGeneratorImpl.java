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
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Component;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.context.LineContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LineChartGeneratorImpl implements PlotGeneratorAPI<LineContext> {

	private JFrame jFrame;

	public void plot(LineContext ctx) {
		String chartTitle = ctx.getChartTitle();
		jFrame.setTitle(ctx.getApplicationTitle());
		double[] xData = ctx.getXData();
		double[] yData = ctx.getYData();
		int n = xData.length;

		XYSeries series = new XYSeries("Data");
		for (int i = 0; i < n; i++) {
			series.add(xData[i], yData[i]);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);

		JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, "X", "Y", dataset, PlotOrientation.VERTICAL, true,
				false, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		ChartPanel chartPanel = new ChartPanel(chart);
		jFrame.setContentPane(chartPanel);
		jFrame.setVisible(true);
	}
}
