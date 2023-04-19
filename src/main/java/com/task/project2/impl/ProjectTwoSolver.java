package com.task.project2.impl;

import static com.task.project2.impl.ProjectTwoUtil.bivariateCorr;
import static com.task.project2.impl.ProjectTwoUtil.estimateIntegralMC;
import static com.task.project2.impl.ProjectTwoUtil.estimateIntegralReimann;
import static com.task.project2.impl.ProjectTwoUtil.stdWeiner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.task.api.BlackScholesAPI;
import com.task.api.MonteCarloSimulatorAPI;
import com.task.api.PlotAPI;
import com.task.domain.MCOperation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectTwoSolver {

	private PlotAPI plt;
	private BlackScholesAPI bsApi;

	void ques1() {
		// Q1 parameters
		double a = -0.7;
		int n = 1000;
		double[] meanVector = new double[] { 0, 0 };
		double[][] covMatrix = new double[][] { { 3, a }, { a, 5 } };
		double[][] bivariateDist = RandHelper.instance().bivariateDist(n, meanVector, covMatrix);
		double rho = bivariateCorr(bivariateDist, n);
		
	}

	void ques2() {
		// Q2 parameters
		double[] meanVector = new double[] { 0, 0 };
		double[][] covMatrix2 = new double[][] { { 1, 0.6 * 0.6 }, { 0.6 * 0.6, 1 } };
		double[][] bivariateDist2 = RandHelper.instance().bivariateDist(10000, meanVector, covMatrix2);
		List<MCContext> ctxList = new ArrayList<>(10000);
		for (int i = 0; i < bivariateDist2.length; i++) {
			ctxList.add(new MCContext(bivariateDist2[i][0], bivariateDist2[i][1]));
		}

		MCOperation<MCContext> op = ctx -> Math.max(0,
				Math.pow(ctx.y, 3) + (Math.sin(ctx.y)) + (ctx.x * ctx.x * ctx.y));

		MonteCarloSimulatorAPI<MCContext> simulator = new MonteCarloSimulatorAPI<>(op, ctxList);
		double expectedMc = simulator.simulateValue();
	}

	void ques3() {

		// Q3 parameters
		int n = 10000;
		MCOperation<Double> opA = t -> {
			double w = stdWeiner(t);
			return (w * w + Math.sin(w));
		};

		MCOperation<Double> opB = t -> {
			double w = stdWeiner(t);
			return (Math.exp(t / 2) * Math.cos(w));
		};

		double a1 = runMcSpecifiedTime(n, opA, 1);
		double a3 = runMcSpecifiedTime(n, opA, 3);
		double a5 = runMcSpecifiedTime(n, opA, 5);

		double b1 = runMcSpecifiedTime(n, opB, 1);
		double b3 = runMcSpecifiedTime(n, opB, 3);
		double b5 = runMcSpecifiedTime(n, opB, 5);

		MCOperation<Double> antitheticA = t -> {
			double w1 = stdWeiner(t);
			double w2 = stdWeiner(t);
			double w = w1 * 0.5 + w2 * -0.5;

			return (w * w + Math.sin(w));
		};

		double aAnt5 = runMcSpecifiedTime(n, opA, 5);
	}

	void ques4() {
		// Q4 parameters
		int n = 100000;
		double T = 5;
		double r = 0.05;
		final double s0 = 100;
		double k = 110;
		double sigma = 0.28;

		MCOperation<Double> callSimulation = t -> {
			double w = stdWeiner(t);
			double st = s0 * Math.exp(sigma * w + (r - sigma * sigma * t / 2));
			double payoff = st - k;
			return payoff * Math.exp((0 - r) * t);
		};

		double expectedCall = runMcSpecifiedTime(n, callSimulation, T);

		MCOperation<Double> callAntithetic = t -> {
			double w1 = stdWeiner(t);
			double w2 = stdWeiner(t);
			double w = w1 * 0.5 + w2 * -0.5;
			double st = s0 * Math.exp(sigma * w + (r - sigma * sigma * t / 2));
			double payoff = st - k;
			return payoff * Math.exp((0 - r) * t);
		};

		double expectedCall2 = runMcSpecifiedTime(n, callAntithetic, T);

		double bsCall = bsApi.calculateCallOptionValue(s0, k, r, T, sigma);
	}

	void ques5() {
		// Q5 parameters
		double r = 0.05;
		double s0 = 100;
		double sigma = 0.28;
		MCOperation<Double> stSimulation = t -> {
			double w = stdWeiner(t);
			double st = s0 * Math.exp(sigma * w + (r - (sigma * sigma) / 2) * t);
			return st;
		};

		double[] expSt = new double[10];
		for (int t = 1; t <= 10; t++) {
			expSt[t - 1] = runMcSpecifiedTime(1000, stSimulation, t);
		}

		double[][] expStPaths = new double[6][1000];
		double timeStep = 0.1;
		for (int i = 1; i <= 1000; i++) {
			for (int j = 0; j < 6; j++) {
				expStPaths[j][i - 1] = stSimulation.run(i * timeStep);
			}
		}
	}

	void ques6() {
		// Q6 parameters
		int steps = 10000000;
		MCOperation<Double> integral = x -> 4 * Math.sqrt(1 - x * x);
		double reimannEst = estimateIntegralReimann(steps, integral, 0, 1);
		double mcEst = estimateIntegralMC(steps, integral, 0, 1);
	}
	
	
	@AllArgsConstructor
	private class MCContext {
		private Double x;
		private Double y;
	}

	private double runMcSpecifiedTime(int n, MCOperation<Double> op, double t) {
		List<Double> times = IntStream.range(0, n).boxed().map(x -> t).collect(Collectors.toList());
		MonteCarloSimulatorAPI<Double> simulator2 = new MonteCarloSimulatorAPI<>(op, times);
		return simulator2.simulateValue();
	}

}
