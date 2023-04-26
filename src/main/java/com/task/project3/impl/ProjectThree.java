package com.task.project3.impl;

import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.task.api.BlackScholesAPI;
import com.task.api.MCCallOptionSimulatorAPI;
import com.task.api.PlotAPI;

import lombok.AllArgsConstructor;

/***
 * 
 * 
 * @author food4thought
 */
@Component
@AllArgsConstructor
public class ProjectThree implements Runnable {
	private static final Logger LOG = Logger
			.getLogger("Project 3: Monte Carlo Simulation continued with Ito Processes and Call option simulation");
	private static final Scanner READER = new Scanner(System.in);

	private PlotAPI plotAPI;
	private BlackScholesAPI bsAPI;
	private MCCallOptionSimulatorAPI mcCallAPI;
	
	@Override
	public void run() {
		ProjectThreeSolver solve = new ProjectThreeSolver(bsAPI, mcCallAPI, plotAPI);
		boolean keepExecuting = true;
		while (keepExecuting) {
			LOG.info(
					"\n\n1 - For the given SDE Ito processes X(t),Y(t) evaluate (i) prob = P(Y(2) > 5)  (ii) E(X(2) ^ (1/3))  (iii) E(Y(3))  (iv) E(X(2) Y(2) 1 (X2 > 1))" // NOSONAR
							+ "\n2 - For the given SDE Ito processes X(t),Y(t) evaluate (i) E( (1 + X(3)) ^ (1/3) )  (ii) E(X(1)Y(1)) "
							+ "\n3 - Estimate the value of call option using Monte Carlo, Optimize using variance reduction techniques and compare it with Black Scholes.  Also calculate the sensitivities"
							+ "\n4 - Given the 2-factor model for stock prices with stochastic volatility, simulate call option using Monte Carlo using  Full Truncation, Partial Truncation, and Reflection"
							+ "\n5 - compare a sample of Pseudo-Random numbers with a sample of Quasi-Monte Carlo numbers "
							+ "\nAny other number to exit\n");

			int x = READER.nextInt();

			switch (x) {
			case 1:
				solve.ques1();
				break;
			case 2:
				solve.ques2();
				break;
			case 3:
				solve.ques3(READER);
				break;
			case 4:
				solve.ques4();
				break;
			case 5:
				solve.ques5();
				break;
			default:
				LOG.info("Exiting to previous menu!");
				keepExecuting = false;
			}
		}
	}
}
