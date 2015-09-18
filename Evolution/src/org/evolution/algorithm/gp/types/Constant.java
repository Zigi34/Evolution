package org.evolution.algorithm.gp.types;

public abstract class Constant extends NumericExpression {

	public Constant(Double value) {
		super(0);
		setValue(value, 0);
	}

	@Override
	public int getMaxChilds() {
		return 0;
	}

	@Override
	public String toString() {
		return "(" + getValue(0) + ")";
	}

	@Override
	public abstract Expression createCopy();
}
