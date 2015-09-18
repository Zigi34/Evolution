package org.evolution.function.neighborhood;

import java.util.Random;

import org.evolution.solution.Solution;
import org.evolution.solution.space.Space;

public abstract class NeighborhoodFunction<T extends Solution> {
	private Space<T> solutionSpace;
	protected static Random random = new Random();

	public NeighborhoodFunction(Space<T> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}

	protected Space<T> getSolutionSpace() {
		return solutionSpace;
	}

	public abstract T getRandomNeighborhood(T solution);

	public abstract T getRandomNeighborhood(T solution, int dimensionIndex);
}
