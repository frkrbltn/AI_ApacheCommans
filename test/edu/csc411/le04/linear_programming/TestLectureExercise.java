package edu.csc411.le04.linear_programming;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import edu.csc411.linear_programming.example.LectureExercise;

public class TestLectureExercise {
	
	@After
	public void tearDown() {
		System.out.println("");
	}

	@Test
	public void testShoes() {
		/*
		 * Expected Value: 12416.666666666666	
		 */
		double[] results = LectureExercise.linearProgram_Shoes();
		String[] items = {"Evaluation"};
		double[] expected_results = {12416.666666666666};
		
		final double EPISILON = 1.0;
		boolean pass = true;
		for (int i = 0; i < results.length; i++) {
			double result = results[i];
			double expected = expected_results[i];
			String line = "%s - Received: %.2f\tExpected: %.2f";
			line = String.format(line, items[i],  result, expected);
			System.out.println(line);
			// Adding a buffer EPISILON in case results are slightly different
			if (!(Math.abs(result-expected) <= EPISILON)) pass = false;
		}
		assertTrue(pass);
	}
}
