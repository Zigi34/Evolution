package org.evolution.algorithm.exception;

import org.evolution.algorithm.state.OptimizeAlgorithmState;

public class AlgorithmException extends Exception {

	private static final long serialVersionUID = 7867611706726704845L;
	private OptimizeAlgorithmState<?> state;

	public AlgorithmException(String message, OptimizeAlgorithmState<?> state) {
		super(message);
		this.state = state;
	}

	public OptimizeAlgorithmState<?> getState() {
		return state;
	}

	public String toString() {
		if (state != null && state.getAlgorithm() != null)
			return String.format("[ALG:\"%s\"][STATE:\"%s\"]: %s",
					state.getAlgorithm(), state, getMessage());
		if (state != null)
			return String.format("[STATE:\"%s\"]: %s", state, getMessage());
		if (state.getAlgorithm() != null)
			return String.format("[ALG:\"%s\"]: %s", state.getAlgorithm(),
					getMessage());

		return String.format("%s", getMessage());
	};
}