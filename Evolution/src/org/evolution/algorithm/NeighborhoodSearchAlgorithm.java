package org.evolution.algorithm;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.evolution.function.neighborhood.NeighborhoodFunction;
import org.evolution.solution.NumericSolution;
import org.evolution.solution.space.Space;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NeighborhoodSearchAlgorithm<T extends NumericSolution> extends
		OptimizeAlgorithm<T> {

	public final static String XML_ENTITY = "neighborhood_search";
	private NeighborhoodFunction<T> localSearchFunction;
	private Space<T> space;

	private int maxIteration = 10;
	private int actualIteration = 0;
	private Integer localSearchIndex;

	public NeighborhoodSearchAlgorithm() {
	}

	public Integer getLocalSearchIndex() {
		return localSearchIndex;
	}

	public void setLocalSearchIndex(Integer localSearchIndex) {
		this.localSearchIndex = localSearchIndex;
	}

	public void run() {
		if (initialSolution == null)
			initialSolution = getSolutionSpace().getRandomSolution();
		checkBestSolution(initialSolution);

		while (actualIteration < maxIteration) {
			T findSolution = null;
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

	}

	public NeighborhoodFunction<T> getLocalSearchFunction() {
		return localSearchFunction;
	}

	public void setLocalSearchFunction(
			NeighborhoodFunction<T> localSearchFunction) {
		this.localSearchFunction = localSearchFunction;
	}

	public Space<T> getSolutionSpace() {
		return space;
	}

	public void setSolutionSpace(Space<T> space) {
		this.space = space;
	}
}
