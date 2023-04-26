package com.task.project3.impl;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.math3.distribution.MultivariateNormalDistribution;
import org.apache.commons.math3.util.FastMath;

import com.task.api.StochasticVolatilitySimulatorAPI;
import com.task.project3.impl.domain.StochasticVolatilityContext;

public class StochasticVolatilitySimulatorImpl implements StochasticVolatilitySimulatorAPI {

	@Override
	public double callOptionFullTruncation(StochasticVolatilityContext ctx) {
		double T = ctx.getT();
		double S0 = ctx.getS0();
		double K = ctx.getK();
		double r = ctx.getR();
		double V0 = ctx.getV0();
		double a = ctx.getA();
		double b = ctx.getB();
		double sigma = ctx.getSigma();
		double rho = ctx.getRho();
		int N = ctx.getNumSteps();
		int M = ctx.getNumPaths();
		
		double dt = T / N;
		double[][] dW = new double[N + 1][2];
		double[][] dZ = new double[N + 1][2];
		double[][] S = new double[N + 1][M + 1];
		double[][] V = new double[N + 1][M + 1];
		double[][] C = new double[N + 1][M + 1];

		// Initialize the stock price and volatility at time 0
		S[0][0] = S0;
		V[0][0] = V0;

		// Generate the Wiener and Brownian motions
		Random rand = new Random();
		for (int i = 1; i <= N; i++) {
			dW[i][0] = Math.sqrt(dt) * rand.nextGaussian();
			dW[i][1] = Math.sqrt(dt) * rand.nextGaussian();
			dZ[i][0] = rho * dW[i][0] + Math.sqrt(1 - rho * rho) * rand.nextGaussian();
			dZ[i][1] = rho * dW[i][1] + Math.sqrt(1 - rho * rho) * rand.nextGaussian();
		}

		// Calculate the stock price and volatility at each time step
		for (int i = 1; i <= N; i++) {
			for (int j = 0; j <= M; j++) {
				double dS = r * S[i - 1][j] * dt + Math.sqrt(V[i - 1][j]) * S[i - 1][j] * dW[i][0];
				double dV = a * (b - V[i - 1][j]) * dt + sigma * Math.sqrt(V[i - 1][j]) * dZ[i][1];
				S[i][j] = S[i - 1][j] + dS;
				V[i][j] = Math.max(V[i - 1][j] + dV, 0); // Full truncation
				C[i][j] = Math.max(S[i][j] - K, 0);
			}
		}

		// Calculate the expected payoff of the call option
		double sum = 0;
		for (int j = 0; j <= M; j++) {
			sum += C[N][j];
		}
		return Math.exp(-r * T) * sum / (M + 1);
	}

	@Override
	public double callOptionPartialTruncation(StochasticVolatilityContext ctx) {
		double T = ctx.getT();
		double S0 = ctx.getS0();
		double K = ctx.getK();
		double r = ctx.getR();
		double V0 = ctx.getV0();
		double a = ctx.getA();
		double b = ctx.getB();
		double sigma = ctx.getSigma();
		double rho = ctx.getRho();
		int numSteps = ctx.getNumSteps();
		int numSimulations = ctx.getNumPaths();
		
		double dt = T / numSteps;
		double sqrtDt = FastMath.sqrt(dt);

		// Initialize the stock and volatility processes
		double[] stock = new double[numSimulations];
		double[] vol = new double[numSimulations];

		Arrays.fill(stock, S0);
		Arrays.fill(vol, V0);

		// Compute the drift and volatility of the Brownian motions
		double mu1 = r - 0.5 * vol[0];
		double mu2 = a * (b - vol[0]);
		double vol1 = sigma * FastMath.sqrt(vol[0]);
		double vol2 = FastMath.sqrt((1 - rho * rho) * vol[0]);

		// Create the multivariate normal distribution
		MultivariateNormalDistribution mvn = new MultivariateNormalDistribution(new double[] { 0, 0 },
				new double[][] { { 1, rho }, { rho, 1 } });

		// Compute the option prices at each step
		for (int i = 0; i < numSteps; i++) {
			// Generate the correlated Brownian motions
			double[][] rand = mvn.sample(numSimulations);
			double[] z1 = rand[0];
			double[] z2 = rand[1];

			// Compute the stock and volatility increments
			double[] dS = new double[numSimulations];
			double[] dV = new double[numSimulations];
			for (int j = 0; j < numSimulations; j++) {
				dS[j] = mu1 * dt + vol1 * sqrtDt * z1[j];
				dV[j] = mu2 * dt + vol2 * sqrtDt * (rho * z1[j] + FastMath.sqrt(1 - rho * rho) * z2[j]);
			}

			// Apply the partial truncation
			for (int j = 0; j < numSimulations; j++) {
				double newVol = FastMath.max(vol[j] + dV[j], 0); // Apply truncation
				double newStock = stock[j] * FastMath.exp(dS[j]); // Geometric Brownian motion

				// Update the stock and volatility processes
				vol[j] = newVol;
				stock[j] = newStock;
			}
		}

		// Compute the payoff at maturity
		double sum = 0;
		for (int i = 0; i < numSimulations; i++) {
			double payoff = FastMath.max(stock[i] - K, 0);
			sum += payoff;
		}

		// Compute the option price
		double optionPrice = FastMath.exp(-r * T) * sum / numSimulations;

		return optionPrice;
	}

