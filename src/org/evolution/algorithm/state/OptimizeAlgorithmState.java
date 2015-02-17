package org.evolution.algorithm.state;

import org.evolution.algorithm.OptimizeAlgorithm;

public class OptimizeAlgorithmState {

	public static final int STARTED = 1;
	public static final int ENDED = 2;
	public static final int NEW_BEST_SOLUTION = 3;

	private int state;
	private OptimizeAlgorithm<?> algorithm;

	protected OptimizeAlgorithmState(OptimizeAlgorithm<?> algorithm) {
		this(algorithm, 0);
	}

	protected OptimizeAlgorithmState(OptimizeAlgorithm<?> algorithm, int state) {
		this.algorithm = algorithm;
		this.state = state;
	}

	public static OptimizeAlgorithmState createState(int state,
			OptimizeAlgorithm<?> algorithm) {
		return new OptimizeAlgorithmState(algorithm, state);
	}

}
