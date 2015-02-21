package org.evolution.function.cross;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.solution.ArraySolution;

public class GeneticCrossFunction<T extends ArraySolution> extends
		CrossFunction<T> {

	private Random random = new Random();

	@SuppressWarnings("unchecked")
	@Override
	public List<T> cross(List<T> inputSolutions) {
		List<T> result = new LinkedList<T>();
		for (int index = 1; index < inputSolutions.size(); index += 2) {

			if (random.nextDouble() < getCrossProbability()) {
				T solution1 = (T) inputSolutions.get(index).createCopy();
				T solution2 = (T) inputSolutions.get(index - 1).createCopy();

				int length = solution1.size();
				int index1 = random.nextInt(length - 1) + 1;

				for (int j = 0; j < index1; j++) {
					Double value1 = solution1.get(j);
					Double value2 = solution2.get(j);

					solution1.setValue(j, value2);
					solution2.setValue(j, value1);
				}

				result.add(solution1);
				result.add(solution1);
			}
		}
		return result;
	}

}
