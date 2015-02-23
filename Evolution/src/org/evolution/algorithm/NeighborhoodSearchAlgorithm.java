package org.evolution.algorithm;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.evolution.function.neighborhood.NeighborhoodFunction;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.solution.ArraySolution;
import org.evolution.solution.space.SolutionSpace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NeighborhoodSearchAlgorithm extends
		OptimizeAlgorithm<ArraySolution> {

	public final static String XML_ENTITY = "neighborhood_search";
	private NeighborhoodFunction<ArraySolution> localSearchFunction;

	private int maxIteration = 10;
	private int actualIteration = 0;
	private Integer localSearchIndex;

	public NeighborhoodSearchAlgorithm(
			ObjectiveFunction<ArraySolution> objectiveFunction,
			SolutionSpace<ArraySolution> solutionSpace) {
		super(solutionSpace);
	}

	public NeighborhoodSearchAlgorithm() {
	}

	public Integer getLocalSearchIndex() {
		return localSearchIndex;
	}

	public void setLocalSearchIndex(Integer localSearchIndex) {
		this.localSearchIndex = localSearchIndex;
	}

	public NeighborhoodFunction<ArraySolution> getLocalSearchFunction() {
		return localSearchFunction;
	}

	/**
	 * Set local search function for this algorithm
	 * 
	 * @param localSearchFunction
	 *            local search function
	 */
	public void setLocalSearchFunction(
			NeighborhoodFunction<ArraySolution> localSearchFunction) {
		this.localSearchFunction = localSearchFunction;
	}

	public void run() {
		if (initialSolution == null)
			initialSolution = getSolutionSpace().getRandomSolution();
		checkBestSolution(initialSolution);

		while (actualIteration < maxIteration) {
			ArraySolution findSolution = null;
			if (localSearchIndex == null)
				findSolution = getLocalSearchFunction().getRandomNeighborhood(
						initialSolution);
			else
				findSolution = getLocalSearchFunction().getRandomNeighborhood(
						initialSolution, localSearchIndex);

			checkBestSolution(findSolution);
			actualIteration++;
		}
	}

	@Override
	public Element createXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("algorithm");
			rootElement.setAttribute("type", XML_ENTITY);
			doc.appendChild(rootElement);
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
