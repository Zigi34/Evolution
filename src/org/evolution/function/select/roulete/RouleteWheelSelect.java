package org.evolution.function.select.roulete;

import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.population.Population;
import org.evolution.function.select.SelectFunction;
import org.evolution.solution.Solution;

public class RouleteWheelSelect<T extends Solution> implements
		SelectFunction<T> {

	@Override
	public List<T> select(double probability, Population population) {
		List<T> list = new LinkedList<T>();
		return list;
	}

	@Override
	public List<T> select(int count, Population population) {
		// TODO Auto-generated method stub
		return null;
	}

}
