package org.evolution.solution;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;
import org.evolution.algorithm.util.XMLManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ArraySolution extends Solution {
	private Logger log = Logger.getLogger(getClass());
	private Double[] values;
	private NumberFormat formatter = new DecimalFormat("#0.000");

	public final static String XML_ELEMENT = "multidim_solution";

	public ArraySolution() {
		this(2);
	}

	public ArraySolution(int dimension) {
		values = new Double[dimension];
	}

	public ArraySolution(ArraySolution solution) {
		this(solution.size());
		for (int index = 0; index < size(); index++)
			values[index] = solution.get(index);
	}

	public void setValue(int dimensionIndex, Double value) {
		values[dimensionIndex] = value;
	}

	public Double get(int index) {
		return values[index];
	}

	protected Double[] getValues() {
		return values;
	}

	public int size() {
		return values.length;
	}

	@Override
	public Solution createCopy() {
		return new ArraySolution(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size() - 1; i++)
			builder.append(formatter.format(values[i]) + ", ");
		builder.append(formatter.format(values[size() - 1]));
		return builder.toString();
	}

	@Override
	public void set(Object value, int index) {
		values[index] = (Double) value;
	}

	public Element createXML() {
		Element root = super.createXML();
		try {
			Document doc = XMLManager.createDocument();

			Element rootElement = doc.createElement(XML_ENTITY);
			Element el = (Element) doc.importNode(root, true);
			rootElement.appendChild(el);
			Element valuesElement = doc.createElement("parameters");
			el.appendChild(valuesElement);
			for (Double value : values) {
				Element valueElement = doc.createElement("parameter");
				valueElement.setAttribute("value", value.toString());
				valuesElement.appendChild(valueElement);
			}
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
