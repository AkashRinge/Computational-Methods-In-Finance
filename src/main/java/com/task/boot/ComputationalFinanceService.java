package com.task.boot;

import java.util.Scanner;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.task.config.AppConfig;
import com.task.project1.ProjectOne;

public class ComputationalFinanceService {
	private static final Logger LOG = Logger.getLogger("Computational Methods in Finance Using Java");
	private static final Scanner sc = new Scanner(System.in);
	
	public static void main(String ... args) {
		
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
		
			LOG.info("\n\n!! Welcome to experiments with Computational Methods in Finance. Following are the list of projects !!"	//NOSONAR
			+ "\n1 - Random number generation"
			+ "\nEnter project number which you want to view: \n ");
			int x = sc.nextInt();
			
			switch(x) { // NOSONAR will add other cases
				case 1:
					ctx.getBean(ProjectOne.class).run();
					break;
				default:
					LOG.severe("\n\n Input was not recognized! Try again with a valid input next-time! Goodbye!");
					System.exit(0);
			}
		}
		
	}
}
