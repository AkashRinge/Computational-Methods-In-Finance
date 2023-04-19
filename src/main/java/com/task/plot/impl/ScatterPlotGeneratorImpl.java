package com.task.plot.impl;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.stereotype.Component;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.domain.ScatterContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScatterPlotGeneratorImpl implements PlotGeneratorAPI<ScatterContext> {

	private JFrame jFrame;

    private XYDataset createDataset(ScatterContext ctx) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(ctx.getChartTitle());

        for (int i = 0; i < ctx.getXValues().length; i++) {
            series.add(ctx.getXValues()[i], ctx.getYValues()[i]);
        }

        dataset.addSeries(series);
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset, String applicationTitle) {
        JFreeChart chart = ChartFactory.createScatterPlot(applicationTitle, "X", "Y", dataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        Shape shape = new Ellipse2D.Double(-3, -3, 6, 6);
        renderer.setSeriesShape(0, shape);
        renderer.setSeriesPaint(0, Color.RED);
        plot.setRenderer(renderer);

        return chart;
    }

	@Override
	public void plot(ScatterContext ctx) {
		XYDataset dataset = createDataset(ctx);
        JFreeChart chart = createChart(dataset, ctx.getChartTitle());
        ChartPanel chartPanel = new ChartPanel(chart);
        
        jFrame.setTitle(ctx.getApplicationTitle());
        jFrame.add(chartPanel);
        jFrame.pack();
        jFrame.setVisible(true);
	}
}