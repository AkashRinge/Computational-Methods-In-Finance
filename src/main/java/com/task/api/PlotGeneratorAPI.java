package com.task.api;

import com.task.plot.impl.domain.PlotContext;

/**
 * Individual plot generators are used by the Plot API such as histogram generator, line plot generator etc
 * 
 * @author food4thought
 *
 * @param <T>
 */
@FunctionalInterface
public interface PlotGeneratorAPI<T extends PlotContext> {
	void plot(T ctx);
}
