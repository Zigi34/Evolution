package org.evolution.function.select;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.solution.ArraySolution;

public class ArrayRouletteWheelSelect extends SelectFunction<ArraySolution> {

	private Random random = new Random();
	public static final double maxSelection = 1.0;
	public static final double minSelection = 0.5;
	private double selectProbability = 1.0;

	@Override
	public List<ArraySolution> select(List<ArraySolution> population) {
		int selectCount = (int) Math
				.ceil(population.size() * selectProbability);
		List<ArraySolution> result = new LinkedList<ArraySolution>();
		List<Double> cumulativeFitnesses = new ArrayList<Double>(selectCount);
		double sumCumulativeFitness = 0.0;

		for (ArraySolution solution : population) {
			Double functionValue = solution.getFunctionValue();
			sumCumulativeFitness += getAlgorithm().isMinimize() ? (1000.0 / functionValue)
					: functionValue;
			cumulativeFitnesses.add(sumCumulativeFitness);
		}

		for (int solutionCount = 0; solutionCount < selectCount; solutionCount++) {
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

	public double getSelectProbability() {
		return selectProbability;
	}

	public void setSelectProbability(double selectProbability) {
		this.selectProbability = selectProbability;
	}
}
