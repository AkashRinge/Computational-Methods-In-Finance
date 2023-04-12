package com.task.api;

import com.task.plot.impl.context.PlotContext;

/**
 * Individual plot generators are used by the Plot API such as histogram generator, line plot generator etc
 * 
 * @author food4thought
 *
 * @param <T>
 */
public interface PlotGeneratorAPI<T extends PlotContext> {
	void plot(T ctx);
}
