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
		this(solution.size());
		for (int index = 0; index < size(); index++)
			values[index] = solution.get(index);
	}

	public void setValue(int dimensionIndex, Double value) {
		values[dimensionIndex] = value;
	}

	public Double get(int index) {
		return values[index];
	}

	protected Double[] getValues() {
		return values;
	}

	public int size() {
		return values.length;
	}

	@Override
	public Solution createCopy() {
		return new ArraySolution(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < size() - 1; i++)
			builder.append(formatter.format(values[i]) + ", ");
		builder.append(formatter.format(values[size() - 1]));
		return builder.toString();
	}

	@Override
	public void set(Object value, int index) {
		values[index] = (Double) value;
	}
}
