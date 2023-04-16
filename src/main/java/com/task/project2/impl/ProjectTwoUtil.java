package com.task.project2.impl;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.math3.distribution.NormalDistribution;

import com.task.util.GeneralHelper;
import com.task.util.StatsHelper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.NONE)
public class ProjectTwoUtil {
	
	static final NormalDistribution STANDARD_NORMAL = new NormalDistribution(0, 1);
	
	static double bivariateCorr(double[][] bivariateNormDist, int n) {
		double[][] bivariateNormDistT = GeneralHelper.transpose(bivariateNormDist);
		
		double cov = StatsHelper.covariance(bivariateNormDistT[0], bivariateNormDistT[1], n);
		double sigma1 = StatsHelper.variance(bivariateNormDistT[0]);
		double sigma2 = StatsHelper.variance(bivariateNormDistT[1]);
		
		return cov / (sigma1 * sigma2);
	}
	
	static double stdWeiner(double t) {
		return STANDARD_NORMAL.sample() * Math.sqrt(t);
	}
}
