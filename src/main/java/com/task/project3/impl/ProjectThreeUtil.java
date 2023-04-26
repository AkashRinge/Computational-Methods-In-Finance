package com.task.project3.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.distribution.NormalDistribution;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ProjectThreeUtil {

	private static final NormalDistribution NORMAL = new NormalDistribution();

	public static double weinerProcess(double t) {
		return NORMAL.inverseCumulativeProbability(Math.random()) * Math.sqrt(t);
	}

	public static Map<Double, Double> weinerProcess(double t, double timeStep) {
		int n = (int) (t / timeStep);
		Map<Double, Double> weiners = new HashMap<>();
		double[] dw = NORMAL.sample(n);
		for (int i = 1; i < n; i++) {
			weiners.put(timeStep * i, dw[i] * Math.sqrt(timeStep));
		}
		return weiners;
	}

	public static double getWeiner(Map<Double, Double> dw, double t) {
		Collection<Double> timeSteps = dw.keySet();
		double w = 0;
		double stepCount = 0;
		for (Double dt : timeSteps) {
			w += dw.get(dt);
			stepCount += dt;
			if (stepCount == t) {
				return w;
			}
		}
		return w;
	}
}
