package org.evolution.function.neighborhood;

import java.util.Random;

import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;

public abstract class NeighborhoodFunction<T extends Solution> {
	private SolutionSpace<T> solutionSpace;
	protected static Random random = new Random();

	public NeighborhoodFunction(SolutionSpace<T> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}

	protected SolutionSpace<T> getSolutionSpace() {
		return solutionSpace;
	}

	public abstract T getRandomNeighborhood(T solution);

	public abstract T getRandomNeighborhood(T solution, int dimensionIndex);
}
