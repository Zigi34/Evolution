package org.evolution.solution.space.restriction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.evolution.solution.NumericSolution;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BasicRestrictiveFunction extends
		RestrictiveCondition<NumericSolution> {

	public final static String XML_ENTITY = "conditions";

	@Override
	public boolean isIncluded(NumericSolution solution) {
		for (int i = 0; i < solution.size(); i++) {
			if (solution.get(0).getClass().equals(Integer.class))
				if (solution.get(i).intValue() < 0)
					return true;
		}
		return false;
	}

	@Override
	public Element createXML() {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XML_ENTITY);
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
