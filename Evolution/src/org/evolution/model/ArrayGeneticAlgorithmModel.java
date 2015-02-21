package org.evolution.model;

import org.evolution.algorithm.population.Population;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.elitismus.ElitismusFunction;
import org.evolution.function.mutate.MutateFunction;
import org.evolution.function.select.SelectFunction;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.SolutionSpace;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("algorithm")
public class ArrayGeneticAlgorithmModel {
	private SelectFunction<ArraySolution> selectFunction;
	private MutateFunction<ArraySolution> mutateFunction;
	private ElitismusFunction<ArraySolution> elitismuFunction;
	private CrossFunction<ArraySolution> crossFunction;
	private SolutionSpace<ArraySolution> solutionSpace;
	private Population<ArraySolution> population;

	public SelectFunction<ArraySolution> getSelectFunction() {
		return selectFunction;
	}

	public void setSelectFunction(SelectFunction<ArraySolution> selectFunction) {
		this.selectFunction = selectFunction;
	}

	public MutateFunction<ArraySolution> getMutateFunction() {
		return mutateFunction;
	}

	public void setMutateFunction(MutateFunction<ArraySolution> mutateFunction) {
		this.mutateFunction = mutateFunction;
	}

	public ElitismusFunction<ArraySolution> getElitismuFunction() {
		return elitismuFunction;
	}

	public void setElitismuFunction(
			ElitismusFunction<ArraySolution> elitismuFunction) {
		this.elitismuFunction = elitismuFunction;
	}

	public CrossFunction<ArraySolution> getCrossFunction() {
		return crossFunction;
	}

	public void setCrossFunction(CrossFunction<ArraySolution> crossFunction) {
		this.crossFunction = crossFunction;
	}

	public SolutionSpace<ArraySolution> getSolutionSpace() {
		return solutionSpace;
	}

	public void setSolutionSpace(SolutionSpace<ArraySolution> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}

	public Population<ArraySolution> getPopulation() {
		return population;
	}

	public void setPopulation(Population<ArraySolution> population) {
		this.population = population;
	}
}
