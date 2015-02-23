package org.evolution;

import java.io.File;
import java.util.Hashtable;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.algorithm.ga.ArrayGeneticAlgorithm;
import org.evolution.algorithm.ga.GeneticAlgorithm;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.GeneticAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmStateListener;
import org.evolution.algorithm.util.XMLManager;
import org.evolution.function.cross.CrossFunction;
import org.evolution.function.cross.GeneticCrossFunction;
import org.evolution.function.elitismus.ElitismusFunction;
import org.evolution.function.elitismus.GeneralElitismusFunction;
import org.evolution.function.mutate.GeneticMuateFunction;
import org.evolution.function.objective.DeJong1;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.function.select.ArrayRouletteWheelSelect;
import org.evolution.function.select.SelectFunction;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.MultidimensionalSpace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Main {

	private GeneticAlgorithm<ArraySolution> algorithm = new ArrayGeneticAlgorithm();

	public static void main(String[] args) {
		Main start = new Main();
		start.startGeneticAlgorithm();
	}

	public void startGeneticAlgorithm() {
		// Cross function
		CrossFunction<ArraySolution> crossFce = new GeneticCrossFunction<ArraySolution>();
		// crossFce.setCrossProbability(0.8);
		algorithm.setCrossFunction(crossFce);

		// Mutate function
		GeneticMuateFunction<ArraySolution> mutateFce = new GeneticMuateFunction<ArraySolution>();
		// mutateFce.setMutatedProbability(0.05);
		algorithm.setMutateFunction(mutateFce);

		// Select function
		SelectFunction<ArraySolution> selectFce = new ArrayRouletteWheelSelect();
		algorithm.setSelectFunction(selectFce);

		// Elitismus
		ElitismusFunction<ArraySolution> elitismusFce = new GeneralElitismusFunction<ArraySolution>();
		algorithm.setElitismusFunction(elitismusFce);

		// Solution Space
		ObjectiveFunction<ArraySolution> objectiveFce = new DeJong1<ArraySolution>();

		MultidimensionalSpace solutionSpace = new MultidimensionalSpace();
		try {
			solutionSpace.addBound(-4, 4);
			solutionSpace.addBound(-4, 4);
		} catch (SolutionSpaceException e) {
			e.printStackTrace();
		}
		solutionSpace.setObjectiveFunction(objectiveFce);
		algorithm.setSolutionSpace(solutionSpace);

		// Population
		Population<ArraySolution> population = new Population<ArraySolution>();
		population.createRandomPopulation(20, solutionSpace);
		algorithm.setPopulation(population);

		algorithm.setMaxIteration(2);

		algorithm
				.addStateListener(new OptimizeAlgorithmStateListener<ArraySolution>() {
					public void handleStateChanged(
							OptimizeAlgorithmState<ArraySolution> eventCode) {
						if (eventCode.getState() == OptimizeAlgorithmState.STARTED) {
							System.out.println("START [POPULACE = "
									+ eventCode.getAlgorithm().getPopulation()
											.size()
									+ "][ITERACI = "
									+ eventCode.getAlgorithm()
											.getMaxIteration() + "]");
						} else if (eventCode.getState() == OptimizeAlgorithmState.ENDED) {
							System.out.println("END [BEST = "
									+ eventCode.getAlgorithm()
											.getBestSolution()
											.getFunctionValue() + "]");
							System.out.println("HISTOGRAM:");
							Hashtable<Integer, Double> histogram = eventCode
									.getAlgorithm().getHistogram();
							for (int i = 1; i <= histogram.size(); i++) {
								System.out.println(i + " = " + histogram.get(i));
							}
						} else if (eventCode.getState() == OptimizeAlgorithmState.ITERATION_CHANGED) {
							System.out.println("\tGENERATION ("
									+ eventCode.getAlgorithm()
											.getActualIteration()
									+ ") [POPULACE = "
									+ eventCode.getAlgorithm().getPopulation()
											.size()
									+ "][BEST = "
									+ eventCode.getAlgorithm()
											.getBestSolution()
											.getFunctionValue() + "]");
						} else if (eventCode.getState() == GeneticAlgorithmState.SELECT_END) {
							System.out.println("\t\tSELECT");
						} else if (eventCode.getState() == GeneticAlgorithmState.CROSS_END) {
							System.out.println("\t\tCROSS");
						} else if (eventCode.getState() == GeneticAlgorithmState.MUTATE_END) {
							System.out.println("\t\tMUTATE");
						} else if (eventCode.getState() == GeneticAlgorithmState.ELITISMUS_END) {
							System.out.println("\t\tELITISMUS");
						}
					}
				});

		// algorithm.start();
		try {
			Document doc = XMLManager.createDocument();
			Element elem = algorithm.createXML();
			Element root = doc.createElement("config");
			root.appendChild(doc.importNode(elem, true));
			doc.appendChild(root);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("file.xml"));

			transformer.transform(source, result);

			System.out.println("File saved!");
			System.out.println(elem);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
