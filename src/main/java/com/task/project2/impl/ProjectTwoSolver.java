package com.task.project2.impl;

import static com.task.project2.impl.ProjectTwoUtil.bivariateCorr;
import static com.task.project2.impl.ProjectTwoUtil.estimateIntegralMC;
import static com.task.project2.impl.ProjectTwoUtil.estimateIntegralReimann;
import static com.task.project2.impl.ProjectTwoUtil.stdWeiner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.task.api.BlackScholesAPI;
import com.task.api.MCCallOptionSimulatorAPI;
import com.task.api.MonteCarloSimulatorAPI;
import com.task.api.PlotAPI;
import com.task.domain.MCOperation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectTwoSolver {

	private static final Logger LOG = Logger.getLogger("Project Two Solver");
	
	private PlotAPI plt;
	private BlackScholesAPI bsApi;
	private MCCallOptionSimulatorAPI mcCallApi;

	void ques1() {
		// Q1 parameters
		double a = -0.7;
		int n = 1000;
		double[] meanVector = new double[] { 0, 0 };
		double[][] covMatrix = new double[][] { { 3, a }, { a, 5 } };
		double[][] bivariateDist = RandHelper.instance().bivariateDist(n, meanVector, covMatrix);
		LOG.info("\n\nBubble Plot for bivariate distribution");
		plt.bubble("Bivariate Normal Distribution", "Bivariate Normal Distribution", bivariateDist, "Number of simulations", "Value of random variable");
		double rho = bivariateCorr(bivariateDist, n);
		LOG.info("\n\n Correlation coefficient: rho(a): " + rho); //NOSONAR
	}

	void ques2() {
		// Q2 parameters
		double[] meanVector = new double[] { 0, 0 };
		double[][] covMatrix2 = new double[][] { { 1, 0.6 }, { 0.6 , 1 } };
		double[][] bivariateDist2 = RandHelper.instance().bivariateDist(10000, meanVector, covMatrix2);
		List<MCContext> ctxList = new ArrayList<>(10000);
		for (int i = 0; i < bivariateDist2.length; i++) {
			ctxList.add(new MCContext(bivariateDist2[i][0], bivariateDist2[i][1]));
		}

		MCOperation<MCContext> op = ctx -> Math.max(0,
				Math.pow(ctx.y, 3) + (Math.sin(ctx.y)) + (ctx.x * ctx.x * ctx.y));

		MonteCarloSimulatorAPI<MCContext> simulator = new MonteCarloSimulatorAPI<>(op, ctxList);
		double expectedMc = simulator.simulateValue();
		LOG.info("\n\n Expected value of expression using monte carlo simulation " + expectedMc); //NOSONAR
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
			double f1 = w1 * w1 + Math.sin(w1);
			double w2 = 0-w1;
			double f2 = w2 * w2 + Math.sin(w2);
			return (0.5*f1 + 0.5*f2);
		};

		double aAnt5 = runMcSpecifiedTime(n, antitheticA, 5);
		LOG.info("\n\n Monte Carlo simulated value of A(t) for t = 1 " + a1); //NOSONAR
		LOG.info("\n Monte Carlo simulated value of A(t) for t = 3 " + a3); //NOSONAR
		LOG.info("\n Monte Carlo simulated value of A(t) for t = 5 " + a5); //NOSONAR
		
		LOG.info("\n\n Monte Carlo simulated value of B(t) for t = 1 " + b1); //NOSONAR
		LOG.info("\n Monte Carlo simulated value of B(t) for t = 3 " + b3); //NOSONAR
		LOG.info("\n Monte Carlo simulated value of B(t) for t = 5 " + b5); //NOSONAR
		
		LOG.info("\n\n Monte Carlo simulated value of A(t) using antithetic variates for t = 5 " + aAnt5); //NOSONAR
	}

	void ques4() {
		// Q4 parameters
		int n = 100000;
		double T = 5;
		double r = 0.05;
		final double s0 = 100;
		double k = 110;
		double sigma = 0.28;

		double expectedCall = mcCallApi.callOption(s0, k, r, sigma, T, n);

		double expectedCall2 = mcCallApi.callOptionAntithetic(s0, k, r, sigma, expectedCall, n);

		double bsCall = bsApi.calculateCallOptionValue(s0, k, r, T, sigma);
		
		LOG.info("\n\n Monte Carlo simulated value of call: " + expectedCall); //NOSONAR
		LOG.info("\nMonte Carlo simulated value of call using antithetic variates: " + expectedCall2); //NOSONAR
		LOG.info("\n Monte Carlo simulated value of call using Black Scholes: " + bsCall); //NOSONAR
	}

	void ques5() {
		// Q5 parameters
		double r = 0.05;
		double s0 = 100;
		double sigma = 0.28;
		MCOperation<Double> stSimulation = t -> {
			double w = stdWeiner(t);
			return s0 * Math.exp(sigma * w + (r - (sigma * sigma) / 2) * t);
		};

		List<double[]> data = new ArrayList<>(7);
		List<String> seriesNames = new ArrayList<>(7);
		double[] expSt = new double[1000];
		for (int t = 1; t <= 10; t++) {
			double mcVal = runMcSpecifiedTime(1000, stSimulation, t);
			for(int i=0; i<100; i++) // This is for plotting 
				expSt[(t-1)*100+i] = mcVal;
		}
		data.add(expSt);
		seriesNames.add("E(St)");

		double[][] expStPaths = new double[6][1000];
		double timeStep = 10.0/1000;
		for (int i = 1; i <= 1000; i++) {
			for (int j = 0; j < 6; j++) {
				if (i == 1) {
					data.add(expStPaths[j]);
					seriesNames.add("Path " + j);
				}
				expStPaths[j][i - 1] = stSimulation.run(i * timeStep);
			}
		}
		
		plt.plotMultiLine("Expected vs Trace of stock prices", "Stock price following GBM", data, seriesNames, "Time", "Stock Price");
		
	}

	void ques6() {
		// Q6 parameters
		int steps = 10000000;
		MCOperation<Double> integral = x -> 4 * Math.sqrt(1 - x * x);
		double reimannEst = estimateIntegralReimann(steps, integral, 0, 1);
		double mcEst = estimateIntegralMC(steps, integral, 0, 1);
		
		LOG.info("\n\n Simulated value of integral using reimann estimate: " + reimannEst); //NOSONAR
		LOG.info("\n Simulated value of integral using monte carlo: " + mcEst); //NOSONAR
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
