package com.task.api;

import java.util.List;

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

	 /**
	  * Multiline plot
	  * 
	  * @param chartTitle
	  * @param applicationTitle
	  * @param data
	  * @param seriesNames
	  * @param xAxisLabel
	  * @param yAxisLabel
	  */
	 public void plotMultiLine(String chartTitle, String applicationTitle, List<double[]> data, List<String> seriesNames, String xAxisLabel, String yAxisLabel);
	 
	 /**
	  * Scatter plot
	  * 
	  * @param chartTitle
	  * @param applicationTitle
	  * @param xData
	  * @param yData
	  */
	 public void scatter(String chartTitle, String applicationTitle, double[] xData, double[] yData);
}
