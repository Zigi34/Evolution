package org.evolution.solution;


public abstract class Solution {
	private Double functionValue = null;

	public abstract int getSize();

	public abstract Solution createCopy();

	public abstract Object getValue(int index);

	public abstract void setValue(Object value, int index);

	public void setFunctionValue(Double value) {
		this.functionValue = value;
	}

	public Double getFunctionValue() {
		return functionValue;
	}

	public boolean isEvaluated() {
		return functionValue == null ? false : true;
	}
}
