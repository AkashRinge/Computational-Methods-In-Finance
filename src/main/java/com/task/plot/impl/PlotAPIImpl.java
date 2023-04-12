package com.task.plot.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.task.api.PlotAPI;
import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.context.HistContext;
import com.task.plot.impl.context.LineContext;
import com.task.plot.impl.context.MultiLineContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PlotAPIImpl implements PlotAPI {

	private PlotGeneratorAPI<HistContext> hist;
	private PlotGeneratorAPI<LineContext> line;
	private PlotGeneratorAPI<MultiLineContext> multiLine;

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
}
