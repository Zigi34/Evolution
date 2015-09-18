package org.evolution.function.mutate;

import java.util.List;

import org.evolution.solution.Solution;
import org.evolution.solution.space.Space;

public abstract class MutateFunction<T extends Solution> {

	public abstract List<T> mutate(List<T> mutatedSolutions,
			Space<T> solutionSpace);
}
