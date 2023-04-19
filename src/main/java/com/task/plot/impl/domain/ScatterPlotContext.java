package com.task.plot.impl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScatterPlotContext extends PlotContext {
	private double[] xValues;
	private double[] yValues;
}
