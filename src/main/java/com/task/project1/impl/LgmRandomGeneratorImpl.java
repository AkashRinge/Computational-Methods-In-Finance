package com.task.project1.impl;

import java.util.Random;

import org.apache.commons.math3.random.AbstractRandomGenerator;
import org.springframework.stereotype.Component;

import com.task.api.LgmRandomGeneratorAPI;

@Component
public class LgmRandomGeneratorImpl extends AbstractRandomGenerator implements LgmRandomGeneratorAPI {

	private Random random;
	
	public double[] uniformDist(int n, int seed) {
		double a = Math.pow(7, 5);
		double m = Math.pow(2, 31) - 1;
		random = new Random(seed);
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

	@Override
	public void setSeed(long seed) {
		random = new Random(seed);
	}

	@Override
	public double nextDouble() {
		return random.nextDouble();
	}
	
}
