package com.task.plot.impl.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistContext extends PlotContext {
	private double[] data;
	private int numBins;
}
