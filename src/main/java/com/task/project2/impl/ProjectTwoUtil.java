package com.task.project2.impl;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;

import com.task.domain.MCOperation;
import com.task.util.GeneralHelper;
import com.task.util.StatsHelper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.NONE)
public class ProjectTwoUtil {
	
	static final NormalDistribution STANDARD_NORMAL = new NormalDistribution(0, 1);
	
	static double bivariateCorr(double[][] bivariateNormDist, int n) {
		double[][] bivariateNormDistT = GeneralHelper.transpose(bivariateNormDist);
		
		double cov = StatsHelper.covariance(bivariateNormDistT[0], bivariateNormDistT[1], n);
		double sigma1 = StatsHelper.variance(bivariateNormDistT[0]);
		double sigma2 = StatsHelper.variance(bivariateNormDistT[1]);
		
		return cov / (sigma1 * sigma2);
	}
	
	static double stdWeiner(double t) {
		return STANDARD_NORMAL.sample() * Math.sqrt(t);
	}
	
	/**
	 * Computes the reimann sum between limts a and b
	 * @param steps
	 * @param function
	 * @return
	 */
	static double estimateIntegralReimann(int steps, MCOperation<Double> function, double a, double b) {
		double result = 0;
		double h = (b-a)/steps;
		for(int i=0; i<steps; i++) {
			result += function.run(a+i*h);
		}
		result *= h;
		return result;
	}
	
	static double estimateIntegralMC(int steps, MCOperation<Double> function, double a, double b) {
		double integral = 0;
		double[] x = new UniformRealDistribution(a,b).sample(steps);
		for(int i=0; i<steps; i++) {
			integral += (function.run(x[i])/steps);
		}
		return integral;
	}
	
	
}
