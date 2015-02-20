package org.evolution.algorithm.state;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.solution.Solution;

/**
 * States for general algorithm instance
 * 
 * @author Zdenek Gold
 *
 */
public class OptimizeAlgorithmState<T extends Solution> {

	/**
	 * State of STARTED algorithm (value = 1)
	 */
	public static final int STARTED = 1;
	/**
	 * State of ENDED algorithm (value = 2)
	 */
	public static final int ENDED = 2;
	/**
	 * State of FIND NEW BEST SOLUTION (value = 3)
	 */
	public static final int NEW_BEST_SOLUTION = 3;
	public static final int ITERATION_CHANGED = 4;
	public static final int INITIALIZE = 5;

	/**
	 * State number
	 */
	private int state;
	/**
	 * Algorithm of changed state
	 */
	private OptimizeAlgorithm<T> algorithm;

	public OptimizeAlgorithmState(OptimizeAlgorithm<T> algorithm) {
		this(algorithm, 0);
	}

	public OptimizeAlgorithmState(OptimizeAlgorithm<T> algorithm, int state) {
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
	public OptimizeAlgorithmState<T> createState(int state,
			OptimizeAlgorithm<T> algorithm) {
		return new OptimizeAlgorithmState<T>(algorithm, state);
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
	public OptimizeAlgorithm<T> getAlgorithm() {
		return algorithm;
	}
}
