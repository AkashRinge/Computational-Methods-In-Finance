package com.task.plot.impl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BubbleContext extends PlotContext {
	private double[][] data;
	private String xAxisLabel;
    private String yAxisLabel;
}
