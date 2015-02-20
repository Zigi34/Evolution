package org.evolution.function.objective;

import org.evolution.solution.Solution;

public abstract class ObjectiveFunction<T extends Solution> {
	/**
	 * Return calculated function value if it is not calculated yet, otherwise
	 * direct return value.
	 * 
	 * @param solution
	 *            solution of function value
	 * @return real value represent function value of this solution
	 */
	public Double getFunctionValue(T solution) {
		if (!solution.isEvaluated()) {
			Double value = calculateFunctionValue(solution);
			solution.setFunctionValue(value);
		}
		return solution.getFunctionValue();
	}

	/**
	 * Calculate actual selected solution and return function value
	 * 
	 * @param solution
	 *            selected solution for calculate
	 * @return
	 */
	public abstract Double calculateFunctionValue(T solution);
}
