package org.evolution.algorithm.ga;

import java.util.List;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.algorithm.exception.AlgorithmException;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.GeneticAlgorithmState;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.elitismus.ElitismusFunction;
import org.evolution.function.mutate.MutateFunction;
import org.evolution.function.select.SelectFunction;
import org.evolution.solution.Solution;

public class GeneticAlgorithm<T extends Solution> extends OptimizeAlgorithm<T> {

	/* EVOLUTION OPERATORS */
	private SelectFunction<T> selectFunction;
	private CrossFunction<T> crossFunction;
	private ElitismusFunction<T> elitismusFunction;
	private MutateFunction<T> mutateFunction;

	private boolean isParentInPopulation = true;

	/**
	 * Konstruktor
	 */
	public GeneticAlgorithm() {
		super();
	}

	@Override
	public String toString() {
		return "Genetic algorithm";
	}

	@Override
	public void initialize() throws AlgorithmException {
		super.initialize();

	}

	public void run() {
		try {
			initialize();
			fireStateListener(GeneticAlgorithmState.STARTED);
			// sort solutions from best to worst
			getPopulation().sortPopulation(
					getSolutionSpace().getObjectiveFunction());
			// set first solution in population as best
			setBestSolution(getPopulation().get(0));

			// calculate algorithm cycle, while reaching maximum iteration
			while (getActualIteration() < getMaxIteration() && isRunning()) {

				Population<T> newPopulation = new Population<T>();
				// SELECTION
				fireStateListener(GeneticAlgorithmState.SELECT_START);
				List<T> parentSolutions = getSelectFunction().select(
						getPopulation());
				if (isParentInPopulation)
					newPopulation.addAll(parentSolutions);
				fireStateListener(GeneticAlgorithmState.SELECT_END);

				// CROSS OVER
				fireStateListener(GeneticAlgorithmState.CROSS_START);
				List<T> potomci = getCrossFunction().cross(parentSolutions);
				newPopulation.addAll(potomci);
				fireStateListener(GeneticAlgorithmState.CROSS_END);

				// MUTATION
				fireStateListener(GeneticAlgorithmState.MUTATE_START);
				potomci = getMutateFunction().mutate(potomci,
						getSolutionSpace());
				fireStateListener(GeneticAlgorithmState.MUTATE_END);

				fireStateListener(GeneticAlgorithmState.ELITISMUS_END);
				List<T> elitismusSolutions = getElitismusFunction().elitismus(
						newPopulation);
				fireStateListener(GeneticAlgorithmState.ELITISMUS_END);

				getPopulation().clear();
				getPopulation().addAll(elitismusSolutions);
				fireStateListener(GeneticAlgorithmState.NEW_POPULATION_CREATED);

				// sort solutions from best to worst
				getPopulation().sortPopulation(
						getSolutionSpace().getObjectiveFunction());
				// set first solution in population as best
				checkBestSolution(getPopulation().get(0));

				// increse iteration
				increseIteration();
			}
			stop();
			fireStateListener(GeneticAlgorithmState.ENDED);
		} catch (AlgorithmException exception) {
			log.error(exception);
		}
	}

	public CrossFunction<T> getCrossFunction() {
		return crossFunction;
	}

	public void setCrossFunction(CrossFunction<T> crossFunction) {
		this.crossFunction = crossFunction;
	}

	public ElitismusFunction<T> getElitismusFunction() {
		return elitismusFunction;
	}

	public void setElitismusFunction(ElitismusFunction<T> elitismusFunction) {
		this.elitismusFunction = elitismusFunction;
		this.elitismusFunction.setAlgorithm(this);
	}

	public SelectFunction<T> getSelectFunction() {
		return selectFunction;
	}

	public void setSelectFunction(SelectFunction<T> selectFunction) {
		this.selectFunction = selectFunction;
		this.selectFunction.setAlgorithm(this);
	}

	public MutateFunction<T> getMutateFunction() {
		return mutateFunction;
	}

	public void setMutateFunction(MutateFunction<T> mutateFunction) {
		this.mutateFunction = mutateFunction;
		this.mutateFunction.setAlgorithm(this);
	}
}
