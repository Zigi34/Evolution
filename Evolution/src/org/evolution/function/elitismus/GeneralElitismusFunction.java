package org.evolution.function.elitismus;

import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.population.Population;
import org.evolution.solution.Solution;

public class GeneralElitismusFunction<T extends Solution> extends
		ElitismusFunction<T> {

	@Override
	public List<T> elitismus(Population<T> population) {
		List<T> result = new LinkedList<T>();

		int i = 0;
		int size = getAlgorithm().getPopulation().size();
		while (result.size() < size) {
			result.add(population.get(i++));
		}
		return result;
	}
}
