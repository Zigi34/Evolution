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
	 * State of STARTED algorithm
	 */
	public static final String STARTED = "Start algorithm";
	/**
	 * State of ENDED algorithm
	 */
	public static final String ENDED = "End algorithm";
	/**
	 * State of FIND NEW BEST SOLUTION
	 */
	public static final String NEW_BEST_SOLUTION = "New best solution found";
	public static final String ITERATION_CHANGED = "Iteration changed";
	public static final String INITIALIZE = "Initialization";
	public static final String NO_STATE = "No state";

	/**
	 * State number
	 */
	private String state;
	/**
	 * Algorithm of changed state
	 */
	private OptimizeAlgorithm<T> algorithm;

	public OptimizeAlgorithmState(OptimizeAlgorithm<T> algorithm) {
		this(algorithm, NO_STATE);
	}

	public OptimizeAlgorithmState(OptimizeAlgorithm<T> algorithm, String state) {
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
	public OptimizeAlgorithmState<T> createState(String state,
			OptimizeAlgorithm<T> algorithm) {
		return new OptimizeAlgorithmState<T>(algorithm, state);
	}

	/**
	 * Get state number
	 * 
	 * @return
	 */
	public String getState() {
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

	@Override
	public String toString() {
		return getState();
	}
}
