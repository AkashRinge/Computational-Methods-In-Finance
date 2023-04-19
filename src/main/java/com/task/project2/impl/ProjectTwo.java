package com.task.project2.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.distribution.UniformRealDistribution;
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

	private static final Logger LOG = Logger
			.getLogger("Project 2: Monte Carlo Simulation and Numerical Integration Techniques");
	private static final Scanner READER = new Scanner(System.in);

	private PlotAPI plot;
	private BlackScholesAPI bsApi;

	public void run() {

		ProjectTwoSolver solver = new ProjectTwoSolver(plot, bsApi);
		boolean keepExecuting = true;
		while (keepExecuting) {
			LOG.info(
					"\n\n1 - Generate a series of Bivariate-Normally distributed random vectors (x,y), with the mean vector of (0,0) and the variance – covariance matrix [[3,a],[a,5]] where a=0.7" // NOSONAR
							+ "\n2 - Evaluate the function f(X,Y) = max(0, Y^3+sinY+YX^2) using Monte Carlo"
							+ "\n3 - Estimate the two weiner processes for t = 1,3 and 5: (i) W^2 + sinW (ii) (e^(t/2)) * cosW"
							+ "\n4 - Estimate the value of call option using Monte Carlo and compare it with Black Scholes. Optimize using antithetic variates Parameters r = 0.05, sigma = 0.28, s0 = $100, T=5, K=$110"
							+ "\n5 - Estimate Stock value  assuming GBM via Monte Carlo and Path tracing and compare the two approaches"
							+ "\n6 - Estimate the following integral 4*sqrt(i-x*x) by Euler's discretization (Riemann sum) and Monte Carlo and optimize using independent sampling"
							+ "\nAny other number to exit\n");

			int x = READER.nextInt();

			switch (x) {
			case 1:
				solver.ques1(READER);
				break;
			case 2:
				solver.ques2();
				break;
			case 3:
				solver.ques3();
				break;
			case 4:
				solver.ques4();
				break;
			case 5:
				solver.ques5();
				break;
			case 6:
				solver.ques6();
				break;
			default:
				LOG.info("Exiting to previous menu!");
				keepExecuting = false;
			}
		}

	}

}
