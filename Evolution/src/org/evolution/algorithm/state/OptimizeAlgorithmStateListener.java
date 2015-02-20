package org.evolution.algorithm.state;

import org.evolution.solution.Solution;

public interface OptimizeAlgorithmStateListener<T extends Solution> {
	public void handleStateChanged(OptimizeAlgorithmState<T> eventCode);
}
