package org.evolution.function.select;

import java.util.List;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.solution.Solution;

public abstract class SelectFunction<T extends Solution> {

	private OptimizeAlgorithm<T> algorithm;

	public abstract List<T> select(List<T> population);

	public abstract List<T> select(List<T> population, int count);

	public OptimizeAlgorithm<T> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(OptimizeAlgorithm<T> algorithm) {
		this.algorithm = algorithm;
	}
}
