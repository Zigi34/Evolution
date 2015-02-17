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

	private boolean minimize = true;

	public NeighborhoodSearchAlgorithm(
			ObjectiveFunction<ArraySolution> objectiveFunction,
			SolutionSpace<ArraySolution> solutionSpace) {
		super(objectiveFunction, solutionSpace);
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

	public void setLocalSearchFunction(
			NeighborhoodFunction<ArraySolution> localSearchFunction) {
		this.localSearchFunction = localSearchFunction;
	}

	private void initialize() {
		if (initialSolution == null)
			initialSolution = getSolutionSpace().getRandomSolution();
		bestSolutionValue = getObjectiveFunction().getFunctionValue(
				initialSolution);
	}

	public void run() {
		initialize();

		while (actualIteration < maxIteration) {
			ArraySolution findSolution = null;
			if (localSearchIndex == null)
				findSolution = getLocalSearchFunction().getRandomNeighborhood(
						initialSolution);
			else
				findSolution = getLocalSearchFunction().getRandomNeighborhood(
						initialSolution, localSearchIndex);

			Double solutionValue = getObjectiveFunction().getFunctionValue(
					findSolution);
			if (!minimize) {
				if (solutionValue > bestSolutionValue) {
					bestSolution = findSolution;
					bestSolutionValue = solutionValue;
				}
			} else {
				if (solutionValue < bestSolutionValue) {
					bestSolution = findSolution;
					bestSolutionValue = solutionValue;
				}
			}
			actualIteration++;
		}
	}
}
