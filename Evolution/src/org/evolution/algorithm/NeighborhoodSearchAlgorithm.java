package org.evolution.algorithm;

import org.evolution.function.neighborhood.NeighborhoodFunction;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.SolutionSpace;

public class NeighborhoodSearchAlgorithm extends
		OptimizeAlgorithm<ArraySolution> {

	private NeighborhoodFunction<ArraySolution> localSearchFunction;

	private int maxIteration = 10;
	private int actualIteration = 0;
	private Integer localSearchIndex;

	public NeighborhoodSearchAlgorithm(
			ObjectiveFunction<ArraySolution> objectiveFunction,
			SolutionSpace<ArraySolution> solutionSpace) {
		super(solutionSpace);
	}

	public NeighborhoodSearchAlgorithm() {
	}

	public Integer getLocalSearchIndex() {
		return localSearchIndex;
	}

	public void setLocalSearchIndex(Integer localSearchIndex) {
		this.localSearchIndex = localSearchIndex;
	}

	public NeighborhoodFunction<ArraySolution> getLocalSearchFunction() {
		return localSearchFunction;
	}

	/**
	 * Set local search function for this algorithm
	 * 
	 * @param localSearchFunction
	 *            local search function
	 */
	public void setLocalSearchFunction(
			NeighborhoodFunction<ArraySolution> localSearchFunction) {
		this.localSearchFunction = localSearchFunction;
	}

	public void run() {
		if (initialSolution == null)
			initialSolution = getSolutionSpace().getRandomSolution();
		checkBestSolution(initialSolution);

		while (actualIteration < maxIteration) {
			ArraySolution findSolution = null;
			if (localSearchIndex == null)
				findSolution = getLocalSearchFunction().getRandomNeighborhood(
						initialSolution);
			else
				findSolution = getLocalSearchFunction().getRandomNeighborhood(
						initialSolution, localSearchIndex);

			checkBestSolution(findSolution);
			actualIteration++;
		}
	}
}
