package com.task.project1.impl;

import java.util.Random;

import com.task.api.LgmRandomGeneratorAPI;

public class LgmRandomGeneratorImpl implements LgmRandomGeneratorAPI {

	public double[] uniformDist(int n, int seed) {
		double a = Math.pow(7, 5);
		double m = Math.pow(2, 31) - 1;
		Random random = new Random(seed);
		double[] rand = new double[n];
		rand[0] = random.nextLong();
				
		for (int i=0; i<n; i++) {
			if (i > 0) {
				rand[i] = (rand[i-1] * a) % m; 
			}
			rand[i] = rand[i] / Math.pow(2, 31);
		}
		
		return rand;
	}
	
}
