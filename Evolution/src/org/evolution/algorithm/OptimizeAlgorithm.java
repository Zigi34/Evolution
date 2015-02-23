package org.evolution.algorithm;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.evolution.algorithm.exception.AlgorithmException;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmStateListener;
import org.evolution.algorithm.util.Constants;
import org.evolution.model.ConfigurationModel;
import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Basic class for all optimize algorithm.
 * 
 * @author Zdenek Gold
 *
 * @param <T>
 *            generic type of optimized solution
 */

public abstract class OptimizeAlgorithm<T extends Solution> implements
		Runnable, ConfigurationModel {
	protected Logger log = Logger.getLogger(getClass());

	private int minPopulationSize = 2;
	private int maxPopulationSize = 10000;

	public final static String XML_ENTITY = "optimize_algorithm";

	/**
	 * space of solution
	 */
	private SolutionSpace<T> solutionSpace;
	/**
	 * states of algorithm
	 */
	private List<OptimizeAlgorithmStateListener<T>> states = new LinkedList<OptimizeAlgorithmStateListener<T>>();
	private OptimizeAlgorithmState<T> currentState;

	private Hashtable<Integer, Double> histogram = new Hashtable<>();

	/**
	 * Population of solutions in this generation
	 */
	private Population<T> population;
	/**
	 * actual best solution of searching in solution space
	 */
	protected T bestSolution;
	/**
	 * initial solution
	 */
	protected T initialSolution;

	/**
	 * maximum iteration of algorithm cycle
	 */
	private int maxIteration = 10;
	/**
	 * actual iteration cycle
	 */
	private int actualIteration = 0;

	/**
	 * is it minimize searching
	 */
	private boolean minimize = true;

	private Thread thread;
	private boolean isRunning = true;

	/**
	 * create optimize algorithm
	 * 
	 * @param objectiveFunction
	 *            objective function for solution type
	 * @param solutionSpace
	 *            solution space for solution type
	 */
	public OptimizeAlgorithm(SolutionSpace<T> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}

	public OptimizeAlgorithm() {

	}

	public void initialize() throws AlgorithmException {
		if (getPopulation() == null)
			throw new AlgorithmException(Constants.ERROR_POPULATION_NOT_SET,
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getPopulation().size() < getMinPopulationSize())
			throw new AlgorithmException(String.format(
					Constants.ERROR_POPULATION_TOO_SMALL, minPopulationSize),
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getPopulation().size() > getMaxPopulationSize())
			throw new AlgorithmException(String.format(
					Constants.ERROR_POPULATION_TOO_BIG, maxPopulationSize),
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
		if (getSolutionSpace() == null)
			throw new AlgorithmException(
					Constants.ERROR_SOLUTION_SPACE_NOT_SET,
					new OptimizeAlgorithmState<T>(this,
							OptimizeAlgorithmState.INITIALIZE));
	}

	public int getMinPopulationSize() {
		return minPopulationSize;
	}

	public void setMinPopulationSize(int minPopulationSize) {
		if (minPopulationSize >= 0) {
			if (minPopulationSize <= maxPopulationSize)
				this.minPopulationSize = minPopulationSize;
			else {
				maxPopulationSize = minPopulationSize;
				this.minPopulationSize = minPopulationSize;
			}
		}
	}

	public int getMaxPopulationSize() {
		return maxPopulationSize;
	}

	public void setMaxPopulationSize(int maxPopulationSize) {
		if (maxPopulationSize >= minPopulationSize)
			this.maxPopulationSize = maxPopulationSize;
		else {
			minPopulationSize = maxPopulationSize;
			this.maxPopulationSize = maxPopulationSize;
		}
	}

	/**
	 * Is it minimize searching problem?
	 * 
	 * @return true - must find minimal value of objective function, false -
	 *         must find maximum value of objective function
	 */
	public boolean isMinimize() {
		return minimize;
	}

	/**
	 * Specify, which extreme value must find
	 * 
	 * @param minimize
	 *            if it is true, we must find minimal value of objective
	 *            function, else must find maximum value
	 */
	public void setMinimize(boolean minimize) {
		this.minimize = minimize;
	}

	protected void checkBestSolution(T solution) {
		if (bestSolution == null) {
			bestSolution = solution;
		} else {
			if (solution.getFunctionValue() < bestSolution.getFunctionValue()
					&& minimize)
				bestSolution = solution;
			else if (solution.getFunctionValue() > bestSolution
					.getFunctionValue() && !minimize)
				bestSolution = solution;
		}

	}

	/**
	 * Actual iteration
	 * 
	 * @return
	 */
	public int getActualIteration() {
		return actualIteration;
	}

	public SolutionSpace<T> getSolutionSpace() {
		return solutionSpace;
	}

	public void setSolutionSpace(SolutionSpace<T> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}

	public int getMaxIteration() {
		return maxIteration;
	}

	/**
	 * Set maximum iteration
	 * 
	 * @param maxIteration
	 */
	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	/**
	 * Return initial solution
	 * 
	 * @return
	 */
	public Solution getInitialSolution() {
		return initialSolution;
	}

	/**
	 * Set initialized solution
	 * 
	 * @param initialSolution
	 */
	public void setInitialSolution(T initialSolution) {
		this.initialSolution = initialSolution;
	}

	/**
	 * Return best solution for this generation
	 * 
	 * @return
	 */
	public T getBestSolution() {
		return bestSolution;
	}

	/**
	 * Set this solution as best of found solution in problem
	 * 
	 * @param best
	 *            best solution instance
	 */
	public void setBestSolution(T best) {
		try {
			this.bestSolution = best;
			fireStateListener(OptimizeAlgorithmState.NEW_BEST_SOLUTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Population<T> getPopulation() {
		return population;
	}

	public void setPopulation(Population<T> population) {
		this.population = population;
	}

	/**
	 * Add new listener, to react on updates of this instance
	 * 
	 * @param listener
	 */
	public void addStateListener(OptimizeAlgorithmStateListener<T> listener) {
		if (listener != null && !states.contains(listener))
			states.add(listener);
	}

	/**
	 * Remove selected listener
	 * 
	 * @param listener
	 */
	public void removeStateListener(OptimizeAlgorithmStateListener<T> listener) {
		if (listener != null && states.contains(listener))
			states.remove(listener);
	}

	/**
	 * Remove all listeners
	 */
	public void clearStateListener() {
		states.clear();
	}

	protected void fireStateListener(final String state) {
		currentState = new OptimizeAlgorithmState<T>(this, state);
		for (OptimizeAlgorithmStateListener<T> item : states) {
			if (currentState.getState() == OptimizeAlgorithmState.ITERATION_CHANGED) {
				histogram.put(getActualIteration(), getBestSolution()
						.getFunctionValue());
			}
			item.handleStateChanged(currentState);
		}
	}

	/**
	 * Set actual iteration number
	 * 
	 * @param iteration
	 */
	public void setActualIteration(int iteration) {
		this.actualIteration = iteration;
		fireStateListener(OptimizeAlgorithmState.ITERATION_CHANGED);
	}

	/**
	 * Increment iteration of optimize cycle and call update of listeners
	 */
	public void increseIteration() {
		this.actualIteration++;
		fireStateListener(OptimizeAlgorithmState.ITERATION_CHANGED);
	}

	public Hashtable<Integer, Double> getHistogram() {
		return histogram;
	}

	public void setHistogram(Hashtable<Integer, Double> histogram) {
		this.histogram = histogram;
	}

	public OptimizeAlgorithmState<T> getCurrentState() {
		return currentState;
	}

	/**
	 * Is algorithm in running mode?
	 * 
	 * @return true - is running, false - is stopped
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Run algorithm in new thread
	 */
	public abstract void run();

	/**
	 * Run algorithm in thread
	 */
	public void start() {
		thread = new Thread(this);
		actualIteration = 0;
		thread.start();
	}

	/**
	 * Stop threading algorithm
	 */
	public void stop() {
		isRunning = false;
		actualIteration = 0;
	}

	public Element createXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XML_ENTITY);
			rootElement.appendChild(doc.importNode(solutionSpace.createXML(),
					true));
			rootElement
					.appendChild(doc.importNode(population.createXML(), true));
			doc.appendChild(rootElement);
			return rootElement;
		} catch (Exception exc) {
			log.error("Create XML is failed");
		}
		return null;
	}

	public void loadXML(Element element) {
		// TODO Auto-generated method stub

	}
}
