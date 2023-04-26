package com.task.api;

public interface MCCallOptionSimulatorAPI {

	/***
	 * Performs a Monte Carlo Call option estimation by discounting the call option payoff to present value
	 * 
	 * @param s0 - stock price at time 0
	 * @param k - strike price
	 * @param r - risk free rate
	 * @param sigma - expected volatility of return distribution
	 * @param T - maturity
	 * @param n - No of simulations to be done
	 * @return
	 */
	public double callOption(double s0, double k, double r, double sigma, double T, int n);
	
	/***
	 * Performs a Monte Carlo Call option estimation by discounting the call option payoff to present value 
	 * using antithetic variates
	 * 
	 * @param s0 - stock price at time 0
	 * @param k - strike price
	 * @param r - risk free rate
	 * @param sigma - expected volatility of return distribution
	 * @param T - maturity
	 * @param n - No of simulations to be done
	 * @return
	 */
	public double callOptionAntithetic(double s0, double k, double r, double sigma, double T, int n);
	
	public double callOptionControl(double s0, double k, double r, double sigma, double T, int n);
	
}
