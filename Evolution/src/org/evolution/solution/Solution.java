package org.evolution.solution;

import org.apache.log4j.Logger;
import org.evolution.algorithm.util.XMLManager;
import org.evolution.model.ConfigurationModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Solution implements ConfigurationModel {
	private Logger log = Logger.getLogger(getClass());

	public final static String XML_ENTITY = "solution";

	private Double functionValue = null;

	public abstract int size();

	public abstract Solution createCopy();

	public abstract Object get(int index);

	public abstract void set(Object value, int index);

	public void setFunctionValue(Double value) {
		this.functionValue = value;
	}

	public Double getFunctionValue() {
		return functionValue;
	}

	public boolean isEvaluated() {
		return functionValue == null ? false : true;
	}

	public Element createXML() {
		try {
			Document doc = XMLManager.createDocument();

			Element rootElement = doc.createElement(XML_ENTITY);
			if (functionValue != null)
				rootElement.setAttribute("function_value",
						functionValue.toString());
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
