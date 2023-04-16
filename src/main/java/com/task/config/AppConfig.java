package com.task.config;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.task.api.LgmRandomGeneratorAPI;
import com.task.api.PlotAPI;
import com.task.api.PlotGeneratorAPI;
import com.task.plot.impl.HistogramGeneratorImpl;
import com.task.plot.impl.LineChartGeneratorImpl;
import com.task.plot.impl.MultiLineChartGeneratorImpl;
import com.task.plot.impl.PlotAPIImpl;
import com.task.plot.impl.domain.HistContext;
import com.task.plot.impl.domain.LineContext;
import com.task.plot.impl.domain.MultiLineContext;
import com.task.project1.impl.LgmRandomGeneratorImpl;
import com.task.project1.impl.ProjectOne;
import com.task.project2.impl.ProjectTwo;

/**
 * This file is used to configure all the primary resources used by the application
 * 
 * @author food4thought
 *
 */
@Configuration
@ComponentScan(basePackages = "com.task")
public class AppConfig {

	@Bean
	public PlotAPI plotAPI() {
		return new PlotAPIImpl(hist(), line(), multiLine());
	}
	
	@Bean 
	public PlotGeneratorAPI<LineContext> line() {
		return new LineChartGeneratorImpl(jFrame());
	}
	
	@Bean 
	public PlotGeneratorAPI<HistContext> hist() {
		return new HistogramGeneratorImpl(jFrame());
	}
	
	@Bean 
	public PlotGeneratorAPI<MultiLineContext> multiLine() {
		return new MultiLineChartGeneratorImpl();
	}
	
	@Bean
	public LgmRandomGeneratorAPI lgmRandomGeneratorAPI() {
		return new LgmRandomGeneratorImpl();
	}
	
	@Bean
	public ProjectOne projectOne() {
		return new ProjectOne(lgmRandomGeneratorAPI(), plotAPI());
	}
	
	@Bean
	public ProjectTwo projectTwo() {
		return new ProjectTwo(plotAPI());
	}
	
	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}
	
	@Bean
	public JFrame jFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame.setSize(1000, 700);
        return frame;
	}
}
