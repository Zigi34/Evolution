package org.evolution.algorithm.population;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.evolution.algorithm.util.Constants;
import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.model.ConfigurationModel;
import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Population<T extends Solution> implements Collection<T>, List<T>,
		ConfigurationModel {
	private Logger log = Logger.getLogger(getClass());
	private List<T> values = new LinkedList<T>();

	public final static String XML_ENTITY = "population";

	public Population() {
	}

	/**
	 * Evaluate and sort population from fitness evaluation individual
	 * 
	 * @param solutions
	 *            all individual of population
	 * @param function
	 *            objective function
	 */
	public void sortPopulation(final ObjectiveFunction<T> function) {
		for (T solution : this) {
			// calculate solution function value
			function.getFunctionValue(solution);
		}
		Collections.sort(values, new Comparator<T>() {
			public int compare(T o1, T o2) {
				double f1 = function.getFunctionValue(o1);
				double f2 = function.getFunctionValue(o2);
				if (f1 > f2)
					return -1; // proto, že øadíme sestupnì a ne vzestupnì
				else if (f1 == f2)
					return 0;
				else
					return 1;
			};
		});
	}

	public void createRandomPopulation(int count, SolutionSpace<T> space) {
		for (int i = 0; i < count; i++) {
			T solution = space.getRandomSolution();
			if (solution != null)
				add(solution);
			else
				log.warn(Constants.WARN_SOLUTIONSPACE_SOLUTION_CANT_CREATE);
		}
	}

	public List<T> getParameters() {
		return values;
	}

	@Override
	public int size() {
		return values.size();
	}

	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return values.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return values.iterator();
	}

	@Override
	public Object[] toArray() {
		return values.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return values.toArray(a);
	}

	@Override
	public boolean add(T e) {
		return values.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return values.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return values.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return values.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return values.retainAll(c);
	}

	@Override
	public void clear() {
		values.clear();
	}

	@Override
	public T get(int index) {
		return values.get(index);
	}

	@Override
	public T set(int index, T element) {
		return values.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		values.add(index, element);
	}

	@Override
	public T remove(int index) {
		return values.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return values.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return values.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return values.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return values.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return values.subList(fromIndex, toIndex);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		values.addAll(index, c);
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		values.addAll(c);
		return true;
	}

	public Element createXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(XML_ENTITY);
			for (T value : values) {
				rootElement
						.appendChild(doc.importNode(value.createXML(), true));
			}
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
