package org.evolution.algorithm.state;

import org.evolution.algorithm.OptimizeAlgorithm;

public class OptimizeAlgorithmState {

	public static final int STARTED = 1;
	public static final int ENDED = 2;
	public static final int NEW_BEST_SOLUTION = 3;
	public static final int ITERATION_CHANGED = 4;
	public static final int INITIALIZE = 5;

	private int state;
	private OptimizeAlgorithm<?> algorithm;

	protected OptimizeAlgorithmState(OptimizeAlgorithm<?> algorithm) {
		this(algorithm, 0);
	}

	protected OptimizeAlgorithmState(OptimizeAlgorithm<?> algorithm, int state) {
		this.algorithm = algorithm;
		this.state = state;
	}

	/**
	 * Create new optimize algorithm state
	 * 
	 * @param state
	 *            state number
	 * @param algorithm
	 *            algorithm with chaned state
	 * @return
	 */
	public static OptimizeAlgorithmState createState(int state,
			OptimizeAlgorithm<?> algorithm) {
		return new OptimizeAlgorithmState(algorithm, state);
	}

	/**
	 * Get state number
	 * 
	 * @return
	 */
	public int getState() {
		return state;
	}

	/**
	 * Get algorithm of this state
	 * 
	 * @return
	 */
	public OptimizeAlgorithm<?> getAlgorithm() {
		return algorithm;
	}
}
