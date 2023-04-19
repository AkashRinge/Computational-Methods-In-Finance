package com.task.project1.impl;

import static com.task.util.StatsHelper.mean;
import static com.task.util.StatsHelper.variance;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.task.api.LgmRandomGeneratorAPI;
import com.task.api.PlotAPI;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProjectOne {
	
	private static final String CHART_TITLE = "Frequency VS Value";
	private static final Logger LOG = Logger.getLogger("Project 1: Random Number Generation");
	private static final Scanner READER = new Scanner(System.in);
	private static final Integer SEED = 42;
	
	private LgmRandomGeneratorAPI generator;
	private PlotAPI plot;
	
	public void run() {
		int x = 1;
		
		// Q1 parameters
		double[] lgm = generator.uniformDist(10000, SEED);
		double[] software = RandHelper.instance().uniformDist(10000, SEED);
		double lgmMean = mean(lgm);
		double lgmVar=  variance(lgm);
		double softMean = mean(software);
		double softVar = variance(software);
		
		// Q2 parameters
		double[] modDist = RandHelper.instance().modifiedDist(10000, new double[] {-1,0,1,2}, 
				new double[] {0.3,0.35,0.2,0.15}, lgm);
		double modMean = mean(modDist);
		double modVar = variance(modDist);
		
		//Q3 parameters
		double[] binomial = RandHelper.instance().binomialDist(1000, 44, 0.64, generator);
		double probGreater40 = 0;
		for(int i=0; i<1000; i++) {
			if (binomial[i]>=40) {
				probGreater40 += 1; 
			}
		}
		probGreater40 /= 1000;
		
		
		//Q4 parameters
		double[] exponential = RandHelper.instance().exponentialDist(10000, 1.5, generator);
		double probGreater2 = 0;
		for(int i=0; i<10000; i++) {
			if (exponential[i]>=2) {
				probGreater2 += 1; 
			}
		}
		probGreater2 /= 10000;
		double expMean = mean(exponential);
		double expVar = variance(exponential);
		
		boolean keepExecuting = true;
		while (keepExecuting) {
			LOG.info("\n\n1 - Generate a Uniform distribution of 10000 numbers between [0,1] using LGM algo" 			//NOSONAR
				+ "\n2 - Generate a Uniform distribution of 10000 numbers using inbuilt algo"
				+ "\n3 - Compare distributions generated in 1 and 2"
				+ "\n4 - Generate random numbers with following probablities P(-1)=0.3;  P(0)=0.35;  P(1)=0.20;  P(2)=0.15"
				+ "\n5 - Generate 1,000 random numbers with Binomial distribution with n = 44 and p = 0.64"
				+ "\n6 - Generate 10,000 Exponentially distributed random numbers with parameter lamda = 1.5"
				+ "\n7 - Generate 500,000 standard random numbers by the Box- Muller Method and the Polar-Marsaglia method, and compare their efficiencies"
				+ "\nAny other number to exit\n"); 
			
			x = READER.nextInt();
		
			switch(x) {
				case 1:
					LOG.info(String.format("\n Mean: %f \t Standard Deviation: %f \n\n", lgmMean, Math.sqrt(lgmVar)));
					plot.plotHist("LGM Distribution Histogram", CHART_TITLE, lgm, 100);
					break;
				case 2:
					LOG.info(String.format("\n Mean: %f \t Standard Deviation: %f \n\n", softMean, Math.sqrt(softVar)));
					plot.plotHist("Software Generated Histogram", CHART_TITLE, software, 100);
					break;
				case 3:
	
					plot.plotMultiLine("Distribution of LGM vs Software", "LGM vs Software", List.of(lgm, software), List.of("LGM", "Software"), "i", "ith Random number");
					LOG.info("As we can see the plots follow exactly similar trend to each other\n\n");
					break;
				case 4:
					LOG.info(String.format("\n Mean: %f \t Standard Deviation: %f \n\n", modMean, Math.sqrt(modVar)));
					plot.plotHist("Software Generated Histogram", CHART_TITLE, modDist, 15);
					break;
				case 5: 
					LOG.info("\nFor the given binomial distribution Probablity P(X >= 40) = " + probGreater40);
					plot.plotHist("Histogram of binomial distribution", CHART_TITLE, binomial, 40);
					break;
				case 6:
					LOG.info("\nFor the given exponential distribution Probablity P(X >= 2) = " + probGreater2);
					LOG.info(String.format("\n Mean: %f \t Standard Deviation: %f \n\n", expMean, Math.sqrt(expVar)));
					plot.plotHist("Exponential Distribution Histogram", CHART_TITLE, exponential, 100);
					break;
				case 7:
					LOG.info("\nRunning the Box Muller algorithm first \n");
					long time1 = runBoxMuller();
					LOG.info("Enter any letter/number to continue: "); 
					READER.next();
					LOG.info("\n\nRunning Polar Marsaglia now \n");
					long time2 = runPolarMars();
					LOG.info(String.format("\n\nTime taken (in ms) by Box Muller:  %d \nTime taken (in ms) by Polar Margsaglia: %d", time1, time2));
					LOG.info("As observed Polar Margsaglia is slightly more expensive than Box Muller");
					break;
				default:
					LOG.info("Exiting to previous menu!");
					keepExecuting = false;
			}
		}	
	}
	
	private long runBoxMuller() {
		StopWatch w = new StopWatch();
		w.start();
		double[] dist = RandHelper.instance().boxMullerDist(500000, 0, 1, generator);
		w.stop();
		plot.plotHist("Box Muller Frequency Distribution", CHART_TITLE, dist, 100);
		return w.getLastTaskTimeMillis();
	}
	
	private long runPolarMars() {
		StopWatch w = new StopWatch();
		w.start();
		double[] dist = RandHelper.instance().polarMargDist(500000, 0, 1, generator);
		w.stop();
		plot.plotHist("Polar Marsaglia Frequency Distribution", CHART_TITLE, dist, 100);
		return w.getLastTaskTimeMillis();
	}
}
