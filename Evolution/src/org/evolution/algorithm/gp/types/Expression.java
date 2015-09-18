package org.evolution.algorithm.gp.types;

import org.evolution.algorithm.util.CopyFor;

public abstract class Expression implements CopyFor<Expression> {
	private String name;
	private int arita;

	public Expression(int childs) {
		this.arita = childs;
	}

	/**
	 * Return number information that this is terminal symbol, which is
	 * determined by number of maximum children. It can by retype for
	 * ITerminalExpression interface
	 * 
	 * @return true - if it is terminal symbol, otherwise false
	 */
	public boolean isTerminal() {
		return getMaxChilds() == 0 ? true : false;
	}

	/**
	 * Set name of expression
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get name of expression
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Maximum number of child for this expression
	 * 
	 * @return number of child expression
	 */
	public abstract int getMaxChilds();

	/**
	 * Is this expression empty? It is not still defined (set)
	 * 
	 * @return true - if it´s not set, otherwise false
	 */
	public boolean isNull() {
		if (this instanceof NullExpression)
			return true;
		return false;
	}

	/**
	 * Function arity. Number of arguments in function
	 * 
	 * @return Number of arguments in function
	 */
	public int getArita() {
		return arita;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("(%s)", getName()));
		return sb.toString();
	}

	// public abstract Expression createCopy();

	public abstract String prefixMode();
}
