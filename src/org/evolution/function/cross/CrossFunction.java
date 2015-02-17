package org.evolution.function.cross;

import java.util.List;

import org.evolution.solution.Solution;

public interface CrossFunction<T extends Solution> {
	List<T> cross(List<T> inputSolutions);
}
