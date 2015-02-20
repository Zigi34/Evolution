package org.evolution.function.objective;

import org.evolution.solution.ArraySolution;

public class TestFunction extends ObjectiveFunction<ArraySolution> {

	@Override
	public Double calculateFunctionValue(ArraySolution solution) {
		Double sum = 0.0;
		int dimension = solution.getSize();
		for (int i = 0; i < dimension; i++) {
			sum += Math.pow(solution.getValue(i), 2.0) - 10
					* Math.cos(2 * Math.PI * solution.getValue(i));
		}
		return 2 * dimension * sum;
	}

}
