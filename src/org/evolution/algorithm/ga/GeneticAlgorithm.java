package org.evolution.algorithm.ga;

import java.security.Policy.Parameters;
import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.algorithm.population.Population;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.select.SelectFunction;
import org.evolution.solution.Solution;

public class GeneticAlgorithm<T extends Solution> extends OptimizeAlgorithm<T> {
	/* SELECTION */
	public static final double maxSelection = 1.0;
	public static final double minSelection = 0.5;
	public double selectProbability = 0.6;

	/* MUTATION */
	public static final double minMutate = 0.0;
	public static final double maxMutate = 0.8;
	private double mutateRange = 0.5;

	/* EVOLUTION OPERATORS */
	private SelectFunction<T> selectFce;
	private CrossFunction<T> crossFce;

	/**
	 * Konstruktor
	 */
	public GeneticAlgorithm() {
		super();
	}

	@Override
	public String toString() {
		return "Genetický algoritmus";
	}

	public void run() {
		startRunning();
		setCurrGeneration(0);
		fireStateListener(GeneticAlgorithmState.STARTED_STATE);
		try {
			setBestSolution(getPopulation().get(0)); // actually best solution

			// Initial evaluation and check best individual
			Population.sortPopulation(getPopulation(), getProblem());
			refreshBest(getPopulation().get(0));

			while (getCurrGeneration() < getMaxGeneration() && isRunning()) {
				List<Individual> selected = getSelectFce().select(
						getSelectProbability(), getProblem(), getPopulation());

				fireStateListener(GeneticAlgorithmState.SELECTED_STATE);

				Population newPopulation = new Population();
				getCrossFce().cross(selected, newPopulation);

				fireStateListener(GeneticAlgorithmState.CROSSED_STATE);

				mutate(newPopulation, getMutateRange());

				fireStateListener(GeneticAlgorithmState.MUTATED_STATE);

				int populationSize = getPopulation().size();
				List<Individual> pars = elitismus(newPopulation, populationSize);
				getPopulation().clear();
				getPopulation().addAll(pars);
				fireStateListener(EvolutionAlgorithmState.NEW_POPULATION_CREATED_STATE);

				Population.sortPopulation(getPopulation(), getProblem());
				refreshBest((Parameters) getPopulation().get(0));

				incrementGeneration();

				Thread.sleep(1);
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		stopRunning();
		fireStateListener(GeneticAlgorithmState.ENDED_STATE);
	}

	private void mutate(List<Individual> solutions, double mutateRange) {
		long size = Math.round(solutions.size() * mutateRange);
		for (int i = 0; i < size; i++) {
			int randPos = random.nextInt(getProblem().getRanges().size());
			solutions.get(i).set(randPos, getProblem().getRandomValue(randPos));
		}
	}

	private List<Individual> elitismus(List<Individual> childrens,
			int populationSize) {
		List<Individual> result = new LinkedList<Individual>();
		result.addAll(childrens);

		int i = 0;
		while (result.size() < populationSize) {
			result.add((Parameters) getPopulation().get(i));
			i++;
		}
		return result;
	}

	/* SELECT PROBABILITY */
	public double getSelectProbability() {
		return selectProbability;
	}

	public void setSelectProbability(double probability) {
		this.selectProbability = probability;
		if (probability > maxSelection)
			this.selectProbability = maxSelection;
		if (probability < minSelection)
			this.selectProbability = minSelection;
	}

	/* SELECT FUNCTION */
	public SelectFunction getSelectFce() {
		return selectFce;
	}

	public void setSelectFce(SelectFunction selectFce) {
		this.selectFce = selectFce;
	}

	/* RANGE OF MUTATE */
	public double getMutateRange() {
		return mutateRange;
	}

	public void setMutateRange(double mutateRange) {
		this.mutateRange = mutateRange;
		if (mutateRange > maxMutate)
			this.mutateRange = maxMutate;
		if (mutateRange < minMutate)
			this.mutateRange = minMutate;
	}

	/* CROSS FUNCTIONS */
	public CrossFunction getCrossFce() {
		return crossFce;
	}

	public void setCrossFce(CrossFunction crossFce) {
		this.crossFce = crossFce;
	}
}
