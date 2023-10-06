package edu.csc411.linear_programming.example;

import java.util.ArrayList;
import java.util.Collection;
 
import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class LectureExercise {
	public static double[] linearProgram_Shoes() {
		double[] results = new double[5];
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		final int LEATHER = 1500;
		final int RUBBER = 500;
		final int CORK = 200;
		// maximize profit on producing Flats, Heels, Wedges, Sandals
		
		// In this section, create the inequalities needed to represent this linear programming problem
		LinearObjectiveFunction f = new LinearObjectiveFunction(#FILL IN YOUR REPRESENTATION#);
		
		// x1 = 2*x2 - Adding this one to represent for every 1 flat, produce 2 heels
		constraints.add(new LinearConstraint(new double[] { 1, 0, 0, 0}, 0, Relationship.EQ, new double[] { 0, 2, 0, 0}, 0));
		
		// Add Constraints for LEATHER, RUBBER, and CORK (should be 3)
		
		// Add Constraints on MINIMAL number of shoes needed to be produced (should be 4)
	
		//create and run solver
		PointValuePair solution = null;
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		try {
			solution = new SimplexSolver().optimize(f, constraintSet, GoalType.MAXIMIZE);
			if (solution != null) {
				double sol = solution.getValue();
				results[0] = sol;
			
				// We are truncating variables instead of rounding because rounding up may
				// violate a constraint, while truncation will not
				for (int i = 0; i < solution.getPoint().length; i++) {
					int variable = (int) solution.getPoint()[i];
					results[i+1] = variable;
				}
			}
		} catch (Exception e) {
			System.out.println("no solution fulfilling the constraints can be found");
			e.printStackTrace();
		}
		
		// Return the results with profit, and assignments for each show type
		return results;
	}
	
	public static void main(String[] args) {
		double[] results = LectureExercise.linearProgram_Shoes();
		String[] items = {"Profit", "Flats", "Heels", "Wedges", "Sandals"};
		System.out.println("Suggested Production:");
		for (int i = 0; i < results.length; i++) {
			double result = results[i];
			String line = "%8s - %.2f";
			line = String.format(line, items[i],  result);
			System.out.println(line);
		}
	}
}
