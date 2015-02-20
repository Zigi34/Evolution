package org.evolution.function.cross;

import java.util.List;

import org.evolution.solution.Solution;

public abstract class CrossFunction<T extends Solution> {
	private double crossProbability = 0.8;

	public abstract List<T> cross(List<T> inputSolutions);

	public double getCrossProbability() {
		return crossProbability;
	}

	public void setCrossProbability(double crossProbability) {
		this.crossProbability = crossProbability;
	}
}
