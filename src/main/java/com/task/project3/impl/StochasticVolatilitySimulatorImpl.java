package com.task.project3.impl;

import java.util.Random;

import org.apache.commons.math3.util.FastMath;
import org.springframework.stereotype.Component;

import com.task.api.StochasticVolatilitySimulatorAPI;
import com.task.project3.impl.domain.StochasticVolatilityContext;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class StochasticVolatilitySimulatorImpl implements StochasticVolatilitySimulatorAPI {

	private static final Random RAND = new Random();

	
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
		double dt = ctx.getTimeStep();
		int numSimulations = ctx.getNumOfSimulations();
        int numTimeSteps = (int) (T / dt);
        double sumPayoffs = 0;

        // Loop through each simulation
        for (int i = 0; i < numSimulations; i++) {
            double S = S0;
            double V = V0;

            // Simulate the stock price and volatility path
            for (int t = 0; t < numTimeSteps; t++) {
                double dw1 = RAND.nextGaussian() * FastMath.sqrt(dt);
                double dw2 = RAND.nextGaussian() * FastMath.sqrt(dt);

                // Simulate stock price (using the stock price equation)
                S = S * FastMath.exp((r - 0.5 * V) * dt + FastMath.sqrt(V) * dw1);

                // Simulate volatility (using the volatility equation with full truncation)
                double dV = a * (b - FastMath.max(0, V)) * dt + sigma * FastMath.sqrt(FastMath.max(0, V)) * (rho * dw1 + FastMath.sqrt(1 - rho * rho) * dw2);
                V = FastMath.max(0, V + dV);
            }

            // Calculate the payoff at the end of the path
            double payoff = FastMath.max(S - K, 0);
            sumPayoffs += payoff;
        }

        // Estimate the call option price by averaging the payoffs and discounting them to present value
        double callPrice = FastMath.exp(-r * T) * (sumPayoffs / numSimulations);

        return callPrice;
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
	    double dt = ctx.getTimeStep();
	    int numSimulations = ctx.getNumOfSimulations();

	    int numTimeSteps = (int) (T / dt);
	    double sumPayoffs = 0;

	    // Loop through each simulation
	    for (int i = 0; i < numSimulations; i++) {
	        double S = S0;
	        double V = V0;
	        double logS = Math.log(S);

	        // Simulate the stock price and volatility path
	        for (int t = 0; t < numTimeSteps; t++) {
	            double dw1 = RAND.nextGaussian() * Math.sqrt(dt);
	            double dw2 = RAND.nextGaussian() * Math.sqrt(dt);

	            // Simulate stock price (using the stock price equation in log terms)
	            logS += (r - 0.5 * V) * dt + Math.sqrt(V) * dw1;

	            // Simulate volatility (using the volatility equation with partial truncation)
	            double dV = a * (b - V) * dt + sigma * Math.sqrt(Math.max(0, V)) * (rho * dw1 + Math.sqrt(1 - rho * rho) * dw2);
	            V += dV;

	            // Apply reflection to keep V non-negative
	            if (V < 0) {
	                V = -V;
	            }
	        }

	        S = Math.exp(logS);

	        // Calculate the payoff at the end of the path
	        double payoff = Math.max(S - K, 0);
	        sumPayoffs += payoff;
	    }

	    // Estimate the call option price by averaging the payoffs and discounting them to present value
	    double callPrice = Math.exp(-r * T) * (sumPayoffs / numSimulations);

	    return callPrice;
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
	    double dt = ctx.getTimeStep();
	    int numSimulations = ctx.getNumOfSimulations();

	    int numTimeSteps = (int) (T / dt);
	    double sumPayoffs = 0;

	    // Loop through each simulation
	    for (int i = 0; i < numSimulations; i++) {
	        double S = S0;
	        double V = V0;
	        double logS = Math.log(S);

	        // Simulate the stock price and volatility path
	        for (int t = 0; t < numTimeSteps; t++) {
	            double dw1 = RAND.nextGaussian() * Math.sqrt(dt);
	            double dw2 = RAND.nextGaussian() * Math.sqrt(dt);

	            // Simulate stock price (using the stock price equation in log terms)
	            logS += (r - 0.5 * V) * dt + Math.sqrt(V) * dw1;

	            // Simulate volatility (using the volatility equation with partial truncation)
	            double dV = a * (b - V) * dt + sigma * Math.sqrt(Math.max(0, V)) * (rho * dw1 + Math.sqrt(1 - rho * rho) * dw2);
	            V += dV;

	            // Apply reflection to keep V non-negative
	            if (V < 0) {
	                V = -V;
	            }
	        }

	        S = Math.exp(logS);

	        // Calculate the payoff at the end of the path
	        double payoff = Math.max(S - K, 0);
	        sumPayoffs += payoff;
	    }

	    // Estimate the call option price by averaging the payoffs and discounting them to present value
	    double callPrice = Math.exp(-r * T) * (sumPayoffs / numSimulations);

	    return callPrice;
	}

}
