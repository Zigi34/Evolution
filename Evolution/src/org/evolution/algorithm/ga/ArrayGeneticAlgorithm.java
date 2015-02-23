package org.evolution.algorithm.ga;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.evolution.model.ConfigurationModel;
import org.evolution.solution.ArraySolution;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@XmlRootElement
public class ArrayGeneticAlgorithm extends GeneticAlgorithm<ArraySolution>
		implements ConfigurationModel {

	private Logger log = Logger.getLogger(getClass());
	public final static String XML_ENTITY = "dimension_genetic_algorithm";

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
			return rootElement;
		} catch (Exception exc) {
			log.error("Create XML is failed");
		}
		return null;
	}

	@Override
	public void loadXML(Element element) {
		// TODO Auto-generated method stub

	}
}
