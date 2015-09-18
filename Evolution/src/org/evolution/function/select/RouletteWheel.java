package org.evolution.function.select;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.solution.Solution;

public class RouletteWheel<T extends Solution> extends SelectFunction<T> {

	private Random random = new Random();
	public static final double maxSelection = 1.0;
	public static final double minSelection = 0.5;

	@Override
	public List<T> select(List<T> population, int count) {
		List<T> result = new LinkedList<T>();
		List<Double> cumulativeFitnesses = new ArrayList<Double>(count);
		double sumCumulativeFitness = 0.0;

		for (T solution : population) {
			Double functionValue = solution.getFunctionValue();
			sumCumulativeFitness += getAlgorithm().isMinimize() ? (1000.0 / functionValue)
					: functionValue;
			cumulativeFitnesses.add(sumCumulativeFitness);
		}

		for (int solutionCount = 0; solutionCount < count; solutionCount++) {
			double selectedValue = random.nextDouble() * sumCumulativeFitness;

			for (int i = 0; i < cumulativeFitnesses.size(); i++) {
				Double value = cumulativeFitnesses.get(i);
				if (selectedValue <= value) {
					result.add(population.get(i));
					break;
				}
			}
		}
		return result;
	}

	@Override
	public List<T> select(List<T> population) {
		return select(population, population.size());
	}
}
