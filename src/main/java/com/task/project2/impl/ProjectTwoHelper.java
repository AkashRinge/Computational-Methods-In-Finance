package com.task.project2.impl;

import com.task.util.GeneralHelper;
import com.task.util.StatsHelper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ProjectTwoHelper {
	
	private static final ProjectTwoHelper SINGLETON = new ProjectTwoHelper();
	
	static ProjectTwoHelper instance() {
		return SINGLETON;
	}
	
	double bivariateCorr(double[][] bivariateNormDist, int n) {
		double[][] bivariateNormDistT = GeneralHelper.transpose(bivariateNormDist);
		
		double cov = StatsHelper.covariance(bivariateNormDistT[0], bivariateNormDistT[1], n);
		double sigma1 = StatsHelper.variance(bivariateNormDistT[0]);
		double sigma2 = StatsHelper.variance(bivariateNormDistT[1]);
		
		return cov / (sigma1 * sigma2);
	}
	
	double customMonteCarlo(double x, double y) {
		return Math.max(0, Math.pow(y, 3) + (Math.sin(y)) + (x*x*y));
	}
	
}
