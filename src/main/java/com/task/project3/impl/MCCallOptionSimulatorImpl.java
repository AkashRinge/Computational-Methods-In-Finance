package com.task.project3.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.NormalDistribution;

import com.task.api.MCCallOptionSimulatorAPI;
import com.task.api.MonteCarloSimulatorAPI;
import com.task.domain.MCOperation;

public class MCCallOptionSimulatorImpl implements MCCallOptionSimulatorAPI {

	private static final NormalDistribution STANDARD_NORMAL = new NormalDistribution(0, 1);

	@Override
	public double callOption(double s0, double k, double r, double sigma, double T, int n) {
		MCOperation<Double> callSimulation = t -> {
			double w = stdWeiner(t);
			double st = s0 * Math.exp(sigma * w + (r * t - sigma * sigma * t / 2));
			double payoff = Math.max(0, st - k);
			return payoff * Math.exp((0 - r) * t);
		};

		return runMcSpecifiedTime(n, callSimulation, T);
	}

	@Override
	public double callOptionAntithetic(double s0, double k, double r, double sigma, double T, int n) {
		MCOperation<Double> callAntithetic = t -> {
			double w1 = stdWeiner(t);
			double w2 = 0 - w1;
			double st1 = s0 * Math.exp(sigma * w1 + (r * t - (sigma * sigma * t) / 2));
			double st2 = s0 * Math.exp(sigma * w2 + (r * t - (sigma * sigma * t) / 2));
			double payoff1 = Math.max(0, st1 - k);
			double payoff2 = Math.max(0, st2 - k);
			double payoff = payoff1 * 0.5 + payoff2 * 0.5;
			return payoff * Math.exp((0 - r) * t);
		};

		return runMcSpecifiedTime(n, callAntithetic, T);
	}
	
	@Override
	public double callOptionControl(double s0, double k, double r, double sigma, double T, int n) {
		// Not implemented yet
		return -1;
	}

	private static double stdWeiner(double t) {
		return STANDARD_NORMAL.sample() * Math.sqrt(t);
	}

	private static double runMcSpecifiedTime(int n, MCOperation<Double> op, double t) {
		List<Double> times = IntStream.range(0, n).boxed().map(x -> t).collect(Collectors.toList());
		MonteCarloSimulatorAPI<Double> simulator2 = new MonteCarloSimulatorAPI<>(op, times);
		return simulator2.simulateValue();
	}
}
