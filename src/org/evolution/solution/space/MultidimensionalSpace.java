package org.evolution.solution.space;

import java.util.Random;

import org.evolution.solution.ArraySolution;

public class MultidimensionalSpace extends SolutionSpace<ArraySolution> {
	private Random random = new Random();
	private Bounds[] bounds;

	public MultidimensionalSpace() {
		this(2);
	}

	public MultidimensionalSpace(int dimension) {
		bounds = new Bounds[dimension];
	}

	public int getDimension() {
		return bounds.length;
	}

	public Bounds get(int index) {
		return bounds[index];
	}

	public void setBounds(int index, Bounds bounds) {
		this.bounds[index] = bounds;
	}

	public Double getRandomValue(int dimensionIndex) {
		return bounds[dimensionIndex].getRandomValue();
	}

	@Override
	public ArraySolution getRandomSolution() {
		ArraySolution solution = new ArraySolution(getDimension());
		for (int i = 0; i < getDimension(); i++) {
			Bounds bound = get(i);
			Double rangeValue = bound.getMaxValue() - bound.getMinValue();
			Double randomValue = random.nextDouble() * rangeValue
					+ bound.getMinValue();
			solution.setValue(i, randomValue);
		}
		return solution;
	}
}
