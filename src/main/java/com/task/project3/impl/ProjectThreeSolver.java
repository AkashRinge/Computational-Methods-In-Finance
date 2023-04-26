package com.task.project3.impl;

import static com.task.project3.impl.ProjectThreeUtil.getWeiner;
import static com.task.project3.impl.ProjectThreeUtil.weinerProcess;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.task.api.BlackScholesAPI;
import com.task.api.CallOptionGreeksAPI;
import com.task.api.MCCallOptionSimulatorAPI;
import com.task.api.PlotAPI;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectThreeSolver {

	private static final Logger LOG = Logger.getLogger("Project 3 Solver");
	private BlackScholesAPI bsAPI;
	private MCCallOptionSimulatorAPI mcCallAPI;
	private CallOptionGreeksAPI greeksAPI;
	private PlotAPI plotAPI;
	
	public void ques1() {
		double p = 0;
	    double e1 = 0;
	    double e2 = 0;
	    double e3 = 0;
	    int n = 1000000;
	    double t1 = 2;
	    double t2 = 3;
	    double dt = 0.001;
	    for (int i = 0; i < n; i++) {
	      double y = q1stochasticProcessY(t1, dt);
	      if (y > 5) p += 1.0/n;
	      double x = q1stochasticProcessX(t1, dt);
	      e1 += Math.pow(x, 1.0/3) * dt / t1;
	      e3 += (x*y*(x > 1 ? 1 : 0)) * dt / t1;
	      e2 += q1stochasticProcessY(t2, dt) * dt / t2;
	    }
	    
	    String output = String.format("Results: \n\n p = %f \ne1 = %f, \ne2 = %f, \ne3 = %f", p,e1,e2,e3); //NOSONAR
	    LOG.info(output);
	}
	
	public void ques2() {
		double e1 = 0;
        double e2 = 0;
        double dt = 0.004; // time-step
        Map<Double, Double> dw = weinerProcess(dt, 3);
        Map<Double, Double> dz = weinerProcess(dt, 3);
        double x1 = q2StochasticProcessX(1, dt, dw, dz);
        double y1 = q2StochasticProcessY(1, dw, dz);
        for (double i = dt; i <= 3; i += dt) {
            e1 += Math.pow(1 + q2StochasticProcessX(i, dt, dw, dz), 1.0 / 3) * dt;
            e2 += x1 * y1 * dt;
        }
		
        String output = String.format("Results: \n\n e1 = %f, \ne2 = %f", e1,e2); //NOSONAR
	    LOG.info(output);
	}
	
	public void ques3(Scanner reader) {
		boolean keepExecuting = true;
		while(keepExecuting) {
			LOG.info("\nEnter S0 K r sigma T (all in a single line separated by spaces): \n\n");
			try {
				double s0 = reader.nextDouble();
				double k = reader.nextDouble();
				double r = reader.nextDouble();
				double sigma = reader.nextDouble();
				double T = reader.nextDouble();
				
				LOG.info("\n Call option value using antithetic variates (100000 simulations) = " + mcCallAPI.callOptionAntithetic(s0, k, r, sigma, T, 100000) // NOSONAR 
				+ "\n Call option value using Black Scholes = " + bsAPI.calculateCallOptionValue(s0, k, r, T, sigma));  
				keepExecuting = false;
			} catch (Exception ex) {
				LOG.log(Level.WARNING, "Error in input, please try again", ex);
			}
		}
		
		q3PlotGreeks(reader);  // At the bottom
	}

	
	public void ques4() {
		
	}
	
	public void ques5() {
		
	}
	
	private double q1stochasticProcessX(double t, double precision) {
	    double x = 1;
	    double dt = precision; // time-step
	    for (double i = dt; i <= t; i += dt) {
	      x += (1.0/5 - x/2) * dt + (2.0/3) * weinerProcess(dt);
	    }
	    return x;
	}
	
	private double q1stochasticProcessY(double t, double precision) {
	    double y = 3.0/4;
	    double dt = precision; // time-step
	    for (double i = dt; i <= t; i += dt) {
	      y += ((2*y)/(1+i) + ((1+i*i*i)/3)) * dt + ((1+i*i*i)/3) * weinerProcess(dt);
	    }
	    return y;
	  }
	
    private double q2StochasticProcessX(double t, double precision, Map<Double, Double> dw, Map<Double, Double> dz) {
        double x = 1;
        double dt = precision; // time-step
        for (double i = dt; i <= t; i += dt) {
            x += (x / 4) * dt + (x / 3) * dw.get(i) - (3 * x / 4) * dz.get(i);
        }
        return x;
    }
    
    private double q2StochasticProcessY(double t, Map<Double, Double> dw, Map<Double, Double> dz) {
        double W_t = getWeiner(dw, t);
        double Z_t = getWeiner(dz, t);
        return Math.exp(-0.08 * t + (W_t / 3) + (3 * Z_t / 4));
    }	
    
    private void q3PlotGreeks(Scanner reader) {
		boolean keepExecuting;
		LOG.info("\nThis segment will plot the sensitivities againt stock prices. Following are the model inputs: K = 20, sigma = 0.25, r = 0.05 and T = 0.5. S goes from 15-25. What do you want to plot?");
		keepExecuting = true;
		while(keepExecuting) {
	        double K = 20; // strike price
	        double T = 0.5; // time to maturity (in years)
	        double r = 0.05; // risk-free interest rate
	        double sigma = 0.25; // volatility of the stock
	        double dt = 0.004; // time step size (in years)
			try {
				LOG.info("\n\n1. Option prices vs S"
						+ "\n2. Delta vs S"
						+ "\n3. Vega vs S"
						+ "\n4. Gamma vs S"
						+ "\n5. Theta vs S"
						+ "\nEnter any other character to exit");
				int input = reader.nextInt();
				double[] stockPrices = new double[11];
				double s0 = 15;
				for(int i=0; i<11; i++) {
					stockPrices[i] = s0 + i;
				}
				switch(input) {
				case 1:
					double[] optionPrices = new double[11];
					for(int i=0; i<11; i++) {
						optionPrices[i] = mcCallAPI.callOption(s0+i, K, r, sigma, dt, 10000);
					}
					plotAPI.plotLine("Option vs S", "Option vs S", stockPrices, optionPrices);
					break;
				case 2:
					double[] delta = new double[11];
					for(int i=0; i<11; i++) {
						delta[i] = greeksAPI.getDelta(stockPrices[i], K, r, sigma, T, dt);
					}
					plotAPI.plotLine("Delta vs S", "Delta vs S", stockPrices, delta);
					break;
				case 3:
					double[] vega = new double[11];
					for(int i=0; i<11; i++) {
						vega[i] = greeksAPI.getVega(stockPrices[i], K, r, sigma, T, dt);
					}
					plotAPI.plotLine("Vega vs S", "Vega vs S", stockPrices, vega);
					break;
				case 4:
					double[] gamma = new double[11];
					for(int i=0; i<11; i++) {
						gamma[i] = greeksAPI.getGamma(stockPrices[i], K, r, sigma, T, dt);
					}
					plotAPI.plotLine("Gamma vs S", "Gamma vs S", stockPrices, gamma);
					break;
				case 5:
					double[] theta = new double[11];
					for(int i=0; i<11; i++) {
						theta[i] = greeksAPI.getTheta(stockPrices[i], K, r, sigma, T, dt);
					}
					plotAPI.plotLine("Theta vs S", "Theta vs S", stockPrices, theta);
					break;
				default:
					LOG.info("Opted to exit, going back to project 3");
					keepExecuting = false;
				}
			} catch (Exception ex) {
				LOG.info("Opted to exit, going back to project 3");
				keepExecuting = false;
			}
		}
	}
	
}
