package com.task.project1.impl;

import java.util.Random;

import com.task.api.LgmRandomGeneratorAPI;

public class LgmRandomGeneratorImpl implements LgmRandomGeneratorAPI {

	public double[] uniformDist(int n, int seed) {
		double a = Math.pow(7, 5);
		double m = Math.pow(2, 31) - 1;
		Random random = new Random(seed);
		double[] rand = new double[n];
		rand[0] = Math.abs(random.nextLong() % m);
		double max = rand[0];
				
		for (int i=1; i<n; i++) {
			rand[i] = Math.abs((rand[i-1] * a) % m);
			max = Math.max(rand[i], max);
		}
		
		for (int i=0; i<n; i++) {
			rand[i] = rand[i] / max;
		}
		return rand;
	}
	
}
