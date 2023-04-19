package com.task.plot.impl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScatterContext extends PlotContext {
	private double[] xValues;
	private double[] yValues;
}
