package org.evolution.algorithm.gp.types;

public class KratFce extends NumericExpression {

	public KratFce() {
		super(2);
		setName("*");
	}

	@Override
	public int getMaxChilds() {
		return 2;
	}

	@Override
	public String toString() {
		return "(" + getValue(0) + "*" + getValue(1) + ")";
	}

	@Override
	public String prefixMode() {
		return null;
	}

	@Override
	public Double getResult() {
		return getValue(0);
	}

	@Override
	public Expression createCopy() {
		// TODO Auto-generated method stub
		return null;
	}
}
