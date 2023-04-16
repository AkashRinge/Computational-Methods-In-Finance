package com.task.project2.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class ProjectTwoSolver {

	private static final ProjectTwoSolver SINGLETON = new ProjectTwoSolver();
	
	static ProjectTwoSolver instance() {
		return SINGLETON;
	}
	
	
}
