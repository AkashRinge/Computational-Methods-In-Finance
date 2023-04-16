package com.task.api;

import java.util.List;

import com.task.domain.MCOperation;
import com.task.util.StatsHelper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonteCarloSimulatorAPI<T> {
	private MCOperation<T> operation;
	private List<T> inputParams;
	
	public double simulateValue() {
		double[] total = new double[inputParams.size()];
		for(int i=0; i<inputParams.size(); i++) {
			total[i] = operation.run(inputParams.get(i));
		}
		return StatsHelper.mean(total);
	}
}
