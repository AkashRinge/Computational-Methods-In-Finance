package com.task.plot.impl.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LineContext extends PlotContext {
	private double[] xData;
	private double[] yData;
}
