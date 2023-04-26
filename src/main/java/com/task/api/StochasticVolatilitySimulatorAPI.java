package com.task.api;

import com.task.project3.impl.domain.StochasticVolatilityContext;

/**
 * This API calculates call option values assuming that volatility is stochastic.   
 * <br> Stock prices in this model follow GBM with weiner dw_1(t)
 * <br> Volatility follows the following SDE -> dV(t) = a(b - V(t)) dt + sigma . (sqrt(V(t)) . dw_2(t) 
 * <br> dw_1(t) dw_2 (t) = rho dt
 * 
 * 
 * @author food4thought
 *
 */
public interface StochasticVolatilitySimulatorAPI {

	/***
	 * Full truncation method - In this method, we truncate the diffusion term in the stochastic differential equation to remove the dependency on the Wiener process, 
	 * which makes it easier to simulate. However, this method can be computationally expensive and may introduce bias in the results.
	 * 
	 * @param rho
	 * @param r
	 * @param S0
	 * @param V0
	 * @param sigma
	 * @param a
	 * @param b
	 * @param K
	 * @param T
	 * @param numSteps
	 * @param numPaths
	 * @return
	 */
	double callOptionFullTruncation(StochasticVolatilityContext ctx);
	
	/***
	 * Partial Truncation -  This method involves truncating the diffusion term in the stochastic differential equation but retaining a portion of it to reduce bias in the results. 
	 * This method can be faster than full truncation and can still provide accurate results.
	 * 
	 * @param rho
	 * @param r
	 * @param S0
	 * @param V0
	 * @param sigma
	 * @param a
	 * @param b
	 * @param K
	 * @param T
	 * @param numSteps
	 * @param numPaths
	 * @return
	 */
	double callOptionPartialTruncation(StochasticVolatilityContext ctx);
	
	
	/***
	 * Reflection method -  In this method, we simulate the stock price and volatility together and ensure that the volatility remains positive by reflecting the simulated values across zero. 
	 * This approach can provide more accurate results than truncation methods, especially when volatility is low, but can also be computationally expensive.
	 * 
	 * @param ctx
	 * @return
	 */
	double callOptionReflection(StochasticVolatilityContext ctx);
}
