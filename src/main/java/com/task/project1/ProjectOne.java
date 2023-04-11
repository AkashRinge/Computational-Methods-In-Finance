package com.task.project1;

import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.task.api.LgmRandomGeneratorAPI;
import com.task.api.PlotAPI;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProjectOne {
	
	private static final String CHART_TITLE = "Freq of n VS value of n";
	private static final Logger LOG = Logger.getLogger("Project 1: Random Number Generation");
	private static final Scanner READER = new Scanner(System.in);
	private static final Integer SEED = 42;
	
	private LgmRandomGeneratorAPI generator;
	private PlotAPI plot;
	
	public void run() {
		int x = 1;
		while (x > 0 && x < 4) {
			LOG.info("\n\n1 - Generate a Uniform distribution of 10000 numbers between [0,1] using LGM algo" 			//NOSONAR
				+ "\n2 - Generate a Uniform distribution of 10000 numbers using inbuilt algo"
				+ "\n3 - Compare distributions generated in 1 and 2"
				+ "\nAny other number to exit\n"); 
			
			x = READER.nextInt();
		
			switch(x) {
				case 1:
					double[] numbers = generator.uniformDist(10000, SEED);
					plot.plotHist("LGM Distribution Histogram", CHART_TITLE, numbers, 100);
					break;
				case 2:
					plot.plotHist("Software Generated Histogram", CHART_TITLE, uniformDist(10000, SEED), 100);
					break;
				case 3:
					double[] range = new double[10000];
					for(int i=1; i<=10000; i++) {
						range[i-1] = i;
					}
					plot.plotLine("LGM Distribution Plot", CHART_TITLE, range, generator.uniformDist(10000, SEED));
					plot.plotLine("Software Generated Plot", CHART_TITLE, range, uniformDist(10000, SEED));
					LOG.info("As we can see the plots follow exactly similar trend to each other");
					break;
				default:
					LOG.info("Goodbye!");
					System.exit(0);
			}
		}	
	}
	
	private double[] uniformDist(int n, int seed) {
		Random random = new Random(seed);
		double[] rand = new double[n];
		for(int i=0; i<n; i++) {
			rand[i] = random.nextDouble();
		}
		return rand;
	}
}
