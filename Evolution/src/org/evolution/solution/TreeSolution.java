package org.evolution.solution;

import java.util.LinkedList;
import java.util.List;

import org.evolution.algorithm.gp.types.Expression;
import org.evolution.algorithm.structures.Node;

public class TreeSolution<T extends Expression> extends Solution {
	private Node<T> root;
	private List<Node<T>> nodes = new LinkedList<>();
	private Node<T> selected = null;

	public void add(T value) {
		add(value, false);
	}

	/**
	 * P�id�n� nov�ho uzlu k aktu�ln� vybran�mu
	 * 
	 * @param value
	 *            hodnota
	 * @param goDown
	 *            m� se p�i p�id�n� ozna�it aktu�ln� vlo�en�?
	 */
	public void add(T value, boolean goDown) {
		Node<T> node = new Node<T>(value);
		nodes.add(node);

		if (root == null) {
			root = node;
			selected = root;
		} else {
			node.setParent(selected);
			if (goDown)
				selected = node;
		}
		nodes.add(node);
	}

	/**
	 * Z�m�na existuj�c�ho uzlu ve stromu uzlem jin�m.
	 * 
	 * @param value
	 *            nov� uzel
	 * @param index
	 *            index st�vaj�c�ho uzlu
	 */
	public void set(T value, int index) {
		Node<T> selected = nodes.get(index);
		Node<T> parent = selected.getParent();
		List<Node<T>> childs = getChildsOf(selected);

		// odstran�n� star�ho uzlu ze seznamu
		nodes.remove(selected);
		selected.setParent(null); // odstran�n� reference star�ho uzlu na rodi�e

		// vytvo�en� nov�ho uzlu a nastaven� rodi�e na rodi�e p�edka star�ho
		// uzlu
		Node<T> newNode = new Node<T>(value);
		newNode.setParent(parent);

		// p�id�n� nov�ho uzlu do seznamu uzl�
		nodes.add(newNode);

		// v�em potomk�m star�ho uzlu nastav�me rodi�e jako uzel nov�
		for (Node<T> node : childs) {
			node.setParent(newNode);
		}

		// pokud byl star� uzel root, tak ozna�me jako root nov� uzel
		if (selected == root)
			root = newNode;

		// ozna�en�me jako vybran� uzel nov� p�idan� uzel
		selected = newNode;
	}

