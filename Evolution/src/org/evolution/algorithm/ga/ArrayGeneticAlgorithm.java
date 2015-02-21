package org.evolution.algorithm.ga;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.evolution.model.ArrayGeneticAlgorithmModel;
import org.evolution.model.ConfigurableAlgorithm;
import org.evolution.solution.ArraySolution;

import com.thoughtworks.xstream.XStream;

@XmlRootElement
public class ArrayGeneticAlgorithm extends GeneticAlgorithm<ArraySolution>
		implements ConfigurableAlgorithm {

	private Logger log = Logger.getLogger(getClass());
	private ArrayGeneticAlgorithmModel model;

	@Override
	public void fromXML(File file) {
		XStream stream = new XStream();
		ArrayGeneticAlgorithm algorithm = (ArrayGeneticAlgorithm) stream
				.fromXML(file);
	}

	@Override
	public void toXML(File file) throws FileNotFoundException {
		ArrayGeneticAlgorithmModel model = new ArrayGeneticAlgorithmModel();
		model.setCrossFunction(getCrossFunction());
		model.setElitismuFunction(getElitismusFunction());
		model.setMutateFunction(getMutateFunction());
		model.setPopulation(getPopulation());
		model.setSolutionSpace(getSolutionSpace());

		FileOutputStream fis = new FileOutputStream(file);
		XStream stream = new XStream();
		stream.toXML(model, fis);
	}

}
