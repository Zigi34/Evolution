package org.evolution.algorithm.gp.types;

import java.util.LinkedList;
import java.util.List;

public abstract class NumericExpression extends Expression {

	private List<Double> arguments = new LinkedList<Double>();

	public NumericExpression(int childs) {
		super(childs);
	}

	public Double getValue(int index) {
		return arguments.get(index);
	}

	public void setValue(Double value, int index) {
		this.arguments.set(index, value);
	}

	public abstract Double getResult();
}
