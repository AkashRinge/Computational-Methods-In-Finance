package com.task.api;

import java.util.List;

@FunctionalInterface
public interface MonteCarloSimulatorAPI<U, V> {

	public V[] simulate(List<U> ctx);
}
