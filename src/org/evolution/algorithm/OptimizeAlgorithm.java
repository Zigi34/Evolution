package org.evolution.algorithm;

import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmStateListener;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;

/**
 * Basic class for all optimize algorithm.
 * 
 * @author Zdenek Gold
 *
 * @param <T>
 *            generic type of optimized solution
 */
public abstract class OptimizeAlgorithm<T extends Solution> implements Runnable {
	/**
	 * objective function of algorithm
	 */
	private ObjectiveFunction<T> objectiveFunction;
	/**
	 * space of solution
	 */
	private SolutionSpace<T> solutionSpace;
	/**
	 * states of algorithm
	 */
	private List<OptimizeAlgorithmStateListener> states = new LinkedList<OptimizeAlgorithmStateListener>();
	private OptimizeAlgorithmState currentState;

	/**
	 * Population of solutions in this generation
	 */
	private Population population;
	/**
	 * actual best solution of searching in solution space
	 */
	protected T bestSolution;
	/**
	 * best solution function value
	 */
	protected double bestSolutionValue;
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
	public OptimizeAlgorithm(ObjectiveFunction<T> objectiveFunction,
			SolutionSpace<T> solutionSpace) {
		this.solutionSpace = solutionSpace;
		this.objectiveFunction = objectiveFunction;
	}

	public OptimizeAlgorithm() {

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

	/**
	 * Actual iteration
	 * 
	 * @return
	 */
	public int getActualIteration() {
		return actualIteration;
	}

	public ObjectiveFunction<T> getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(ObjectiveFunction<T> objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
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

	public Population getPopulation() {
		return population;
	}

	public void setPopulation(Population population) {
		this.population = population;
	}

	/**
	 * Add new listener, to react on updates of this instance
	 * 
	 * @param listener
	 */
	public void addStateListener(OptimizeAlgorithmStateListener listener) {
		if (listener != null && !states.contains(listener))
			states.add(listener);
	}

	/**
	 * Remove selected listener
	 * 
	 * @param listener
	 */
	public void removeStateListener(OptimizeAlgorithmStateListener listener) {
		if (listener != null && states.contains(listener))
			states.remove(listener);
	}

	/**
	 * Remove all listeners
	 */
	public void clearStateListener() {
		states.clear();
	}

	protected void fireStateListener(final int state) {
		currentState = OptimizeAlgorithmState.createState(state, this);
		for (OptimizeAlgorithmStateListener item : states)
			item.handleStateChanged(this, currentState);
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

	/**
	 * Initialize state of algorithm before it is started
	 */
	public abstract void initialize();

	/**
	 * Run algorithm in new thread
	 */
	public abstract void run();

	/**
	 * Run algorithm in thread
	 */
	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Stop threading algorithm
	 */
	public void stop() {
		isRunning = false;
		actualIteration = 0;
	}

}
