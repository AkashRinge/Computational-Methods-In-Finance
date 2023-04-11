package com.task.config;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.task.api.LgmRandomGeneratorAPI;
import com.task.api.PlotAPI;
import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.HistogramGeneratorImpl;
import com.task.plot.impl.LineChartGeneratorImpl;
import com.task.plot.impl.PlotAPIImpl;
import com.task.plot.impl.context.HistContext;
import com.task.plot.impl.context.LineContext;
import com.task.project1.impl.LgmRandomGeneratorImpl;

@Configuration
@ComponentScan("com.task")
public class AppConfig {

	@Bean
	public PlotAPI plotAPI() {
		return new PlotAPIImpl(hist(), line());
	}
	
	@Bean 
	public PlotGeneratorAPI<LineContext> line() {
		return new LineChartGeneratorImpl();
	}
	
	@Bean 
	public PlotGeneratorAPI<HistContext> hist() {
		return new HistogramGeneratorImpl();
	}
	
	@Bean
	public LgmRandomGeneratorAPI lgmRandomGeneratorAPI() {
		return new LgmRandomGeneratorImpl();
	}
	
	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
}
