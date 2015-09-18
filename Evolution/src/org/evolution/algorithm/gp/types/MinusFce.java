package org.evolution.algorithm.gp.types;

public class MinusFce extends NumericExpression {

	public MinusFce() {
		super(2);
		setName("-");
	}

	@Override
	public int getMaxChilds() {
		return 2;
	}

	@Override
	public String toString() {
		return "(" + getValue(0) + "-" + getValue(1) + ")";
	}

	@Override
	public String prefixMode() {
		return null;
	}

	@Override
	public Double getResult() {
		return getValue(0) - getValue(1);
	}

	@Override
	public Expression createCopy() {
		return null;
	}

}
