package org.evolution.function.mutate;

import java.util.List;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;

public abstract class MutateFunction<T extends Solution> {
	private OptimizeAlgorithm<T> algorithm;

	public abstract List<T> mutate(List<T> mutatedSolutions,
			SolutionSpace<T> solutionSpace);

	public OptimizeAlgorithm<T> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(OptimizeAlgorithm<T> algorithm) {
		this.algorithm = algorithm;
	}
}
