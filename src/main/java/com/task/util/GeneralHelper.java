package com.task.util;

import java.util.stream.IntStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class GeneralHelper {

	public static double[][] transpose(double[][] matrix) {
	    return IntStream.range(0, matrix[0].length)
	        .mapToObj(j -> IntStream.range(0, matrix.length)
	            .mapToDouble(i -> matrix[i][j])
	            .toArray())
	        .toArray(double[][]::new);
	}
}
