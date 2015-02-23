package org.evolution.solution.space.restriction;

import org.evolution.model.ConfigurationModel;
import org.evolution.solution.Solution;

public abstract class RestrictiveCondition<T extends Solution> implements
		ConfigurationModel {
	public abstract boolean isIncluded(T solution);
}
