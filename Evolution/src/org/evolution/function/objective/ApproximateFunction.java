package org.evolution.function.objective;

import org.evolution.solution.FunctionTree;
import org.evolution.solution.space.Space;

public class ApproximateFunction extends ObjectiveFunction<FunctionTree> {
	private FunctionTree function;
	private int evalutionCount = 0;
	private Space<FunctionTree> space;

	public FunctionTree getFunction() {
		return function;
	}

	public void setFunction(FunctionTree function) {
		this.function = function;
	}

	public int getEvalutionCount() {
		return evalutionCount;
	}

	public void setEvalutionCount(int evalutionCount) {
		this.evalutionCount = evalutionCount;
	}

	@Override
	public Double calculateFunctionValue(FunctionTree solution) {

		return null;
	}

	@Override
	public void setSolutionSpace(Space<FunctionTree> solutionSpace) {
		this.space = solutionSpace;
	}

	@Override
	public Space<FunctionTree> getSolutionSpace() {
		return space;
	}
}
