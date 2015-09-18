package org.evolution.algorithm.gp.types;

public class SinFce extends NumericExpression {

	public SinFce() {
		super(1);
		setName("sin()");
	}

	@Override
	public int getMaxChilds() {
		return 1;
	}

	@Override
	public String toString() {
		return "sin(" + getValue(0) + ")";
	}

	@Override
	public Expression createCopy() {
		return null;
	}

	@Override
	public String prefixMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getResult() {
		return Math.sin(getValue(0));
	}

}
