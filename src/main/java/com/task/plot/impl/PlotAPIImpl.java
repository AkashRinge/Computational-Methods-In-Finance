package com.task.plot.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.task.api.PlotAPI;
import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.domain.BubbleContext;
import com.task.plot.impl.domain.HistContext;
import com.task.plot.impl.domain.LineContext;
import com.task.plot.impl.domain.MultiLineContext;
import com.task.plot.impl.domain.ScatterContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlotAPIImpl implements PlotAPI {

	private PlotGeneratorAPI<HistContext> hist;
	private PlotGeneratorAPI<LineContext> line;
	private PlotGeneratorAPI<MultiLineContext> multiLine;
	private PlotGeneratorAPI<ScatterContext> scatter;
	private PlotGeneratorAPI<BubbleContext> bubble;

	@Override
	public void plotHist(String applicationTitle, String chartTitle, double[] data, int numBins) {
		HistContext histCtx = new HistContext(data, numBins);
		histCtx.setApplicationTitle(applicationTitle);
		histCtx.setChartTitle(chartTitle);
		hist.plot(histCtx);
	}

	@Override
	public void plotLine(String chartTitle, String applicationTitle, double[] xData, double[] yData) {
		LineContext lineCtx = new LineContext(xData, yData);
		lineCtx.setApplicationTitle(applicationTitle);
		lineCtx.setChartTitle(chartTitle);
		line.plot(lineCtx);
	}

	@Override
	public void plotMultiLine(String chartTitle, String applicationTitle, List<double[]> data, List<String> seriesNames,
			String xAxisLabel, String yAxisLabel) {
		MultiLineContext multiCtx = new MultiLineContext(data, seriesNames, xAxisLabel, yAxisLabel);
		multiCtx.setApplicationTitle(applicationTitle);
		multiCtx.setChartTitle(chartTitle);
		multiLine.plot(multiCtx);
	}

	@Override
	public void scatter(String chartTitle, String applicationTitle, double[] xData, double[] yData) {
		ScatterContext scatterCtx = new ScatterContext(xData, yData);
		scatterCtx.setApplicationTitle(applicationTitle);
		scatterCtx.setChartTitle(chartTitle);
		scatter.plot(scatterCtx);
	}
	
	@Override
	public void bubble(String chartTitle, String applicationTitle, double[][] data, String xAxis, String yAxis) {
		BubbleContext bbCtx = new BubbleContext(data, xAxis, yAxis);
		bbCtx.setApplicationTitle(applicationTitle);
		bbCtx.setChartTitle(chartTitle);
		bubble.plot(bbCtx);
	}
}
