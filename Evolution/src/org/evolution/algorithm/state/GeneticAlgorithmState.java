package org.evolution.algorithm.state;

import org.evolution.algorithm.OptimizeAlgorithm;

public class GeneticAlgorithmState extends OptimizeAlgorithmState {

	/**
	 * State of SELECTION solutions to cross
	 */
	public static final int SELECT_START = 100;
	public static final int SELECT_END = 101;

	/**
	 * State of CROSS solutions
	 */
	public static final int CROSS_START = 102;
	public static final int CROSS_END = 103;

	/**
	 * State of MUTATE solutions
	 */
	public static final int MUTATE_START = 104;
	public static final int MUTATE_END = 105;

	/**
	 * State of ELITISMUS solutions
	 */
	public static final int ELITISMUS_START = 106;
	public static final int ELITISMUS_END = 107;

	/**
	 * State of NEW_POPULATION of solutions
	 */
	public static final int NEW_POPULATION_CREATED = 108;

	protected GeneticAlgorithmState(OptimizeAlgorithm<?> algorithm, int state) {
		super(algorithm, state);
	}

	protected GeneticAlgorithmState(OptimizeAlgorithm<?> algorithm) {
		super(algorithm);
	}
}
