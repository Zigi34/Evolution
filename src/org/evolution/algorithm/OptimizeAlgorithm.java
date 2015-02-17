package org.evolution.algorithm;

import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmStateListener;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;

/**
 * Basic class for all optimize algorithm.
 * 
 * @author Zden�k Gold
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

	public void setMaxIteration(int maxIteration) {
		this.maxIteration = maxIteration;
	}

	public Solution getInitialSolution() {
		return initialSolution;
	}

	public void setInitialSolution(T initialSolution) {
		this.initialSolution = initialSolution;
	}

	public T getBestSolution() {
		return bestSolution;
	}

	/* BEST SOLUTION */
	public void setBestSolution(T best) {
		try {
			this.bestSolution = best;
			fireStateListener(OptimizeAlgorithmState.createState(
					OptimizeAlgorithmState.NEW_BEST_SOLUTION, this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* LISTENERS */
	public void addStateListener(
			OptimizeAlgorithmStateListener<OptimizeAlgorithmState> listener) {
		if (listener != null && !states.contains(listener))
			states.add(listener);
	}

	public void removeStateListener(
			OptimizeAlgorithmStateListener<OptimizeAlgorithmState> listener) {
		if (listener != null && states.contains(listener))
			states.remove(listener);
	}

	public void clearStateListener() {
		states.clear();
	}

	protected void fireStateListener(final OptimizeAlgorithmState state) {
		this.currentState = state;
		for (OptimizeAlgorithmStateListener<OptimizeAlgorithmState> item : states)
			item.handleStateChanged(this, state);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		isRunning = false;
		actualIteration = 0;
	}

	public abstract void run();
}
