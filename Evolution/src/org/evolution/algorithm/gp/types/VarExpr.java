package org.evolution.algorithm.gp.types;

public class VarExpr extends NumericExpression {
	private Double minValue;
	private Double maxValue;

	public VarExpr(String name) {
		super(0);
		this.setName(name);
	}

	@Override
	public int getMaxChilds() {
		return 0;
	}

	public Double getMinValue() {
		return minValue;
	}

	public void setMinValue(Double minValue) {
		this.minValue = minValue;
	}

	public Double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Double maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public String prefixMode() {
		// TODO Auto-generated method stub
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
