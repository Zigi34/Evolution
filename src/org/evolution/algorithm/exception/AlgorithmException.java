package org.evolution.algorithm.exception;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.algorithm.state.OptimizeAlgorithmState;

public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 7867611706726704845L;
	private OptimizeAlgorithm<?> algorithm;
	private OptimizeAlgorithmState state;

	public AlgorithmException(String name, OptimizeAlgorithm<?> source,
			OptimizeAlgorithmState state) {
		super(name);
		this.algorithm = source;
		this.state = state;
	}

	public OptimizeAlgorithm<?> getAlgorithm() {
		return algorithm;
	}

	public OptimizeAlgorithmState getState() {
		return state;
	}
}