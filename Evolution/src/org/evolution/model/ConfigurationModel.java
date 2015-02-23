package org.evolution.model;

import org.w3c.dom.Element;

public interface ConfigurationModel {
	public Element createXML();

	public void loadXML(Element element);
}
