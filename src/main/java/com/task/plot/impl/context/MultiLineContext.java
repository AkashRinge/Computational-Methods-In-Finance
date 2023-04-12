package com.task.plot.impl.context;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MultiLineContext extends PlotContext {
	private List<double[]> data;
	private List<String> seriesNames;
	private String xAxisLabel;
	private String yAxisLabel;
}
