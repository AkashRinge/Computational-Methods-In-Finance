package com.task.boot;

import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.task.config.AppConfig;
import com.task.project1.impl.ProjectOne;
import com.task.project2.impl.ProjectTwo;

public class ComputationalFinanceService {
	private static final Logger LOG = Logger.getLogger("Computational Methods in Finance Using Java");
	private static final Scanner READ = new Scanner(System.in);
	
	public static void main(String ... args) {
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
			boolean keepExecuting = true;
			while(keepExecuting) {
				LOG.info("\n\n!! Welcome to experiments with Computational Methods in Finance. Following are the list of projects !!"	//NOSONAR
				+ "\nProject 1 - Random Number Simulation Methodologies"
				+ "\nProject 2 - Monte Carlo Simulation and Numerical Integration Techniques"
				+ "\n\n|| Enter project number which you want to view || Any other key to exit: || \n ");
				try {
					int x = READ.nextInt();
					switch(x) { // NOSONAR will add other cases
						case 1:
							ctx.getBean(ProjectOne.class).run();
							break;
						case 2:
							ctx.getBean(ProjectTwo.class).run();
							break;
						default:
							keepExecuting = false;
							LOG.info(" !!Goodbye!! ");
					}
				} catch(Exception ex) {
					keepExecuting = false;
					LOG.info(" !!Goodbye!! ");
				}
			}
			System.exit(0);
		}
	}
}
