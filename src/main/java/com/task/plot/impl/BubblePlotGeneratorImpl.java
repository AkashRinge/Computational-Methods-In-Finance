package com.task.plot.impl;
import java.awt.Color;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.springframework.stereotype.Component;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.domain.BubbleContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BubblePlotGeneratorImpl implements PlotGeneratorAPI<BubbleContext> {

	private JFrame jFrame;

	@Override
	public void plot(BubbleContext ctx) {
	
		// Create the dataset
        DefaultXYZDataset dataset = new DefaultXYZDataset();
        double[][] parsable = new double[3][ctx.getData().length];
        for(int i=0; i<ctx.getData().length; i++) {
        	parsable[0][i] = i;
        	parsable[1][i] = ctx.getData()[i][0];
        	parsable[2][i] = ctx.getData()[i][1];
        }
        dataset.addSeries("Data", parsable);

        // Create the chart
        JFreeChart chart = ChartFactory.createBubbleChart(ctx.getChartTitle(), ctx.getXAxisLabel(), ctx.getYAxisLabel(), dataset,
                PlotOrientation.VERTICAL, false, true, false);

        // Customize the chart
        chart.setBackgroundPaint(Color.white);

        // Get the plot and set the renderer
        XYPlot plot = (XYPlot) chart.getPlot();
        XYBubbleRenderer renderer = new XYBubbleRenderer();
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.gray);
        plot.setRenderer(renderer);

        // Set the axis labels
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setLabel(ctx.getXAxisLabel());
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabel(ctx.getYAxisLabel());

        // Create the chart panel and set its properties
        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new Dimension(500, 500));
        chartPanel.setMouseZoomable(true, false);
        chartPanel.setPopupMenu(null);

        jFrame.setTitle(ctx.getApplicationTitle());
        jFrame.add(chartPanel);
        jFrame.pack();
        jFrame.setVisible(true);
	}
}
