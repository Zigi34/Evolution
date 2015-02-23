package org.evolution.algorithm.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class XMLManager {
	private static Logger log = Logger.getLogger(XMLManager.class);

	public static Document createDocument() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			return docBuilder.newDocument();
		} catch (Exception exc) {
			log.error(exc);
		}
		return null;
	}
}
