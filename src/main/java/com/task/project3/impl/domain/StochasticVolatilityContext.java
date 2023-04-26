package com.task.project3.impl.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StochasticVolatilityContext {
	 private double rho;
	 private double r;
	 private double S0;
	 private double V0;
	 private double sigma;
	 private double a;
	 private double b; 
	 private double K; 
	 private double T;
	 private int numSteps;
	 private int numPaths;
}
