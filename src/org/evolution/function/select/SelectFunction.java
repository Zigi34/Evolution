package org.evolution.function.select;

import java.util.List;

import org.evolution.algorithm.population.Population;
import org.evolution.solution.Solution;

public interface SelectFunction<T extends Solution> {
	/**
	 * Select solutions from population, which is selected with objective
	 * function and best are selected
	 * 
	 * @param probability
	 * @param population
	 *            solutions in population
	 * @return selected solutions
	 */
	List<T> select(double probability, Population population);

	List<T> select(int count, Population population);
}
