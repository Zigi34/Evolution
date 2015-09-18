package org.evolution.algorithm.exception;

import org.evolution.solution.space.Space;

public class SolutionSpaceException extends Exception {

	private static final long serialVersionUID = -8511725252968374656L;
	private Space<?> solutionSpace;

	public SolutionSpaceException(Space<?> solutionSpace, String message) {
		super(message);
		this.solutionSpace = solutionSpace;
	}

	public Space<?> getSolutionSpace() {
		return solutionSpace;
	}

	public String toString() {
		if (solutionSpace != null)
			return String.format("[SOLUTION SPACE:\"%s\"]: %s",
					solutionSpace.toString(), getMessage());
		return String.format("%s", getMessage());
	};
}
