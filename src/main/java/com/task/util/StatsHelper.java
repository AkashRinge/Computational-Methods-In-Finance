package com.task.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility functions
 * 
 * @author food4thought
 *
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public class StatsHelper {

	public static double mean(double[] list) {
		int n = list.length;
		double avg = 0;
		for(int i=0; i<n; i++) avg += (list[i]/n);
		return avg;
	}
	
	public static double variance(double[] list) {
		int n = list.length;
		double m = mean(list);
		double v = 0;
		for(int i=0; i<n; i++) {
			v += ((list[i] - m)/n) * (list[i] - m);
		}
		return v;
	}
}
