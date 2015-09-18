package org.evolution.function.mutate;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.evolution.algorithm.gp.types.Expression;
import org.evolution.solution.TreeSolution;
import org.evolution.solution.space.Space;
import org.evolution.solution.space.StructureSpace;

public class StructureMutation<T extends Expression> extends
		MutateFunction<TreeSolution<T>> {

	private Random random = new Random();

	@Override
	public List<TreeSolution<T>> mutate(List<TreeSolution<T>> mutatedSolutions,
			Space<TreeSolution<T>> solutionSpace) {

		StructureSpace<T> space = (StructureSpace<T>) solutionSpace;
		List<Expression> structures = new LinkedList<Expression>();

		structures.addAll(space.getFunctions());
		structures.addAll(space.getTerminals());
		List<TreeSolution<T>> result = new LinkedList<TreeSolution<T>>();
		for (int i = 0; i < mutatedSolutions.size(); i++) {
			TreeSolution<T> sol = mutatedSolutions.get(i);
			sol.setNull(random.nextInt(sol.size()));

			while (true) {
				List<T> leaves = sol.getNullExpressions();
				// is no leaves
				if (leaves.size() == 0)
					break; // than stop process of generating

				T expr = (T) leaves.get(random.nextInt(leaves.size()));
				if (space.isComplete()) {
					int dep = sol.getDepth(expr);
					if (sol.getDepth(expr) < space.getMaxDepth()) {
						sol.setExpression(
								(T) space
										.getFunctions()
										.get(random.nextInt(space
												.getFunctions().size()))
										.getInstance(), expr);
					} else {
						sol.setExpression(
								(T) space
										.getTerminals()
										.get(random.nextInt(space
												.getTerminals().size()))
										.getInstance(), expr);
					}
				} else {
					if (expr.getDepth() == space.getMaxDepth()) {
						sol.setExpression(
								(T) space
										.getTerminals()
										.get(random.nextInt(space
												.getTerminals().size()))
										.getInstance(), expr);
					} else
						sol.setExpression(
								(T) structures.get(
										random.nextInt(structures.size()))
										.getInstance(), expr);
				}
			}
			result.add(sol);
		}
		return result;
	}

}
