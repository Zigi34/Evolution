package org.evolution.solution.space.restriction;

import org.evolution.solution.Solution;

public abstract class RestrictiveCondition<T extends Solution> {
	public abstract boolean isIncluded(T solution);
}
