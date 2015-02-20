package org.evolution.function.elitismus;

import java.util.List;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.algorithm.population.Population;
import org.evolution.solution.Solution;

public abstract class ElitismusFunction<T extends Solution> {
	private OptimizeAlgorithm<T> algorithm;

	public abstract List<T> elitismus(Population<T> population);

	public OptimizeAlgorithm<T> getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(OptimizeAlgorithm<T> algorithm) {
		this.algorithm = algorithm;
	}
}
