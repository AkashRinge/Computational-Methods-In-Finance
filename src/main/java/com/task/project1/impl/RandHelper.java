package com.task.project1.impl;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandHelper {

	private static final RandHelper SINGLETON = new RandHelper();

	static RandHelper instance() {
		return SINGLETON;
	}

	double[] uniformDist(int n, int seed) {
		Random random = new Random(seed);
		double[] rand = new double[n];
		for (int i = 0; i < n; i++) {
			rand[i] = random.nextDouble();
		}
		return rand;
	}
	
	double[] polarMargDist(int sampleSize, double mu, double sigma, RandomGenerator lgmApi) {
		double[] dist = new double[sampleSize]; 
		for(int i=0; i<sampleSize; i++) {
			double u, v, s; 
	        do {
	            u = lgmApi.nextDouble() * 2 - 1;
	            v = lgmApi.nextDouble() * 2 - 1;
	            s = u * u + v * v;
	        } while (s >= 1 || s == 0);

	        double z = u * Math.sqrt(-2 * Math.log(s) / s);
	        dist[i] = mu + sigma * z;
		}
		return dist;
	}

	double[] boxMullerDist(int sampleSize, double mu, double sigma, RandomGenerator lgmApi) {
		double[] dist = new double[sampleSize]; 
		for(int i=0; i<sampleSize; i++) {
			double u1 = lgmApi.nextDouble();
	        double u2 = lgmApi.nextDouble();
			double z = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);
	        dist[i] = mu + sigma * z;
		}
		return dist;
	}

	double[] exponentialDist(int sampleSize, double lamda, RandomGenerator lgmApi) {
		ExponentialDistribution expDist = new ExponentialDistribution(lgmApi, 1 / lamda);
		return expDist.sample(sampleSize);
	}

	double[] binomialDist(int sampleSize, int n, double p, RandomGenerator lgmApi) {
		BinomialDistribution dist = new BinomialDistribution(lgmApi, n, p);
		int[] arr = dist.sample(sampleSize);
		return Arrays.stream(arr).asDoubleStream().toArray();
	}

	double[] modifiedDist(int n, double[] values, double[] prob, double[] uniDist) {
		double[] rand = new double[n];
		for (int i = 0; i < n; i++) {
			rand[i] = randomChoiceWithProbability(values, prob, uniDist[i]);
		}
		return rand;
	}

	private double randomChoiceWithProbability(double[] values, double[] probabilities, double uniDistVar) {
		if (values.length != probabilities.length) {
			throw new IllegalArgumentException("Values and probabilities arrays must be of the same length.");
		}

		double sumProbabilities = 0.0;
		for (double p : probabilities) {
			if (p < 0) {
				throw new IllegalArgumentException("Probabilities must be non-negative.");
			}
			sumProbabilities += p;
		}

		if (sumProbabilities == 0.0) {
			throw new IllegalArgumentException("Probabilities must sum to a positive value.");
		}

		double randomValue = uniDistVar * sumProbabilities;

		double cumulativeProbability = 0.0;
		for (int i = 0; i < probabilities.length; i++) {
			cumulativeProbability += probabilities[i];
			if (randomValue < cumulativeProbability) {
				return values[i];
			}
		}

		return values[values.length - 1];
	}

}
