package org.evolution;

import java.util.Hashtable;

import org.evolution.algorithm.ga.GeneticAlgorithm;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.GeneticAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmStateListener;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.cross.GeneticCrossFunction;
import org.evolution.function.elitismus.ElitismusFunction;
import org.evolution.function.elitismus.GeneralElitismusFunction;
import org.evolution.function.mutate.GeneticMuateFunction;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.function.objective.TestFunction;
import org.evolution.function.select.RouletteWheelSelect;
import org.evolution.function.select.SelectFunction;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.MultidimensionalSpace;
import org.evolution.solution.space.SolutionSpace;

public class Main {

	private GeneticAlgorithm<ArraySolution> algorithm = new GeneticAlgorithm<ArraySolution>();

	public static void main(String[] args) {
		Main start = new Main();
		start.startGeneticAlgorithm();
	}

	public void startGeneticAlgorithm() {
		CrossFunction<ArraySolution> crossFce = new GeneticCrossFunction<ArraySolution>();
		crossFce.setCrossProbability(0.9);
		algorithm.setCrossFunction(crossFce);

		GeneticMuateFunction<ArraySolution> mutateFce = new GeneticMuateFunction<ArraySolution>();
		mutateFce.setMutatedProbability(0.05);
		algorithm.setMutateFunction(mutateFce);

		SelectFunction<ArraySolution> selectFce = new RouletteWheelSelect<ArraySolution>();
		algorithm.setSelectFunction(selectFce);

		ElitismusFunction<ArraySolution> elitismusFce = new GeneralElitismusFunction<ArraySolution>();
		algorithm.setElitismusFunction(elitismusFce);

		SolutionSpace<ArraySolution> solutionSpace = new MultidimensionalSpace();
		ObjectiveFunction<ArraySolution> objectiveFce = new TestFunction();
		solutionSpace.setObjectiveFunction(objectiveFce);
		algorithm.setSolutionSpace(solutionSpace);

		Population<ArraySolution> population = new Population<ArraySolution>();
		population.createRandomPopulation(100, solutionSpace);
		algorithm.setPopulation(population);

		algorithm.setMaxIteration(500);

		algorithm
				.addStateListener(new OptimizeAlgorithmStateListener<ArraySolution>() {
					public void handleStateChanged(
							OptimizeAlgorithmState<ArraySolution> eventCode) {
						if (eventCode.getState() == OptimizeAlgorithmState.STARTED) {
							System.out.println("START [POPULACE = "
									+ eventCode.getAlgorithm().getPopulation()
											.size()
									+ "][ITERACI = "
									+ eventCode.getAlgorithm()
											.getMaxIteration() + "]");
						} else if (eventCode.getState() == OptimizeAlgorithmState.ENDED) {
							System.out.println("END [BEST = "
									+ eventCode.getAlgorithm()
											.getBestSolution()
											.getFunctionValue() + "]");
							System.out.println("HISTOGRAM:");
							Hashtable<Integer, Double> histogram = eventCode
									.getAlgorithm().getHistogram();
							for (int i = 1; i <= histogram.size(); i++) {
								System.out.println(i + " = " + histogram.get(i));
							}
						} else if (eventCode.getState() == OptimizeAlgorithmState.ITERATION_CHANGED) {
							System.out.println("\tGENERATION ("
									+ eventCode.getAlgorithm()
											.getActualIteration()
									+ ") [POPULACE = "
									+ eventCode.getAlgorithm().getPopulation()
											.size()
									+ "][BEST = "
									+ eventCode.getAlgorithm()
											.getBestSolution()
											.getFunctionValue() + "]");
						} else if (eventCode.getState() == GeneticAlgorithmState.SELECT_END) {
							System.out.println("\t\tSELECT");
						} else if (eventCode.getState() == GeneticAlgorithmState.CROSS_END) {
							System.out.println("\t\tCROSS");
						} else if (eventCode.getState() == GeneticAlgorithmState.MUTATE_END) {
							System.out.println("\t\tMUTATE");
						} else if (eventCode.getState() == GeneticAlgorithmState.ELITISMUS_END) {
							System.out.println("\t\tELITISMUS");
						}
					}
				});

		algorithm.start();
	}
}
