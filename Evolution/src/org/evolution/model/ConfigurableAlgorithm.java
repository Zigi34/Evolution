package org.evolution.model;

import java.io.File;
import java.io.FileNotFoundException;

public interface ConfigurableAlgorithm {
	public void fromXML(File file);

	public void toXML(File file) throws FileNotFoundException;
}
