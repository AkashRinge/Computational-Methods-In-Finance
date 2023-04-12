package com.task.plot.impl;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.springframework.stereotype.Component;

import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.context.MultiLineContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MultiLineChartGeneratorImpl implements PlotGeneratorAPI<MultiLineContext> {

	public void plot(MultiLineContext ctx) {
		DefaultXYDataset dataset = new DefaultXYDataset();

		for (int i = 0; i < ctx.getData().size(); i++) {
			double[] seriesData = ctx.getData().get(i);
			String seriesName = ctx.getSeriesNames().get(i);

			double[][] series = new double[2][seriesData.length];
			for (int j = 0; j < seriesData.length; j++) {
				series[0][j] = j;
				series[1][j] = seriesData[j];
			}

			dataset.addSeries(seriesName, series);
		}

		JFreeChart chart = ChartFactory.createXYLineChart(ctx.getChartTitle(), ctx.getYAxisLabel(), ctx.getYAxisLabel(), dataset,
                PlotOrientation.VERTICAL, true, true, false);

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        for (int i = 0; i < ctx.getData().size(); i++) {
            Color color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random(), 0.5f);
            renderer.setSeriesPaint(i, color);
            renderer.setSeriesShapesVisible(i, false);
        }

        chart.getXYPlot().setRenderer(renderer);

        ChartFrame frame = new ChartFrame(ctx.getApplicationTitle(), chart);
        frame.pack();
        frame.setVisible(true);
	}
}
