package com.task.api;

import com.task.plot.impl.context.PlotContext;

public interface PlotGeneratorAPI<T extends PlotContext> {
	void plot(T ctx);
}
