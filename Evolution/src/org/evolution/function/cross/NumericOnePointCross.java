package org.evolution.function.cross;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.solution.ArraySolution;

/**
 * Generic one point cross function which is applied on array of values. It is
 * selected two solution from head and randomly generated cross index.
 * 
 * @author Zdenek Gold
 *
 * @param <T>
 *            Type of class, which is inherited from NumericSolution class
 */
public class NumericOnePointCross extends CrossFunction<ArraySolution<?>> {

	private Random random = new Random();

	@SuppressWarnings("unchecked")
	public List<ArraySolution<?>> cross(List<ArraySolution<?>> inputSolutions) {
		List<ArraySolution<?>> result = new LinkedList<ArraySolution<?>>();
		for (int index = 1; index < inputSolutions.size(); index += 2) {

			if (random.nextDouble() < getCrossProbability()) {
				ArraySolution solution1 = (ArraySolution) inputSolutions.get(
						index).createCopy();
				ArraySolution solution2 = (ArraySolution) inputSolutions.get(
						index - 1).createCopy();

				int length = solution1.size();
				int index1 = random.nextInt(length - 1) + 1;

				for (int j = 0; j < index1; j++) {
					Object value1 = solution1.get(j);
					Object value2 = solution2.get(j);

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
