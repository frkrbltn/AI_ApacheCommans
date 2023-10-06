package edu.csc411.linear_programming.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.optim.*;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
 
public class NCSUScooters {
	public static void main(String[] args) {
		// X1 = Normal Scooters, X2 = Racing Scooters
		Map<String, String> variableName = new HashMap<String, String>();
		variableName.put("X1", "Normal Scooters");
		variableName.put("X2", "Racing Scooters");
		
		// maximize 600*X1 + 800*X2 
		LinearObjectiveFunction f = new LinearObjectiveFunction(new double[] { 600, 800}, 0);
		
		// Builds an ArrayList full of constraints
		Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		
		// such that
		//  MOTORS -> X1 + X2 <= 75, or that we can only use up to 75 motors
		final int MOTORS = 75;
		constraints.add(new LinearConstraint(new double[] { 1, 1}, Relationship.LEQ, MOTORS));
		
		//  LABOR HOURS -> 20*X1 + 30*X2 <= 2000, or the total labor should be <= 2000 hours
		final int LABORHOURS = 2000;
		constraints.add(new LinearConstraint(new double[] { 20, 30}, Relationship.LEQ, LABORHOURS));
		
		//  METAL -> 20*X1 + 16*X2 <= 3000, or the total sqft of sheet metal should be <= 3000
		final int METAL = 3000;
		constraints.add(new LinearConstraint(new double[] { 20, 16}, Relationship.LEQ, METAL));
	
		// Create and Run Solver
		PointValuePair solution = null;
		LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
		try {
			solution = new SimplexSolver().optimize(f, constraintSet, 
								                    GoalType.MAXIMIZE, 
								                    // No Negative Variables 
								                    new NonNegativeConstraint(true));
		} catch (Exception e) {
			// If no solution exists, then simply print that out
			System.out.println("no solution fulfilling the constraints can be found");
		}
	
		if (solution != null) {
			// Converts doubles into integer values; in case that matters
			// Get object functions max/min value and print it
			double sol = solution.getValue();
			System.out.printf("Optimal Profit: $%.2f\n", sol);
		
			// Extract and print the Decision Variables (the value for each X)
			for (int i = 0; i < solution.getPoint().length; i++) {
				// Can't sell an incomplete scooter
				int variable = (int)solution.getPoint()[i];
				String productName = String.format("X%d", i+1);
				String line = "%s = %d (%s)\n"; 
				System.out.printf(line, productName, variable, variableName.get(productName));
			}
		}
	}
}

