package org.evolution.algorithm.gp.types;

public class TanFce extends NumericExpression {

	public TanFce() {
		super(1);
		setName("tan()");
	}

	@Override
	public int getMaxChilds() {
		return 1;
	}

	@Override
	public String toString() {
		return "tan(" + getValue(0) + ")";
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
		return Math.tan(getValue(0));
	}
}
