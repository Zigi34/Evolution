package org.evolution.solution.space;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.algorithm.gp.types.Expression;
import org.evolution.solution.TreeSolution;
import org.evolution.solution.space.util.Bounds;

public abstract class StructureSpace<T extends Expression> extends
		Space<TreeSolution<T>> {

	private List<T> functions = new LinkedList<T>();
	private List<T> terminals = new LinkedList<T>();
	private int maxDepth = 3;
	private boolean complete = false;
	private Hashtable<String, Bounds<Number>> variables = new Hashtable<String, Bounds<Number>>();

	@Override
	public int getDimension() {
		return variables.size();
	}

	public List<T> getFunctions() {
		return functions;
	}

	public List<T> getTerminals() {
		return terminals;
	}

	// DodÏlat funkci a vloûÌt vol·nÌ vyjÌmek a z·pis do logu
	public void addExpression(T expression) throws SolutionSpaceException {
		if (expression == null)
			throw new SolutionSpaceException(this,
					"Expression must not be null");
		if (expression.isTerminal() && !terminals.contains(expression)) {
			terminals.add(expression);
		} else if (!functions.contains(expression)) {
			functions.add(expression);
		}
	}

	// JeötÏ upravit vol·nÌ aù nenÌ tak hnusnÈ
	public Bounds<Number> getVariableBound(int index) {
		return (Bounds<Number>) variables.values().toArray()[index];
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public abstract TreeSolution<T> getRandomSolution();
}
