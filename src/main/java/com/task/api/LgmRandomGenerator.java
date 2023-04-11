package com.task.api;

/**
 * 
 * Uses Lewis, Goodman, and Miller algorithm to generate random numbers
 * 
 */
public interface LgmRandomGenerator {
	
	/**
	 * 
	 * @param start - range start
	 * @param end - range end
	 * @param seed - seed 
	 * @return
	 */
	public float generateUniformNumbers(int start, int end, int seed);
}
