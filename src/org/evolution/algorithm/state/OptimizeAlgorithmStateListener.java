package org.evolution.algorithm.state;

public interface OptimizeAlgorithmStateListener<T extends OptimizeAlgorithmState> {
	public void handleStateChanged(Object soource, T eventCode);
}
