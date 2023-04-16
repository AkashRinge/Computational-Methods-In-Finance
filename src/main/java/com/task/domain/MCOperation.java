package com.task.domain;

@FunctionalInterface
public interface MCOperation<T> {

	public double run(T ctx);

}
