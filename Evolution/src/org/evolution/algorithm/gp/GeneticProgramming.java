package org.evolution.algorithm.gp;

import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.algorithm.exception.AlgorithmException;
import org.evolution.algorithm.gp.types.Expression;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.GeneticAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.util.Constants;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.mutate.MutateFunction;
import org.evolution.function.select.SelectFunction;
import org.evolution.model.ConfigurationModel;
import org.evolution.solution.TreeSolution;
import org.evolution.solution.space.Space;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class GeneticProgramming<T extends Expression> extends
		OptimizeAlgorithm<TreeSolution<T>> implements ConfigurationModel {

	public final static String XML_ENTITY = "genetic_programming";

	/* EVOLUTION OPERATORS */
	private SelectFunction<TreeSolution<T>> selectFunction;
	private CrossFunction<TreeSolution<T>> crossFunction;
	private MutateFunction<TreeSolution<T>> mutateFunction;
	private Space<TreeSolution<T>> solutionSpace;

	private double crossProbability = 0.75;
	private Random random = new Random();

	/**
	 * Konstruktor
	 */
	public GeneticProgramming() {
		super();
	}

	@Override
	public String toString() {
		return "Genetic programming";
	}

	@Override
	public void initialize() throws AlgorithmException {
		super.initialize();
		if (getSelectFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_SELECT_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<TreeSolution<T>>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getMutateFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_MUTATE_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<TreeSolution<T>>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getCrossFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_CROSS_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<TreeSolution<T>>(this,
							OptimizeAlgorithmState.INITIALIZE));
		/*
		 * if (getElitismusFunction() == null) throw new AlgorithmException(
		 * Constants.ERROR_ELITISMUS_FUNCTION_NOT_SET, new
		 * OptimizeAlgorithmState<T>(this, OptimizeAlgorithmState.INITIALIZE));
		 */
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

				List<TreeSolution<T>> potomci = null;
				Population<TreeSolution<T>> newPopulation = new Population<TreeSolution<T>>();
				while (newPopulation.size() < getPopulation().size()) {
					if (random.nextDouble() < crossProbability) {
						// SELECTION
						fireStateListener(GeneticAlgorithmState.SELECT_START);
						List<TreeSolution<T>> parentSolutions = getSelectFunction()
								.select(getPopulation(), 2);
						fireStateListener(GeneticAlgorithmState.SELECT_END);

						// CROSS OVER
						fireStateListener(GeneticAlgorithmState.CROSS_START);
						potomci = getCrossFunction().cross(parentSolutions);
						newPopulation.addAll(potomci);
						fireStateListener(GeneticAlgorithmState.CROSS_END);
					} else {
						// SELECTION
						fireStateListener(GeneticAlgorithmState.SELECT_START);
						List<TreeSolution<T>> parentSolutions = getSelectFunction()
								.select(getPopulation(), 2);
						fireStateListener(GeneticAlgorithmState.SELECT_END);

						// MUTATION
						fireStateListener(GeneticAlgorithmState.MUTATE_START);
						potomci = getMutateFunction().mutate(parentSolutions,
								getSolutionSpace());
						fireStateListener(GeneticAlgorithmState.MUTATE_END);
					}
					newPopulation.addAll(potomci);
				}

				if (newPopulation.size() > getPopulation().size())
					newPopulation.remove(0);

				getPopulation().clear();
				getPopulation().addAll(newPopulation);
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

	public CrossFunction<TreeSolution<T>> getCrossFunction() {
		return crossFunction;
	}

	public void setCrossFunction(CrossFunction<TreeSolution<T>> crossFunction) {
		this.crossFunction = crossFunction;
	}

	/*
	 * public ElitismusFunction<T> getElitismusFunction() { return
	 * elitismusFunction; }
	 * 
	 * public void setElitismusFunction(ElitismusFunction<T> elitismusFunction)
	 * { this.elitismusFunction = elitismusFunction;
	 * this.elitismusFunction.setAlgorithm(this); }
	 */
	public SelectFunction<TreeSolution<T>> getSelectFunction() {
		return selectFunction;
	}

	public void setSelectFunction(SelectFunction<TreeSolution<T>> selectFunction) {
		this.selectFunction = selectFunction;
		this.selectFunction.setAlgorithm(this);
	}

	public MutateFunction<TreeSolution<T>> getMutateFunction() {
		return mutateFunction;
	}

	public void setMutateFunction(MutateFunction<TreeSolution<T>> mutateFunction) {
		this.mutateFunction = mutateFunction;
	}

	@Override
	public Element createXML() {
		Element root = super.createXML();
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XML_ENTITY);
			rootElement.appendChild(doc.importNode(root, true));
			doc.appendChild(rootElement);
			return rootElement;
		} catch (Exception exc) {
			log.error("Create XML is failed");
		}
		return null;
	}

	@Override
	public void loadXML(Element element) {
		// TODO Auto-generated method stub

	}

	public Space<TreeSolution<T>> getSolutionSpace() {
		return solutionSpace;
	}

	public void setSolutionSpace(Space<TreeSolution<T>> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}
}
