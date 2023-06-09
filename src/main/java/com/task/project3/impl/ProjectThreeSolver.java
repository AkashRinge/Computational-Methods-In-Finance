package com.task.project3.impl;

import static com.task.project3.impl.ProjectThreeUtil.getWeiner;
import static com.task.project3.impl.ProjectThreeUtil.weinerProcess;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.math3.random.HaltonSequenceGenerator;

import com.task.api.BlackScholesAPI;
import com.task.api.CallOptionGreeksAPI;
import com.task.api.MCCallOptionSimulatorAPI;
import com.task.api.PlotAPI;
import com.task.api.StochasticVolatilitySimulatorAPI;
import com.task.project3.impl.domain.StochasticVolatilityContext;
import com.task.util.GeneralHelper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectThreeSolver {

	private static final Logger LOG = Logger.getLogger("Project 3 Solver");
	private static final Random RAND = new Random();
	
	private PlotAPI plotAPI;
	private BlackScholesAPI bsAPI;
	private MCCallOptionSimulatorAPI mcCallAPI;
	private CallOptionGreeksAPI greeksAPI;
	private StochasticVolatilitySimulatorAPI stocVolAPI;
	
	public void ques1() {
		double p = 0;
	    double e1 = 0;
	    double e2 = 0;
	    double e3 = 0;
	    int n = 10000;
	    double t1 = 2;
	    double t2 = 3;
	    double dt = 0.004;
	    for (int i = 0; i < n; i++) {
	      double y = q1stochasticProcessY(t1, dt);
	      if (y > 5) p += 1.0/n;
	      double x = q1stochasticProcessX(t1, dt);
	      e1 += Math.pow(Math.abs(x), 1.0/3) * (Math.abs(x)/x) * dt / t1;
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
        double[] dw = weinerProcess(3, dt);
        double[] dz = weinerProcess(3, dt);
        double x1 = q2StochasticProcessX(1, dt, dw, dz);
        double y1 = q2StochasticProcessY(1, dt, dw, dz);
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

	
	public void ques4(Scanner reader) {
		double dt = 0.004;
		int n = 10000;
		double rho = -0.6;
		double r = 0.03;
		double s0 = 48;
		double v0 = 0.05;
		double sigma = 0.42;
		double a = 5.8;
		double b = 0.0625;
		
		boolean keepExecuting = true;
		while(keepExecuting) {
			LOG.info("\n\nFollowing are the default model parameters rho = -0.6, r = 0.03, s0 = $48, v0 = 0.05, "
					+ "sigma = 0.42, a = 5.8, b = 0.0625. Enter K and T (in a single line separated by spaces): \n\n");
			try {
				double k = reader.nextDouble();
				double T = reader.nextDouble();
				
				StochasticVolatilityContext ctx = new StochasticVolatilityContext(rho, r, s0, v0, sigma, a, b, k, T, dt, n);
				
				LOG.info("\n Call option value using full truncation = " + stocVolAPI.callOptionFullTruncation(ctx)// NOSONAR 
				+ "\n Call option value using partial truncation = " + stocVolAPI.callOptionPartialTruncation(ctx)
				+ "\n Call option value using reflection = " + stocVolAPI.callOptionReflection(ctx));
				keepExecuting = false;
			} catch (Exception ex) {
				LOG.log(Level.SEVERE, "Error in input, exitting", ex);
				keepExecuting = false;
			}
		}
	}
	
	public void ques5(Scanner reader) {
		double[][] psuedoRandom = q5GenerateUniform2DVectors(100);
		double[][] haltonSeq1 = new double[100][2];
		double[][] haltonSeq2 = new double[100][2];
		
		int[] bases1 = {2, 7};
		int[] bases2 = {2, 4};
		int[] wts = {1,1};
		
        HaltonSequenceGenerator halton1 = new HaltonSequenceGenerator(2, bases1, wts);
        HaltonSequenceGenerator halton2 = new HaltonSequenceGenerator(2, bases2, wts);
        
        for(int i=0; i<100; i++) {
        	haltonSeq1[i] = halton1.nextVector();
        	haltonSeq2[i] = halton2.nextVector();
        }
        
        LOG.info("\nWhat do you want to plot");
		boolean keepExecuting = true;
		List<double[]> data;
		double[][] seq;
		String appTitle = "Uniform numbers";
		while(keepExecuting) {
			try {
				LOG.info("\n\n1. Pseudo Random sequence"
						+ "\n2. Halton sequence 1 (bases 2, 7)"
						+ "\n3. Halton sequence 2 (bases 2, 4)"
						+ "\nEnter any other number to exit");
				int input = reader.nextInt();
				switch(input) {
				case 1:
					seq = GeneralHelper.transpose(psuedoRandom);
					data = new LinkedList<>();
					data.add(seq[0]);
					data.add(seq[1]);
					plotAPI.plotMultiLine("Pseduo Random Sequence", appTitle, data, List.of("Seq1", "Seq2"), "", "n");
					break;
				case 2:
					seq = GeneralHelper.transpose(haltonSeq1);
					data = new LinkedList<>();
					data.add(seq[0]);
					data.add(seq[1]);
					plotAPI.plotMultiLine("Halton Sequence 1", appTitle, data, List.of("Base 2", "Base 7"), "", "n");
					break;
				case 3:
					seq = GeneralHelper.transpose(haltonSeq2);
					data = new LinkedList<>();
					data.add(seq[0]);
					data.add(seq[1]);
					plotAPI.plotMultiLine("Halton Sequence 2", appTitle, data, List.of("Base 2", "Base 4"), "", "n");
					break;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
				LOG.info("Opted to exit, going back to project 3");
				keepExecuting = false;
			}
		}
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
	
    private double q2StochasticProcessX(double t, double precision, double[] dw, double[] dz) {
        double x = 1;
        double dt = precision; // time-step
        int ct = 0;
        for (double i = dt; i <= t; i += dt) {
            x += (x / 4) * dt + (x / 3) * dw[ct] - (3 * x / 4) * dz[ct];
            ct++;
        }
        return x;
    }
    
    private double q2StochasticProcessY(double t, double dt, double[] dw, double[] dz) {
        double W_t = getWeiner(dw, t, dt);
        double Z_t = getWeiner(dz, t, dt);
        return Math.exp(-0.08 * t + (W_t / 3) + (3 * Z_t / 4));
    }	
    
    private void q3PlotGreeks(Scanner reader) {
		boolean keepExecuting;
		LOG.info("\n\nThis segment will plot the sensitivities againt stock prices. Following are the model inputs: K = 20, sigma = 0.25, r = 0.05 and T = 0.5. S goes from 15-25. What do you want to plot?");
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
    
	private double[][] q5GenerateUniform2DVectors(int n) {
	    
	    double[][] vectors = new double[n][2];
	    for (int i = 0; i < n; i++) {
	        vectors[i][0] = RAND.nextDouble();
	        vectors[i][1] = RAND.nextDouble();
	    }
	    return vectors;
	}
}
