package org.evolution.function.mutate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;

public class GeneticMuateFunction<T extends Solution> extends MutateFunction<T> {

	public static final double minMutate = 0.0;
	public static final double maxMutate = 0.8;
	private double mutatedProbability = 0.01;
	private static Random random = new Random();

	@Override
	public List<T> mutate(List<T> mutatedSolutions,
			SolutionSpace<T> solutionSpace) {
		List<T> result = new LinkedList<T>();
		for (int i = 0; i < mutatedSolutions.size(); i++) {
			T solution = mutatedSolutions.get(i);
			T randomSolution = getAlgorithm().getSolutionSpace()
					.getRandomSolution();
			for (int parameterIndex = 0; parameterIndex < solution.getSize(); parameterIndex++) {
				if (random.nextDouble() <= mutatedProbability) {
					solution.setValue(randomSolution.getValue(parameterIndex),
							parameterIndex);
				}
			}
		}
		return result;
	}

	public double getMutatedProbability() {
		return mutatedProbability;
	}

	public void setMutatedProbability(double mutatedProbability) {
		this.mutatedProbability = mutatedProbability;
	}
}
