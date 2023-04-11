package com.task.api;

/**
 * 
 * Uses Lewis, Goodman, and Miller algorithm to generate random numbers
 * 
 */
public interface LgmRandomGeneratorAPI {
	
	/**
	 * Generated uniform distribution between [0,1]
	 * 
	 * @param n - number of rand numbers to be generated
	 * @param seed - seed 
	 * @return
	 */
	public double[] uniformDist(int n, int seed);
}
