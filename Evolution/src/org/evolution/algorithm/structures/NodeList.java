package org.evolution.algorithm.structures;

import java.util.LinkedList;
import java.util.List;

public class NodeList<T> implements TreeStruct<T> {

	private List<TreeStruct<T>> structure = new LinkedList<>();

	public void add(TreeStruct<T> structure) {
		structure.add(structure);
	}

	public List<TreeStruct<T>> getList() {
		return structure;
	}

	@Override
	public boolean isValue() {
		return false;
	}

	@Override
	public boolean isNode() {
		return true;
	}

}
