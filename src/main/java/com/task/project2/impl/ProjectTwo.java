package com.task.project2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.task.api.BlackScholesAPI;
import com.task.api.MonteCarloSimulatorAPI;
import com.task.api.PlotAPI;
import com.task.domain.MCOperation;

import static com.task.project2.impl.ProjectTwoUtil.*;

import lombok.AllArgsConstructor;

/***
 * 
 * 
 * @author food4thought
 */
@Component
@AllArgsConstructor
public class ProjectTwo {

	private static final Logger LOG = Logger.getLogger("Project 2: Monte Carlo Simulation and Numerical Integration in Finance");
	
	private PlotAPI plot;
	private BlackScholesAPI bsApi;
	
	public void run() {
		
		// Q1 parameters
		double a = -0.7;
		int n = 1000;
		double[] meanVector = new double[]{0, 0};
		double[][] covMatrix = new double[][] {{3,a},{a,5}};
		double[][] bivariateDist = RandHelper.instance().bivariateDist(n, meanVector, covMatrix);
		double rho = bivariateCorr(bivariateDist, n);
		
		// Q2 parameters
		double[][] covMatrix2 = new double[][] {{1,0.6*0.6},{0.6*0.6,1}};
		double[][] bivariateDist2 = RandHelper.instance().bivariateDist(10000, meanVector, covMatrix2);
		List<MCContext> ctxList = new ArrayList<>(10000);
		for(int i=0; i<bivariateDist2.length; i++) {
			ctxList.add(new MCContext(bivariateDist2[i][0], bivariateDist2[i][1]));
		}
		
		MCOperation<MCContext> op = ctx -> Math.max(0, Math.pow(ctx.y, 3) 
				+ (Math.sin(ctx.y)) + (ctx.x*ctx.x*ctx.y));
		
		MonteCarloSimulatorAPI<MCContext> simulator = new MonteCarloSimulatorAPI<>(op, ctxList);
		double expectedMc = simulator.simulateValue();
		
		// Q3 parameters
		n = 10000;
		MCOperation<Double> opA = t -> { 
				double w = stdWeiner(t);
				return (w*w + Math.sin(w));
		};
		
		MCOperation<Double> opB = t -> { 
			double w = stdWeiner(t);
			return (Math.exp(t/2) * Math.cos(w));
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
			
			return (w*w + Math.sin(w));
		};
		
		double aAnt5 = runMcSpecifiedTime(n, opA, 5);
		
		// Q4 parameters
		double T = 5;
		double r = 0.05;
		double s0 = 100;
		double k = 110;
		double sigma = 0.28;
		
		MCOperation<Double> callSimulation = t -> { 
			double w = stdWeiner(t);
			double st = s0 * Math.exp(sigma * w + (r - sigma*sigma*t/2));
			double payoff = st - k;
			return payoff * Math.exp((0-r)*t);
		};
		
		double expectedCall = runMcSpecifiedTime(n, callSimulation, T);
		
		MCOperation<Double> callAntithetic = t -> { 
			double w1 = stdWeiner(t);
			double w2 = stdWeiner(t);
			double w =  w1 * 0.5 + w2 * -0.5;
			double st = s0 * Math.exp(sigma * w + (r - sigma*sigma*t/2));
			double payoff = st - k;
			return payoff * Math.exp((0-r)*t);
		};
		
		double expectedCall2 = runMcSpecifiedTime(n, callAntithetic, T);
		
		double bsCall = bsApi.calculateCallOptionValue(s0, k, r, T, sigma);
		
		// Q5 parameters
		
		// Q6 parameters
		
	}

	private double runMcSpecifiedTime(int n, MCOperation<Double> op, double t) {
		List<Double> times = IntStream.range(0, n).boxed().map(x -> t).collect(Collectors.toList());
		MonteCarloSimulatorAPI<Double> simulator2 = new MonteCarloSimulatorAPI<>(op, times);
		return simulator2.simulateValue();
	}
	
	@AllArgsConstructor
	private class MCContext {
		private Double x;
		private Double y;
	}
	
}
