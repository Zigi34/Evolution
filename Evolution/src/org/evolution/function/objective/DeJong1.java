package org.evolution.function.objective;

import org.evolution.solution.ArraySolution;

public class DeJong1<T extends ArraySolution> extends ObjectiveFunction<T> {

	@Override
	public Double calculateFunctionValue(T solution) {
		double sum = 0.0;
		for (int i = 0; i < solution.size(); i++) {
			sum += Math.abs(Math.pow(solution.get(i).doubleValue(),
					solution.size()));
		}
		return sum;
	}
}
