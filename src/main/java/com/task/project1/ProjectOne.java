package com.task.project1;

import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.task.api.LgmRandomGeneratorAPI;
import com.task.api.PlotAPI;
import com.task.config.Factory;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
public class ProjectOne {
	
	private static final Logger LOG = Logger.getLogger("Project 1: Random Number Generation");
	private static final Scanner READER = new Scanner(System.in);
	
	private LgmRandomGeneratorAPI generator;
	private PlotAPI plot;
	
	public void run() {

		LOG.info("1 - Generate a Uniform distribution of 10000 numbers between [0,1] using LGM algo");
		LOG.info("2 - Generate a Uniform distribution of 10000 numbers using inbuilt algo");
		LOG.info("3 - Compare distributions generated in 1 and 2");
		LOG.info("Any other number to exit");
		
		switch(READER.nextInt()) {
			case 1:
				plot.
			case 2:
				
			case 3:
				
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
