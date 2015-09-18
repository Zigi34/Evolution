package org.evolution.function.cross;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.algorithm.gp.types.Expression;
import org.evolution.algorithm.structures.Node;
import org.evolution.solution.TreeSolution;

public class StructureCrossFunction<T extends Expression> extends
		CrossFunction<TreeSolution<T>> {

	private Random random = new Random();

	@Override
	public List<TreeSolution<T>> cross(List<TreeSolution<T>> inputSolutions) {
		List<TreeSolution<T>> result = new LinkedList<TreeSolution<T>>();
		for (int index = 1; index < inputSolutions.size(); index += 2) {

			if (random.nextDouble() < getCrossProbability()) {
				TreeSolution<T> solution1 = (TreeSolution<T>) inputSolutions
						.get(index).createCopy();
				TreeSolution<T> solution2 = (TreeSolution<T>) inputSolutions
						.get(index - 1).createCopy();

				int length = solution1.size();
				int index1 = random.nextInt(length - 1) + 1;

				for (int j = index1; j < length; j++) {
					Node<T> itemObject1 = solution1.get(j);
					Node<T> itemObject2 = solution2.get(j);

					Node<T> prev1 = itemObject1.getParent();
					Node<T> prev2 = itemObject2.getParent();

					int child1 = prev1.getChild().indexOf(itemObject1);
					int child2 = prev2.getChild().indexOf(itemObject2);

					prev1.getChild().set(child1, itemObject2);
					itemObject2.setParent(prev1);

					prev2.getChild().set(child2, itemObject1);
					itemObject1.setParent(prev2);
				}

				result.add(solution1);
				result.add(solution2);
			}
		}
		return result;
	}

}
