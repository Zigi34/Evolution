package org.evolution.solution.space;

import java.util.LinkedList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.algorithm.util.Constants;
import org.evolution.solution.ArraySolution;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MultidimensionalSpace extends SolutionSpace<ArraySolution> {
	private Logger log = Logger.getLogger(getClass());
	private Random random = new Random();
	private LinkedList<Bounds> bounds = new LinkedList<>();
	public final static String XML_ENTITY = "multidimensional_space";

	public MultidimensionalSpace() {
	}

	public int getDimension() {
		return bounds.size();
	}

	public Bounds getBound(int index) {
		return bounds.get(index);
	}

	public void setBounds(int index, Bounds bounds) {
		this.bounds.set(index, bounds);
	}

	public Double getRandomValue(int dimensionIndex)
			throws SolutionSpaceException {
		return bounds.get(dimensionIndex).getRandomValue();
	}

	public void addBound(double minVal, double maxVal)
			throws SolutionSpaceException {
		if (minVal > maxVal) {
			throw new SolutionSpaceException(this,
					"Max value of new dimension must be equal or greater then min value.");
		}
		bounds.add(new Bounds(this, minVal, maxVal));
	}

	@Override
	public ArraySolution getRandomSolution() {
		ArraySolution solution = new ArraySolution();
		try {
			for (int i = 0; i < getDimension(); i++) {
				Bounds bound = getBound(i);
				Double randomValue = bound.getRandomValue();
				solution.setValue(i, randomValue);
			}
			return solution;
		} catch (SolutionSpaceException exception) {
			log.error(exception);
		}
		return null;
	}

	public class Bounds {
		private Double minValue = null;
		private Double maxValue = null;
		private MultidimensionalSpace solutionSpace;

		public Bounds(MultidimensionalSpace solutionSpace) {
			this.solutionSpace = solutionSpace;
		}

		public Bounds(MultidimensionalSpace solutionSpace, Double minValue,
				Double maxValue) {
			this.solutionSpace = solutionSpace;
			if (minValue != null)
				this.minValue = minValue;
			if (maxValue != null)
				this.maxValue = maxValue;
		}

		public Double getMinValue() {
			return minValue;
		}

		public Double getMaxValue() {
			return maxValue;
		}

		public void setMinValue(Double minValue) {
			if (minValue != null)
				this.minValue = minValue;
		}

		public void setMaxValue(Double maxValue) {
			if (maxValue != null)
				this.maxValue = maxValue;
		}

		public Double getRandomValue() throws SolutionSpaceException {
			if (minValue == null || maxValue == minValue)
				throw new SolutionSpaceException(solutionSpace,
						Constants.ERROR_MULDIM_DIMENSION_RANGE_NOT_SET);
			return random.nextDouble() * (maxValue - minValue) + minValue;
		}

		@Override
		public String toString() {
			return "Multidimensional space";
		}
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
			Element boundsElement = doc.createElement("bounds");
			rootElement.appendChild(boundsElement);
			for (Bounds bound : bounds) {
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
