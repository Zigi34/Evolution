package org.evolution.solution;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ArraySolution extends Solution {
	private Double[] values;
	private NumberFormat formatter = new DecimalFormat("#0.000");

	public ArraySolution() {
		this(2);
	}

	public ArraySolution(int dimension) {
		values = new Double[dimension];
	}

	public ArraySolution(ArraySolution solution) {
		this(solution.getSize());
		for (int index = 0; index < getSize(); index++)
			values[index] = solution.getValue(index);
	}

	public void setValue(int dimensionIndex, Double value) {
		values[dimensionIndex] = value;
	}

	public Double getValue(int index) {
		return values[index];
	}

	protected Double[] getValues() {
		return values;
	}

	public int getSize() {
		return values.length;
	}

	@Override
	public Solution createCopy() {
		return new ArraySolution(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < getSize() - 1; i++)
			builder.append(formatter.format(values[i]) + ", ");
		builder.append(formatter.format(values[getSize() - 1]));
		return builder.toString();
	}

	@Override
	public void setValue(Object value, int index) {
		values[index] = (Double) value;
	}
}
