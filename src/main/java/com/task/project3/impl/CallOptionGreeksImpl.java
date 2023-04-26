package com.task.project3.impl;

import org.springframework.stereotype.Component;

import com.task.api.BlackScholesAPI;
import com.task.api.CallOptionGreeksAPI;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CallOptionGreeksImpl implements CallOptionGreeksAPI { 
	
	private BlackScholesAPI bsAPI;

	@Override
    public double getDelta(double S, double X, double r, double sigma, double T, double dt) {
        double S_plus = S * (1 + dt);
        double S_minus = S * (1 - dt);
        double C_plus = getCallPrice(S_plus, X, r, sigma, T);
        double C_minus = getCallPrice(S_minus, X, r, sigma, T);
        return (C_plus - C_minus) / (2 * S * dt);
    }

	@Override
    public double getGamma(double S, double X, double r, double sigma, double T, double dt) {
        double S_plus = S * (1 + dt);
        double S_minus = S * (1 - dt);
        double C = getCallPrice(S, X, r, sigma, T);
        double C_plus = getCallPrice(S_plus, X, r, sigma, T);
        double C_minus = getCallPrice(S_minus, X, r, sigma, T);
        return (C_plus - 2 * C + C_minus) / (S * S * dt * dt);
    }

	@Override
    public double getTheta(double S, double X, double r, double sigma, double T, double dt) {
        double C1 = getCallPrice(S, X, r, sigma, T);
        double C2 = getCallPrice(S, X, r, sigma, T - dt);
        return (C2 - C1) / dt;
    }

	@Override
    public double getVega(double S, double X, double r, double sigma, double T, double dt) {
        double sigma_plus = sigma * (1 + dt);
        double sigma_minus = sigma * (1 - dt);
        double C_plus = getCallPrice(S, X, r, sigma_plus, T);
        double C_minus = getCallPrice(S, X, r, sigma_minus, T);
        return (C_plus - C_minus) / (2 * sigma * dt);
    }
    
    private double getCallPrice(double S, double X, double r, double sigma, double T) {
    	return bsAPI.calculateCallOptionValue(S, X, r, T, sigma);
    }
}
