package com.task.project2.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.task.api.MonteCarloSimulatorAPI;
import com.task.api.PlotAPI;
import com.task.util.GeneralHelper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
	
	public void run() {
		// Q1 parameters
		double a = -0.7;
		int n = 1000;
		double[] meanVector = new double[]{0, 0};
		double[][] covMatrix = new double[][] {{3,a},{a,5}};
		double[][] bivariateDist = RandHelper.instance().bivariateDist(n, meanVector, covMatrix);
		double rho = ProjectTwoHelper.instance().bivariateCorr(bivariateDist, n);
		
		// Q2 parameters
		double[][] covMatrix2 = new double[][] {{1,0.6*0.6},{0.6*0.6,1}};
		double[][] bivariateDist2 = RandHelper.instance().bivariateDist(10000, meanVector, covMatrix2);
		List<MonteCarloContext> ctxList = new ArrayList<>(10000);
		for(int i=0; i<bivariateDist2.length; i++) {
			ctxList.add(new MonteCarloContext(bivariateDist2[i][0], bivariateDist2[i][1]));
		}
		
		MonteCarloSimulatorAPI<MonteCarloContext, Double> simulator = list -> list.stream()
				.map(ctx -> ProjectTwoHelper.instance().customMonteCarlo(ctx.x, ctx.y))
				.toArray(Double[]::new);
		simulator.simulate(ctxList);
		
		// Q3 parameters
		
		// Q4 parameters
		
		// Q5 parameters
		
		// Q6 parameters
		
	}
	
	@AllArgsConstructor
	private class MonteCarloContext {
		private Double x;
		private Double y;
	}
}
