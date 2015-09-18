package org.evolution.function.objective;

import org.evolution.solution.NumericSolution;
import org.evolution.solution.space.Space;

public class DeJong1 extends ObjectiveFunction<NumericSolution> {

	private Space<NumericSolution> space;

	@Override
	public Double calculateFunctionValue(NumericSolution solution) {
		double sum = 0.0;
		for (int i = 0; i < solution.size(); i++) {
			sum += Math.abs(Math.pow(solution.get(i).doubleValue(),
					solution.size()));
		}
		return sum;
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
