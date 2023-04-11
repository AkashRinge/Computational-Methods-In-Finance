package com.task.api;

/***
 * These are plotting libraries created to help us visualize the data
 * 
 * @author food4thought
 *
 */
public interface PlotAPI {
	
	/**
	 * Frequency Dist Histogram
	 * 
	 * @param applicationTitle
	 * @param chartTitle
	 * @param data
	 * @param numBins
	 */
	 public void plotHist(String applicationTitle, String chartTitle, double[] data, int numBins);
	 
	 
	 /**
	  * General line plot 
	  * 
	  * @param chartTitle
	  * @param applicationTitle
	  * @param xData
	  * @param yData
	  */
	 public void plotLine(String chartTitle, String applicationTitle, double[] xData, double[] yData);
}
