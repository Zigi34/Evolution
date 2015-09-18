package org.evolution.function.objective;

import org.evolution.solution.NumericSolution;
import org.evolution.solution.space.Space;

public class TestFunction extends ObjectiveFunction<NumericSolution> {

	private Space<NumericSolution> space;

	@Override
	public Double calculateFunctionValue(NumericSolution solution) {
		Double sum = 0.0;
		int dimension = solution.size();
		for (int i = 0; i < dimension; i++) {
			sum += Math.pow(solution.get(i).doubleValue(), 2.0) - 10
					* Math.cos(2 * Math.PI * solution.get(i).doubleValue());
		}
		return 2 * dimension * sum;
	}

	@Override
	public void setSolutionSpace(Space<NumericSolution> solutionSpace) {
		this.space = solutionSpace;
	}

	@Override
	public Space<NumericSolution> getSolutionSpace() {
		return space;
	}

}
