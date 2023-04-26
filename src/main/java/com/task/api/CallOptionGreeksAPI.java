package com.task.api;

public interface CallOptionGreeksAPI {
	/**
	 *  Calculate Call Option Delta
	 * @param S
	 * @param X
	 * @param r
	 * @param sigma
	 * @param T
	 * @param dt
	 * @return
	 */
	 public double getDelta(double S, double X, double r, double sigma, double T, double dt);
	 
	 /***
	  * Calculate call option gamma 
	  * 
	  * @param S
	  * @param X
	  * @param r
	  * @param sigma
	  * @param T
	  * @param dt
	  * @return
	  */
	 public double getGamma(double S, double X, double r, double sigma, double T, double dt);
	 
	 /***
	  * calculate call option theta
	  * 
	  * @param S
	  * @param X
	  * @param r
	  * @param sigma
	  * @param T
	  * @param dt
	  * @return
	  */
	 public double getTheta(double S, double X, double r, double sigma, double T, double dt);
	 
	 /***
	  * calculate call option Vega
	  * 
	  * @param S
	  * @param X
	  * @param r
	  * @param sigma
	  * @param T
	  * @param dt
	  * @return
	  */
	 public double getVega(double S, double X, double r, double sigma, double T, double dt);
}
