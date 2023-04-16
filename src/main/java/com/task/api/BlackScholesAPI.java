package com.task.api;

/**
 * API to Calculate Black scholes call option value
 * 
 * @author food4thought
 *
 */
public interface BlackScholesAPI {

	/***
	 * 
	 * @param s  current stock price
	 * @param k  option strike price
	 * @param r  risk-free interest rate
	 * @param t  time to expiration in years
	 * @param sigma  stock price volatility
	 * @return
	 */
	public double calculateCallOptionValue(double s, double k, double r, double t, double sigma);
	
}
