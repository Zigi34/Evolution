package org.evolution.solution.space.restriction;

import org.evolution.solution.ArraySolution;

public class BasicRestrictiveFunction extends
		RestrictiveCondition<ArraySolution> {

	@Override
	public boolean isIncluded(ArraySolution solution) {
		for (int i = 0; i < solution.size(); i++)
			if (solution.get(i) < 0)
				return true;
		return false;
	}

}
