package com.task.boot;

import java.util.Scanner;
import java.util.logging.Logger;

import com.task.config.Factory;

public class BootLoader {
	private static Logger LOG = Logger.getLogger("Computational Methods in Finance Using Java");
	
	public static void main(String ... args) {
		Factory.initialize();
		Scanner sc = Factory.scanner();
		
		LOG.info("!! Welcome to experiments with Computational Methods in Finance. Following are the list of projects !!");
		LOG.info("1 - Random number generation");
		LOG.info("Enter project number which you want to view: ");
		int x = sc.nextInt();
		
		switch(x) {
			case 1:
				break;
			default:
				LOG.severe("Input was not recognized! Try again with a valid input next-time! Goodbye!");
				System.exit(0);
		}
		
	}
}
