package com.task.project2.impl;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * This is my Random generation helper. I have added capabilities to compute 
 * the bivariate normal distribution etc
 * 
 * @author food4thought
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandHelper {

	private static final RandHelper SINGLETON = new RandHelper();

	static RandHelper instance() {
		return SINGLETON;
	}

	public double[][] bivariateDist(int sampleSize, double[] means, double[][] covariances) {
		double[][] result = new double[sampleSize][];
		MultivariateNormalDistribution bivariateNormal = new MultivariateNormalDistribution(means, covariances);
		for (int i=0; i<sampleSize; i++) {
			result[i] = bivariateNormal.sample();
		}
		return result;
	}
}
