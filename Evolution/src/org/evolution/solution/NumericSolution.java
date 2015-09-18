package org.evolution.solution;

public class NumericSolution extends ArraySolution<Number> {

	public NumericSolution(int dimension) {
		super(dimension);
	}

	@Override
	public Solution createCopy() {
		NumericSolution solution = new NumericSolution(size());
		for (int i = 0; i < size(); i++) {
			solution.set(get(i), i);
		}
		return solution;
	}

	@Override
	public void set(Object value, int index) {
		if (value instanceof Number)
			super.setValue(index, (Number) value);
	}
}
