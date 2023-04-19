package com.task.plot.impl;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;
import org.springframework.stereotype.Component;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.domain.BubbleContext;
import com.task.plot.impl.domain.PlotContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BubblePlotGeneratorImpl implements PlotGeneratorAPI<BubbleContext> {

	private JFrame jFrame;

	@Override
	public void plot(BubbleContext ctx) {
	
		// Create the dataset
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        dataset.addSeries("Data", ctx.getData());

        // Create the chart
        JFreeChart chart = ChartFactory.createBubbleChart(ctx.getChartTitle(), ctx.getXAxisLabel(), ctx.getYAxisLabel(), dataset,
                PlotOrientation.VERTICAL, true, true, false);

        // Customize the chart
        chart.setBackgroundPaint(Color.white);

        // Get the plot and set the renderer
        XYPlot plot = (XYPlot) chart.getPlot();
        XYBubbleRenderer renderer = new XYBubbleRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        plot.setRenderer(renderer);

        // Set the axis labels
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLabel(ctx.getXAxisLabel());
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabel(ctx.getYAxisLabel());

        // Create the chart panel and set its properties
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 500));
        chartPanel.setMouseZoomable(true, false);
        chartPanel.setPopupMenu(null);

        jFrame.setTitle(ctx.getApplicationTitle());
        jFrame.add(chartPanel);
        jFrame.pack();
        jFrame.setVisible(true);
	}
}
