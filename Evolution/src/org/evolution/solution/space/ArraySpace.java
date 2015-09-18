package org.evolution.solution.space;

import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.util.Bounds;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ArraySpace<T> extends Space<ArraySolution<T>> {
	private Logger log = Logger.getLogger(getClass());
	private LinkedList<Bounds<Number>> bounds = new LinkedList<>();
	public final static String XML_ENTITY = "multidimensional_space";

	public ArraySpace() {
	}

	public int getDimension() {
		return bounds.size();
	}

	public Bounds<Number> getBound(int index) {
		return bounds.get(index);
	}

	public void setBounds(int index, Bounds<Number> bounds) {
		this.bounds.set(index, bounds);
	}

	public void addBound(double minVal, double maxVal)
			throws SolutionSpaceException {
		if (minVal > maxVal) {
			throw new SolutionSpaceException(this,
					"Max value of new dimension must be equal or greater then min value.");
		}
		bounds.add(new Bounds<Number>(this, minVal, maxVal));
	}

	/*
	 * @Override public ArraySolution<T> getRandomSolution() { ArraySolution<T>
	 * solution = new ArraySolution(); try { for (int i = 0; i < getDimension();
	 * i++) { Bounds<Number> bound = getBound(i); Number randomValue =
	 * bound.getRandomValue(); solution.setValue(i, randomValue); } return
	 * solution; } catch (SolutionSpaceException exception) {
	 * log.error(exception); } return null; }
	 */

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
			Element boundsElement = doc.createElement("bounds");
			rootElement.appendChild(boundsElement);
			for (Bounds<Number> bound : bounds) {
				Element boundElement = doc.createElement("bound");
				boundElement
						.setAttribute("min", bound.getMinValue().toString());
				boundElement
						.setAttribute("max", bound.getMaxValue().toString());
				boundsElement.appendChild(boundElement);
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