	/**
	 * Z�m�na star�ho uzlu podle hodnoty a vlo�en� uzlu nov�ho s hodnotou
	 * 
	 * @param value
	 *            nov� hodnota
	 * @param instead
	 *            star� hodnota
	 */
	public void set(T value, T instead) {
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).equals(instead))
				set(value, i);
		}
	}

	/**
	 * Select node with index
	 * 
	 * @param index
	 */
	public void select(int index) {
		Node<T> node = nodes.get(index);
		selected = node;
	}

	/**
	 * Select value in tree
	 * 
	 * @param value
	 */
	public void select(T value) {
		for (Node<T> node : nodes) {
			if (node.getValue().equals(value)) {
				selected = node;
				return;
			}
		}
	}

	/**
	 * Vytvo�en� podstromu z n�jak�ho konkr�tn�ho uzlu ve strom�
	 * 
	 * @param selected
	 *            vybran� uzel ve strom�
	 * @return
	 */
	public TreeSolution<T> getSubTree(Node<T> selected) {
		TreeSolution<T> subTree = new TreeSolution<>();
		subTree.add(selected.getValue(), true);

		// Na ka�d�ho potomka rekurzivn� vol�me vkl�d�n� jeho potomk�
		List<Node<T>> leaves = new LinkedList<>();
		leaves.addAll(subTree.getChildsOf(selected));

		for (int index = 0; index < leaves.size(); index++) {
			addChildOf(this, subTree, leaves.get(index));
		}
		subTree.moveUp();
		return subTree;
	}

	/**
	 * Pro vybran� uzel rekurzivn� vkl�d� potomky tohoto uzlu z p�vodn�ho stromu
	 * 
	 * @param prevTree
	 *            vzorov� strom
	 * @param tree
	 *            generovan� strom
	 * @param parent
	 *            rodi�ovsk� uzel
	 */
	private void addChildOf(TreeSolution<T> prevTree, TreeSolution<T> tree,
			Node<T> parent) {
		prevTree.add(parent.getValue(), true);
		for (Node<T> node : prevTree.getChildsOf(parent)) {
			addChildOf(prevTree, tree, node);
		}
		tree.moveUp();
	}

	/**
	 * Odstran�n� uzlu ze stromu s vybranou hodnotou
	 * 
	 * @param value
	 *            vybran� hodnota uzlu
	 */
	public void remove(T value, boolean includeChilds) {
		Node<T> toRemove = null;
		for (Node<T> selected : nodes) {
			if (selected.getValue().equals(value)) {
				toRemove = selected;
				break;
			}
		}

		if (toRemove != null) {
			if (includeChilds) {
				// dopsat
			}
			nodes.remove(toRemove);
			// dopsat
		}
	}

	/**
	 * Vrac� seznam uzl�, kter� jsou podstromem, jeho� ko�en je vybran� uzel
	 * 
	 * @param from
	 *            vybran� uzel, jako ko�en podstromu
	 * @return seznam uzl� pro dan� podstrom
	 */
	public List<Node<T>> getNodes(Node<T> from) {
		List<Node<T>> list = new LinkedList<Node<T>>();

		return list;
	}

	private boolean isLeaveNode(Node<T> node) {
		if (nodes.contains(node)) {
			// projdeme v�echny uzly
			for (Node<T> active : nodes) {
				// pokud n�jak� uzel ze seznamu odkazuje na tento uzel, tak nen�
				// list
				if (active.getParent() != null
						&& active.getParent().equals(node))
					return false;
			}
			// pokud ��dn� uzel neodkazuje na vybran� uzel, jde o list
			return true;
		}
		// pokud vybran� uzel nen� v seznamu uzl� stromu, tak to nen� list
		return false;
	}

	/**
	 * Odstran�n� potomk� uzlu s vybranou hodnotou
	 * 
	 * @param selectedIndex
	 *            vybran� index uzlu, jeho potomky chceme odstranit
	 */
	public void removeChilds(int selectedIndex) {

	}

	/**
	 * Vrac�me seznam uzl�, kter� jsou v cest� z vybran�ho do c�lov�ho uzlu,
	 * krom� c�lov�ho uzlu. ten u� v seznamu nen�. Metoda vr�t� pr�zdn� seznam,
	 * pokud neexistuje cesta z po��te�n�ho do koncov�ho uzlu v tomto sm�ru nebo
	 * pokud je po��te�n� uzel tak� c�lov�m uzlem.
	 * 
	 * @param from
	 *            vybran� po��te�n� uzel
	 * @param to
	 *            vybran� koncov� uzel
	 * @return seznam s uzly v cest� od po��te�n�ho uzlu ke koncov�mu uzlu,
	 *         krom� koncov�ho uzlu
	 */
	private List<Node<T>> getNodesPath(Node<T> from, Node<T> to) {
		Node<T> actual = from;
		List<Node<T>> list = new LinkedList<Node<T>>();

		// dokud m� aktu�ln� uzel rodi�e a z�rove� je aktu�ln� uzel jin� ne�
		// c�lov�, ozna� jako aktu�ln� rodi�e a p�idej uzel do seznamu
		while (actual.getParent() != null && !actual.equals(to)) {
			list.add(actual.getParent()); // p�idej rodi�e aktu�ln�ho uzlu do
											// seznamu
			actual = actual.getParent(); // ozna� aktu�ln� jako rodi� aktu�ln�ho
		}

		// aktu�ln� uzel je kone�n� nebo je rodi� aktu�ln�ho null
		if (actual.equals(to))
			return list;

		// pokud jsme se nedostali ke kone�n�mu uzlu, tak vrac�me pr�zdn� seznam
		list.clear();
		return list;
	}

	/**
	 * Select parent node of actual selected node
	 */
	public void moveUp() {
		if (selected.getParent() != null)
			selected = selected.getParent();
	}

	public int size() {
		return nodes.size();
	}

	/**
	 * Depht of tree (from actual node)
	 * 
	 * @return
	 */
	public int getDepth() {
		int depth = 0;
		Node<T> actual = selected;
		while (!actual.isRoot()) {
			depth++;
			actual = actual.getParent();
		}
		return depth;
	}

	/**
	 * Return depth of value in tree
	 * 
	 * @param value
	 * @return
	 */
	public int getDepth(T value) {
		for (Node<T> node : nodes)
			if (node.getValue().equals(value)) {
				Node<T> selected = node;
				int depth = 0;
				while (selected.getParent() != null) {
					depth++;
					selected = selected.getParent();
				}
				return depth;
			}
		return -1;
	}

	public List<Node<T>> getLeaves() {
		List<Node<T>> list = new LinkedList<Node<T>>();
		for (Node<T> node : nodes) {
			if (isLeaveNode(node))
				list.add(node);
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(selected.toString());
		return result.toString();
	}

	public void setNodes(List<Node<T>> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Set node as root
	 * 
	 * @param root
	 */
	public void setRoot(Node<T> root) {
		this.root = root;
	}

	/**
	 * Select node
	 * 
	 * @param node
	 */
	public void setSelected(Node<T> node) {
		this.selected = node;
	}

	/**
	 * Return all nodes from tree
	 * 
	 * @return
	 */
	public List<Node<T>> getNodes() {
		return nodes;
	}

	/**
	 * return node with index of list
	 */
	public Node<T> get(int index) {
		return nodes.get(index);
	}

	/**
	 * Return list of nodes, which is direct child of selected node
	 * 
	 * @param selected
	 *            node, whose child nodes find
	 * @return list of selected node
	 */
	public List<Node<T>> getChildsOf(Node<T> selected) {
		List<Node<T>> list = new LinkedList<Node<T>>();
		for (Node<T> node : nodes) {
			if (node.getParent().equals(selected))
				list.add(node);
		}
		return list;
	}

	@Override
	public TreeSolution<T> createCopy() {
		TreeSolution<T> newTree = new TreeSolution<T>();
		List<Node<T>> list = new LinkedList<Node<T>>();
		int rootIndex = -1;
		int selectedIndex = -1;
		for (int i = 0; i < size(); i++) {
			Node<T> sel = nodes.get(i);
			list.add((Node<T>) sel.createCopy());
			if (sel.equals(root))
				rootIndex = i;
			if (sel.equals(selected))
				selectedIndex = i;
		}
		newTree.setNodes(list);
		newTree.setRoot(list.get(rootIndex));
		newTree.setSelected(list.get(selectedIndex));

		for (int i = 0; i < nodes.size(); i++) {
			Node<T> active = nodes.get(i);
			for (Node<T> child : active.getChilds()) {

				// najdi, na jak�m indexu je child
				for (int k = 0; k < nodes.size(); k++) {
					if (nodes.get(k).equals(child)) {
						list.get(i).add(list.get(k));
						list.get(k).setParent(list.get(i));
						break;
					}
				}

			}
		}
		return newTree;
	}

	@Override
	public void set(Object value, int index) {
	}

	/**
	 * Odstran�n� v�ech potomk� dan�ho uzlu a poduzl�
	 * 
	 * @param tree
	 *            strom
	 */
	public void removeChilds(TreeSolution<T> tree) {
		for (Node<T> node : childs) {
			// rekurzivn� odstra�ov�n� potomk� uzlu
			node.removeChilds(tree);
			tree.getNodes().remove(node);
		}
		setParent(null);
	}

	public void removeChild(Node<T> node, TreeSolution<T> tree) {
		if (childs.contains(node)) {
			// nejprve odstran�me v�echny poduzly odstran�n�ho uzlu
			node.removeChilds(tree);
			// potom odstran�me samotn� uzel
			childs.remove(node);
			node.setParent(null);

			tree.remove(node);
		}
	}
	
	public List<T>
}