	@Override
	public double callOptionReflection(StochasticVolatilityContext ctx) {
		double T = ctx.getT();
		double S0 = ctx.getS0();
		double K = ctx.getK();
		double r = ctx.getR();
		double V0 = ctx.getV0();
		double a = ctx.getA();
		double b = ctx.getB();
		double sigma = ctx.getSigma();
		double rho = ctx.getRho();
		int nSteps = ctx.getNumSteps();
		int nPaths = ctx.getNumPaths();
		
	    double dt = T / nSteps; // time step
	    double sqrtDt = Math.sqrt(dt);
	    double[][] cov = { { 1, rho }, { rho, 1 } }; // covariance matrix
	    
	    // Initialize arrays for stock price and volatility
	    double[] S = new double[nPaths];
	    double[] V = new double[nPaths];
	    double[] S_hat = new double[nPaths];
	    double[] V_hat = new double[nPaths];
	    
	    // Initialize variables for the option price and discount factor
	    double payoffSum = 0.0;
	    double discount = Math.exp(-r * T);
	    
	    // Generate random normal variables using MultivariateNormalDistribution
	    MultivariateNormalDistribution normal = new MultivariateNormalDistribution(new double[] { 0, 0 }, cov);
	    
	    // Monte Carlo simulation
	    for (int i = 0; i < nPaths; i++) {
	        S[i] = S0;
	        V[i] = V0;
	        S_hat[i] = S0;
	        V_hat[i] = V0;
	        
	        // Generate two sets of correlated random variables
	        double[] x = normal.sample();
	        double w1 = x[0];
	        double w2 = rho * w1 + Math.sqrt(1 - rho * rho) * x[1];
	        
	        // Simulate the stock price and volatility using full truncation
	        for (int j = 1; j <= nSteps; j++) {
	            double dW1 = w1 * sqrtDt;
	            double dW2 = w2 * sqrtDt;
	            double S_sqrtV = S[i] * Math.sqrt(V[i]);
	            double S_sqrtV_hat = S_hat[i] * Math.sqrt(V_hat[i]);
	            
	            // Simulate the stock price and volatility
	            S[i] = S[i] + r * S[i] * dt + S_sqrtV * dW1;
	            V[i] = V[i] + a * (b - V[i]) * dt + sigma * S_sqrtV * dW2;
	            S[i] = Math.max(S[i], 0.0); // reflect at zero
	            V[i] = Math.max(V[i], 0.0); // reflect at zero
	            
	            // Simulate the reflected stock price and volatility
	            S_hat[i] = S_hat[i] + r * S_hat[i] * dt + S_sqrtV_hat * dW1;
	            V_hat[i] = V_hat[i] + a * (b - V_hat[i]) * dt + sigma * S_sqrtV_hat * dW2;
	            S_hat[i] = Math.abs(S_hat[i]); // reflect across zero
	            V_hat[i] = Math.abs(V_hat[i]); // reflect across zero
	        }
	        
	        // Calculate the option payoff
	        double payoff = Math.max(S[i] - K, 0.0);
	        double payoff_hat = Math.max(S_hat[i] - K, 0.0);
	        
	        // Update the option price estimate
	        payoffSum += 0.5 * (payoff + payoff_hat);
	    }
	    
	    // Return the option price estimate
	    return discount * (payoffSum / nPaths);
	}

}
