package org.evolution.algorithm.structures;

import org.evolution.algorithm.gp.types.Expression;
import org.evolution.algorithm.util.CopyFor;
import org.evolution.solution.TreeSolution;

public class Node<T extends Expression> implements CopyFor<Node<T>> {
	private T value;
	private Node<T> parent;

	/**
	 * Vytvoøení nového uzlu. Každý uzel je jedineèný
	 * 
	 * @param value
	 *            hodnota
	 */
	public Node(T value) {
		this.value = value;
	}

	/**
	 * Odstranìní potomkù uzlu a odkazù na jejich rodièe
	 */
	public void clearChilds(TreeSolution<T> tree) {
		for (Node<T> node : tree.getChildsOf(this)) {
			node.clearChilds(tree);
			tree.remove(node);
			node.setParent(null);
		}
	}

	/**
	 * Vrácení hodnoty
	 * 
	 * @return
	 */
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public boolean isRoot() {
		return parent == null ? true : false;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("(" + value.toString() + ")");
		return result.toString();
	}

	@Override
	public Node<T> createCopy() {
		T value = (T) getValue().createCopy();
		Node<T> newNode = new Node<T>(value);
		return newNode;
	}
}
