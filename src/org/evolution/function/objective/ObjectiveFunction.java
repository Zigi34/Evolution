package org.evolution.function.objective;

import org.evolution.solution.Solution;

public abstract class ObjectiveFunction<T extends Solution> {
	public abstract Double getFunctionValue(T solution);
}
