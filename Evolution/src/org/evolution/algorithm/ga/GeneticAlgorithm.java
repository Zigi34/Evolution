package org.evolution.algorithm.ga;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.evolution.algorithm.OptimizeAlgorithm;
import org.evolution.algorithm.exception.AlgorithmException;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.GeneticAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.util.Constants;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.elitismus.ElitismusFunction;
import org.evolution.function.mutate.MutateFunction;
import org.evolution.function.select.SelectFunction;
import org.evolution.model.ConfigurationModel;
import org.evolution.solution.Solution;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class GeneticAlgorithm<T extends Solution> extends
		OptimizeAlgorithm<T> implements ConfigurationModel {

	public final static String XML_ENTITY = "genetic_algorithm";

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
		if (getSelectFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_SELECT_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getMutateFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_MUTATE_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getCrossFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_CROSS_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getElitismusFunction() == null)
			throw new AlgorithmException(
					Constants.ERROR_ELITISMUS_FUNCTION_NOT_SET,
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
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
}
