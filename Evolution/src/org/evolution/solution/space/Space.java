package org.evolution.solution.space;

import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.model.ConfigurationModel;
import org.evolution.solution.Solution;
import org.evolution.solution.space.restriction.RestrictiveCondition;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Space<T extends Solution> implements ConfigurationModel {
	private List<RestrictiveCondition<T>> restrictives = new LinkedList<RestrictiveCondition<T>>();
	private boolean isHardRestricted = true;
	private ObjectiveFunction<T> objectiveFunction;

	public final static String XML_ENTITY = "solution_space";

	public void addRestrictive(RestrictiveCondition<T> condition) {
		if (restrictives.contains(condition))
			restrictives.add(condition);
	}

	public void clearRestrictiveConditions() {
		restrictives.clear();
	}

	public void removeRestrictivecondition(RestrictiveCondition<T> condition) {
		if (restrictives.contains(condition))
			restrictives.remove(condition);
	}

	public List<RestrictiveCondition<T>> getRestrictiveConditions() {
		return restrictives;
	}

	/*
	 * public T getSolutionPattern() { return solutionPattern; }
	 * 
	 * public void setSolutionPattern(T solutionPattern) { this.solutionPattern
	 * = solutionPattern; }
	 */
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
		objectiveFunction.setSolutionSpace(this);
	}

	@Override
	public Element createXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XML_ENTITY);
			for (RestrictiveCondition<T> condition : restrictives) {
				rootElement.appendChild(condition.createXML());
			}
			doc.appendChild(rootElement);
			return rootElement;
		} catch (Exception exc) {
			// log.error("Create XML is failed");
		}
		return null;
	}

	@Override
	public void loadXML(Element element) {
		// TODO Auto-generated method stub

	}
}
