package org.evolution.function.objective;

import org.evolution.solution.ArraySolution;

public class TestFunction extends ObjectiveFunction<ArraySolution> {

	@Override
	public Double calculateFunctionValue(ArraySolution solution) {
		Double sum = 0.0;
		int dimension = solution.size();
		for (int i = 0; i < dimension; i++) {
			sum += Math.pow(solution.get(i), 2.0) - 10
					* Math.cos(2 * Math.PI * solution.get(i));
		}
		return 2 * dimension * sum;
	}

}
