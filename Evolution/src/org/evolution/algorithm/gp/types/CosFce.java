package org.evolution.algorithm.gp.types;

public class CosFce extends NumericExpression {

	public CosFce() {
		super(1);
		setName("cos()");
	}

	@Override
	public int getMaxChilds() {
		return 1;
	}

	@Override
	public String toString() {
		return "cos(" + getResult() + ")";
	}

	@Override
	public String prefixMode() {
		return null;
	}

	@Override
	public Double getResult() {
		return Math.cos(getValue(0));
	}

	@Override
	public Expression createCopy() {
		return null;
	}
}
