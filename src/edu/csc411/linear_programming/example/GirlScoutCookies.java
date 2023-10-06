package edu.csc411.linear_programming.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
 
public class GirlScoutCookies {
	public static void main(String[] args) {
		// X1 = Trefoils, X2 = Thin Mints
		Map<String, String> variableName = new HashMap<String, String>();
		variableName.put("X1", "Trefoils");
		variableName.put("X2", "Thin Mints");
		// maximize .25*X1 + .30*X2
		LinearObjectiveFunction f = new LinearObjectiveFunction(
				// .25x1 + .3x2 = 0
				new double[] { .25, .30},
				0);
		
		// Builds an ArrayList full of constraints
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		
		// such that
		//  X1 <= 500, or Trefoils cannot exceed 500 boxes
		final int TREFOILS = 500;
		constraints.add(new LinearConstraint(
				// 1*x1 + 0*x2 <= 500
				new double[] { 1, 0}, 
				Relationship.LEQ, 
				TREFOILS));
		
		//  X2 <= 500, or Thin Mints cannot exceed 500 boxes
		final int THINMINTS = 500;
		constraints.add(new LinearConstraint(new double[] { 0, 1}, Relationship.LEQ, THINMINTS));
		
		//  X1 + X2 <= 800, or the minivan cannot hold more than 800 boxes
		final int CAPACITY = 800;
		constraints.add(new LinearConstraint(
				// 1*x1 + 1*x2 <= 800
				new double[] { 1, 1}, 
				Relationship.LEQ, 
				CAPACITY));
	
		// Create and Run Solver
		// Set solution to null in case there is no solution
		PointValuePair solution = null;
		// Add the constraints to a Set
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		try {
			// Apache Commons has access to a Linear Programming Simplex Solver
			// that expects 4 parameters
			// - the object function (f)
			// - the constraints to the problem (constraintSet)
			// - our goal, either to maximize or minimize f
			// - whether we accept values to be negative; in our case, no, boxes shouldn't be negative
			solution = new SimplexSolver().optimize(f, constraintSet, GoalType.MAXIMIZE, 
								                    // No Negative Variables 
								                    new NonNegativeConstraint(true));
		} catch (Exception e) {
			// If no solution exists, then simply print that out
			System.out.println("no solution fulfilling the constraints can be found");
		}
	
		if (solution != null) {
			// Get object functions max/min value and print it
			double sol = solution.getValue();
			System.out.printf("Optimal Profit: $%.2f\n", sol);
		
			// Extract and print the Decision Variables (the value for each X)
			for (int i = 0; i < solution.getPoint().length; i++) {
				// Can't sell half a box
				int variable = (int)solution.getPoint()[i];
				String productName = String.format("X%d", i+1);
				String line = "%s = %d (%s)\n"; 
				System.out.printf(line, productName, variable, variableName.get(productName));
			}
		}
	}
}

