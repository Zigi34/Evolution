package org.evolution.algorithm.population;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.evolution.function.objective.ObjectiveFunction;
import org.evolution.solution.Solution;
import org.evolution.solution.space.SolutionSpace;

public class Population implements Collection<Solution>, List<Solution> {

	private List<Solution> values = new LinkedList<Solution>();

	/**
	 * Evaluate and sort population from fitness evaluation individual
	 * 
	 * @param solutions
	 *            all individual of population
	 * @param function
	 *            objective function
	 */
	public static void sortPopulation(List<Solution> solutions,
			ObjectiveFunction<Solution> function) {
		// Seřazení řešení podle fitness od nejlepšího po nejhorší
		Collections.sort(solutions, new Comparator<Solution>() {
			public int compare(Solution o1, Solution o2) {
				double f1 = function.getFunctionValue(o1);
				double f2 = function.getFunctionValue(o2);
				if (f1 > f2)
					return -1; // protože řadíme sestupně a ne vzestupně
								// (default)
				else if (f1 == f2)
					return 0;
				else
					return 1;
			};
		});
	}

	/**
	 * Evaluate and sort population from fitness evaluation individual
	 * 
	 * @param individuals
	 *            all individual of population
	 * @param function
	 *            objective function
	 */
	public static void sortPopulation(Population population,
			ObjectiveFunction<Solution> function) {
		sortPopulation(population.getParameters(), function);
	}

	public static Population createRandomPopulation(int count,
			SolutionSpace<Solution> space) {
		Population pop = new Population();
		for (int i = 0; i < count; i++)
			pop.add(space.getRandomSolution());
		return pop;
	}

	public List<Solution> getParameters() {
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
	public Iterator<Solution> iterator() {
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
	public boolean add(Solution e) {
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
	public boolean addAll(Collection<? extends Solution> c) {
		return values.addAll(c);
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
	public boolean addAll(int index, Collection<? extends Solution> c) {
		return values.addAll(index, c);
	}

	@Override
	public Solution get(int index) {
		return values.get(index);
	}

	@Override
	public Solution set(int index, Solution element) {
		return values.set(index, element);
	}

	@Override
	public void add(int index, Solution element) {
		values.add(index, element);
	}

	@Override
	public Solution remove(int index) {
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
	public ListIterator<Solution> listIterator() {
		return values.listIterator();
	}

	@Override
	public ListIterator<Solution> listIterator(int index) {
		return values.listIterator(index);
	}

	@Override
	public List<Solution> subList(int fromIndex, int toIndex) {
		return values.subList(fromIndex, toIndex);
	}
}
