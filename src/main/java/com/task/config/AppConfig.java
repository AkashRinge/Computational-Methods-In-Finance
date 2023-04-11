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
import com.task.plot.impl.PlotAPIImpl;
import com.task.plot.impl.context.HistContext;
import com.task.plot.impl.context.LineContext;
import com.task.project1.ProjectOne;
import com.task.project1.impl.LgmRandomGeneratorImpl;

@Configuration
@ComponentScan(basePackages = "com.task")
public class AppConfig {

	@Bean
	public PlotAPI plotAPI() {
		return new PlotAPIImpl(hist(), line());
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
	public LgmRandomGeneratorAPI lgmRandomGeneratorAPI() {
		return new LgmRandomGeneratorImpl();
	}
	
	@Bean
	public ProjectOne projectOne() {
		return new ProjectOne(lgmRandomGeneratorAPI(), plotAPI());
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
