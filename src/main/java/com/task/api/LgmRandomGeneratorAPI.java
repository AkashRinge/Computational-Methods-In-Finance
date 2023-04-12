package com.task.api;

import org.apache.commons.math3.random.RandomGenerator;

/**
 * 
 * Uses Lewis, Goodman, and Miller algorithm to generate random numbers
 * 
 */
public interface LgmRandomGeneratorAPI extends RandomGenerator {
	
	/**
	 * Generated uniform distribution between [0,1]
	 * 
	 * @param n - number of rand numbers to be generated
	 * @param seed - seed 
	 * @return
	 */
	public double[] uniformDist(int n, int seed);
}
