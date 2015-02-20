package org.evolution.function.neighborhood;

import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.MultidimensionalSpace;
import org.evolution.solution.space.SolutionSpace;

public class BasicNeighborhoodFunction extends
		NeighborhoodFunction<ArraySolution> {

	public BasicNeighborhoodFunction(SolutionSpace<ArraySolution> solutionSpace) {
		super(solutionSpace);
	}

	@Override
	public ArraySolution getRandomNeighborhood(ArraySolution solution) {
		int randomIndex = random.nextInt(getSolutionSpace().getDimension());
		ArraySolution copy = (ArraySolution) solution.createCopy();
		MultidimensionalSpace space = (MultidimensionalSpace) getSolutionSpace();
		copy.setValue(randomIndex, space.getRandomValue(randomIndex));
		return copy;
	}

	@Override
	public ArraySolution getRandomNeighborhood(ArraySolution solution,
			int dimensionIndex) {
		ArraySolution copy = (ArraySolution) solution.createCopy();
		MultidimensionalSpace space = (MultidimensionalSpace) getSolutionSpace();
		copy.setValue(dimensionIndex, space.getRandomValue(dimensionIndex));
		return copy;
	}

}
