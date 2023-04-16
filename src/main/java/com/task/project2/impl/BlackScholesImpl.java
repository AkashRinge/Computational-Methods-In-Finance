package com.task.project2.impl;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.stereotype.Component;

import com.task.api.BlackScholesAPI;

@Component
public class BlackScholesImpl implements BlackScholesAPI {
	
    public double calculateCallOptionValue(double s, double k, double r, double t, double sigma) {
        NormalDistribution norm = new NormalDistribution();
        double d1 = (Math.log(s/k) + (r + (sigma*sigma)/2)*t) / (sigma*Math.sqrt(t));
        double d2 = d1 - sigma*Math.sqrt(t);
        return s*norm.cumulativeProbability(d1) - k*Math.exp(-r*t)*norm.cumulativeProbability(d2);
    }
}
