package org.evolution.function.neighborhood;

import org.apache.log4j.Logger;
import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.ArraySpace;
import org.evolution.solution.space.Space;

public class BasicNeighborhoodFunction extends
		NeighborhoodFunction<ArraySolution> {
	private Logger log = Logger.getLogger(getClass());

	public BasicNeighborhoodFunction(Space<ArraySolution> solutionSpace) {
		super(solutionSpace);
	}

	@Override
	public ArraySolution getRandomNeighborhood(ArraySolution solution) {
		try {
			int randomIndex = random.nextInt(getSolutionSpace().getDimension());
			ArraySolution copy = (ArraySolution) solution.createCopy();
			ArraySpace space = (ArraySpace) getSolutionSpace();
			copy.setValue(randomIndex, space.getRandomValue(randomIndex));
			return copy;
		} catch (SolutionSpaceException exception) {
			log.error(exception);
		}
		return null;
	}

	@Override
	public ArraySolution getRandomNeighborhood(ArraySolution solution,
			int dimensionIndex) {
		ArraySolution copy = (ArraySolution) solution.createCopy();
		try {
			ArraySpace space = (ArraySpace) getSolutionSpace();
			copy.setValue(dimensionIndex, space.getRandomValue(dimensionIndex));
			return copy;
		} catch (SolutionSpaceException exception) {
			log.error(exception);
		}
		return null;
	}
}
