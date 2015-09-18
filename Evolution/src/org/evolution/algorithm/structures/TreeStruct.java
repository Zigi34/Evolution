package org.evolution.algorithm.structures;

public interface TreeStruct<T> {

	public boolean isValue();

	public boolean isNode();

	public void add(TreeStruct<T> struct);
}
