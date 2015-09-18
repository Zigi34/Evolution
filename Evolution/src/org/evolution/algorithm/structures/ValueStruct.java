package org.evolution.algorithm.structures;

public class ValueStruct<T> implements TreeStruct<T> {

	private T value;

	@Override
	public boolean isValue() {
		return true;
	}

	@Override
	public boolean isNode() {
		return false;
	}

	@Override
	public void add(TreeStruct<T> struct) {
		value = 
	}
}
