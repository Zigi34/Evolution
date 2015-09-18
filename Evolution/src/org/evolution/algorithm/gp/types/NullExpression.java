package org.evolution.algorithm.gp.types;

public class NullExpression extends NumericExpression {

	public NullExpression() {
		super(0);
		setName("null");
	}

	@Override
	public int getMaxChilds() {
		return 0;
	}

	@Override
	public Expression createCopy() {
		NullExpression item = new NullExpression();
		return item;
	}

	@Override
	public String prefixMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getResult() {
		return null;
	}
}
