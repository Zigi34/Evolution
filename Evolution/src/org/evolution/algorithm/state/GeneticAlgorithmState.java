package org.evolution.algorithm.state;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.solution.Solution;

public class GeneticAlgorithmState<T extends Solution> extends
		OptimizeAlgorithmState<T> {

	/**
	 * State of SELECTION solutions to cross
	 */
	public static final String SELECT_START = "Select function started";
	public static final String SELECT_END = "Select function ended";

	/**
	 * State of CROSS solutions
	 */
	public static final String CROSS_START = "Cross function started";
	public static final String CROSS_END = "Cross function ended";

	/**
	 * State of MUTATE solutions
	 */
	public static final String MUTATE_START = "Mutate function started";
	public static final String MUTATE_END = "Mutate function ended";

	/**
	 * State of ELITISMUS solutions
	 */
	public static final String ELITISMUS_START = "Elitismus function started";
	public static final String ELITISMUS_END = "Elitismus function ended";

	/**
	 * State of NEW_POPULATION of solutions
	 */
	public static final String NEW_POPULATION_CREATED = "New population created";

	protected GeneticAlgorithmState(OptimizeAlgorithm<T> algorithm, String state) {
		super(algorithm, state);
	}

	protected GeneticAlgorithmState(OptimizeAlgorithm<T> algorithm) {
		super(algorithm);
	}
}
