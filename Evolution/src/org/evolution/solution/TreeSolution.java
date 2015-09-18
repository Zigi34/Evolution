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
	 * Pøidání nového uzlu k aktuálnì vybranému
	 * 
	 * @param value
	 *            hodnota
	 * @param goDown
	 *            má se pøi pøidání oznaèit aktuálnì vložený?
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
	 * Zámìna existujícího uzlu ve stromu uzlem jiným.
	 * 
	 * @param value
	 *            nový uzel
	 * @param index
	 *            index stávajícího uzlu
	 */
	public void set(T value, int index) {
		Node<T> selected = nodes.get(index);
		Node<T> parent = selected.getParent();
		List<Node<T>> childs = getChildsOf(selected);

		// odstranìní starého uzlu ze seznamu
		nodes.remove(selected);
		selected.setParent(null); // odstranìní reference starého uzlu na rodièe

		// vytvoøení nového uzlu a nastavení rodièe na rodièe pøedka starého
		// uzlu
		Node<T> newNode = new Node<T>(value);
		newNode.setParent(parent);

		// pøidání nového uzlu do seznamu uzlù
		nodes.add(newNode);

		// všem potomkùm starého uzlu nastavíme rodièe jako uzel nový
		for (Node<T> node : childs) {
			node.setParent(newNode);
		}

		// pokud byl starý uzel root, tak oznaème jako root nový uzel
		if (selected == root)
			root = newNode;

		// oznaèeníme jako vybraný uzel novì pøidaný uzel
		selected = newNode;
	}

	/**
	 * Zámìna starého uzlu podle hodnoty a vložení uzlu nového s hodnotou
	 * 
	 * @param value
	 *            nová hodnota
	 * @param instead
	 *            stará hodnota
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
	 * Vytvoøení podstromu z nìjakého konkrétního uzlu ve stromì
	 * 
	 * @param selected
	 *            vybraný uzel ve stromì
	 * @return
	 */
	public TreeSolution<T> getSubTree(Node<T> selected) {
		TreeSolution<T> subTree = new TreeSolution<>();
		subTree.add(selected.getValue(), true);

		// Na každého potomka rekurzivnì voláme vkládání jeho potomkù
		List<Node<T>> leaves = new LinkedList<>();
		leaves.addAll(subTree.getChildsOf(selected));

		for (int index = 0; index < leaves.size(); index++) {
			addChildOf(this, subTree, leaves.get(index));
		}
		subTree.moveUp();
		return subTree;
	}

	/**
	 * Pro vybraný uzel rekurzivnì vkládá potomky tohoto uzlu z pùvodního stromu
	 * 
	 * @param prevTree
	 *            vzorový strom
	 * @param tree
	 *            generovaný strom
	 * @param parent
	 *            rodièovský uzel
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
	 * Odstranìní uzlu ze stromu s vybranou hodnotou
	 * 
	 * @param value
	 *            vybraná hodnota uzlu
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
	 * Vrací seznam uzlù, které jsou podstromem, jehož koøen je vybraný uzel
	 * 
	 * @param from
	 *            vybraný uzel, jako koøen podstromu
	 * @return seznam uzlù pro daný podstrom
	 */
	public List<Node<T>> getNodes(Node<T> from) {
		List<Node<T>> list = new LinkedList<Node<T>>();

		return list;
	}

	private boolean isLeaveNode(Node<T> node) {
		if (nodes.contains(node)) {
			// projdeme všechny uzly
			for (Node<T> active : nodes) {
				// pokud nìjaký uzel ze seznamu odkazuje na tento uzel, tak není
				// list
				if (active.getParent() != null
						&& active.getParent().equals(node))
					return false;
			}
			// pokud žádný uzel neodkazuje na vybraný uzel, jde o list
			return true;
		}
		// pokud vybraný uzel není v seznamu uzlù stromu, tak to není list
		return false;
	}

	/**
	 * Odstranìní potomkù uzlu s vybranou hodnotou
	 * 
	 * @param selectedIndex
	 *            vybraný index uzlu, jeho potomky chceme odstranit
	 */
	public void removeChilds(int selectedIndex) {

	}

	/**
	 * Vracíme seznam uzlù, které jsou v cestì z vybraného do cílového uzlu,
	 * kromì cílového uzlu. ten už v seznamu není. Metoda vrátí prázdný seznam,
	 * pokud neexistuje cesta z poèáteèního do koncového uzlu v tomto smìru nebo
	 * pokud je poèáteèní uzel také cílovým uzlem.
	 * 
	 * @param from
	 *            vybraný poèáteèní uzel
	 * @param to
	 *            vybraný koncový uzel
	 * @return seznam s uzly v cestì od poèáteèního uzlu ke koncovému uzlu,
	 *         kromì koncového uzlu
	 */
	private List<Node<T>> getNodesPath(Node<T> from, Node<T> to) {
		Node<T> actual = from;
		List<Node<T>> list = new LinkedList<Node<T>>();

		// dokud má aktuální uzel rodièe a zároveò je aktuální uzel jiný než
		// cílový, oznaè jako aktuální rodièe a pøidej uzel do seznamu
		while (actual.getParent() != null && !actual.equals(to)) {
			list.add(actual.getParent()); // pøidej rodièe aktuálního uzlu do
											// seznamu
			actual = actual.getParent(); // oznaè aktuální jako rodiè aktuálního
		}

		// aktuální uzel je koneèný nebo je rodiè aktuálního null
		if (actual.equals(to))
			return list;

		// pokud jsme se nedostali ke koneènému uzlu, tak vracíme prázdný seznam
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

				// najdi, na jakém indexu je child
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
	 * Odstranìní všech potomkù daného uzlu a poduzlù
	 * 
	 * @param tree
	 *            strom
	 */
	public void removeChilds(TreeSolution<T> tree) {
		for (Node<T> node : childs) {
			// rekurzivní odstraòování potomkù uzlu
			node.removeChilds(tree);
			tree.getNodes().remove(node);
		}
		setParent(null);
	}

	public void removeChild(Node<T> node, TreeSolution<T> tree) {
		if (childs.contains(node)) {
			// nejprve odstraníme všechny poduzly odstranìného uzlu
			node.removeChilds(tree);
			// potom odstraníme samotný uzel
			childs.remove(node);
			node.setParent(null);

			tree.remove(node);
		}
	}
	
	public List<T>
}
