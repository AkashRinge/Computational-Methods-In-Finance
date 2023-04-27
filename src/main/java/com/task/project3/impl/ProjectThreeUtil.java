package com.task.project3.impl;

import org.apache.commons.math3.distribution.NormalDistribution;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ProjectThreeUtil {

	private static final NormalDistribution NORMAL = new NormalDistribution();

	public static double weinerProcess(double t) {
		return NORMAL.sample() * Math.sqrt(t);
	}

	public static double[] weinerProcess(double t, double timeStep) {
		int n = (int) (t / timeStep);
		double[] dw = NORMAL.sample(n);
		for (int i = 0; i < n; i++) {
			dw[i] = dw[i] * Math.sqrt(timeStep);
		}
		return dw;
	}

	public static double getWeiner(double[] dw, double T, double timeStep) {
		double w = 0;
		double t = 0;
		for(int i=0; i<dw.length; i++) {
			w += dw[i];
			t += timeStep;
			if (t>=T) {
				return w;
			}
		}
		return w;
	}
}
