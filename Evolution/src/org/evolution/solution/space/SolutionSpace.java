package org.evolution.solution.space;

import java.util.LinkedList;
import java.util.List;

import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.solution.Solution;
import org.evolution.solution.space.restriction.RestrictiveCondition;

public abstract class SolutionSpace<T extends Solution> {
	private List<RestrictiveCondition<T>> restrictiveConditions = new LinkedList<RestrictiveCondition<T>>();
	private boolean isHardRestricted = true;
	private T solutionPattern;
	private ObjectiveFunction<T> objectiveFunction;

	public void add(RestrictiveCondition<T> condition) {
		if (restrictiveConditions.contains(condition))
			restrictiveConditions.add(condition);
	}

	public void clearRestrictiveConditions() {
		restrictiveConditions.clear();
	}

	public void removeRestrictivecondition(RestrictiveCondition<T> condition) {
		if (restrictiveConditions.contains(condition))
			restrictiveConditions.remove(condition);
	}

	public List<RestrictiveCondition<T>> getRestrictiveConditions() {
		return restrictiveConditions;
	}

	public T getSolutionPattern() {
		return solutionPattern;
	}

	public void setSolutionPattern(T solutionPattern) {
		this.solutionPattern = solutionPattern;
	}

	public abstract T getRandomSolution();

	public abstract int getDimension();

	public boolean isFeasibleSolution(T solution) {
		for (RestrictiveCondition<T> condition : getRestrictiveConditions()) {
			if (condition.isIncluded(solution))
				return false;
		}
		return true;
	}

	public Double getFunctionValue(T solution, ObjectiveFunction<T> function) {
		return function.getFunctionValue(solution);
	}

	public ObjectiveFunction<T> getObjectiveFunction() {
		return objectiveFunction;
	}

	public void setObjectiveFunction(ObjectiveFunction<T> objectiveFunction) {
		this.objectiveFunction = objectiveFunction;
	}

	@Override
	public String toString() {
		return "Solution space";
	}
}
