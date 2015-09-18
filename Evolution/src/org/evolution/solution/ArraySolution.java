package org.evolution.solution;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.evolution.algorithm.util.XMLManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ArraySolution<T> extends Solution {
	private Logger log = Logger.getLogger(getClass());
	private ArrayList<T> values;
	private NumberFormat formatter = new DecimalFormat("#0.000");

	public final static String XML_ELEMENT = "multidim_solution";

	public ArraySolution() {
		this(2);
	}

	public ArraySolution(int dimension) {
		values = new ArrayList<T>(dimension);
	}

	public ArraySolution(ArraySolution<T> solution) {
		this(solution.size());
		for (int index = 0; index < size(); index++)
			values.add(solution.get(index));
	}

	public void setValue(int dimensionIndex, T value) {
		values.set(dimensionIndex, value);
	}

	public T get(int index) {
		return values.get(index);
	}

	protected List<T> getValues() {
		return values;
	}

	public int size() {
		return values.size();
	}

	public abstract Solution createCopy();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size() - 1; i++)
			builder.append(formatter.format(values.get(i)) + ", ");
		builder.append(formatter.format(values.get(size() - 1)));
		return builder.toString();
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
			for (T value : values) {
				Element valueElement = doc.createElement("parameter");
				if (value.getClass() == Double.class)
					valueElement.setAttribute("value", String.valueOf(value));
				else if (value.getClass() == Integer.class)
					valueElement.setAttribute("value", String.valueOf(value));
				else if (value.getClass() == Byte.class)
					valueElement.setAttribute("value", String.valueOf(value));
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
