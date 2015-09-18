package org.evolution.solution.space.util;

import java.util.Random;

import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.algorithm.util.Constants;
import org.evolution.solution.space.Space;

public class Bounds<T extends Number> {
	private T minValue = null;
	private T maxValue = null;
	private Space<?> solutionSpace;
	private static Random random = new Random();

	public Bounds(Space<?> solutionSpace) {
		this.solutionSpace = solutionSpace;
	}

	public Bounds(Space<?> solutionSpace, T minValue, T maxValue) {
		this.solutionSpace = solutionSpace;
		if (minValue != null)
			this.minValue = minValue;
		if (maxValue != null)
			this.maxValue = maxValue;
	}

	public T getMinValue() {
		return minValue;
	}

	public T getMaxValue() {
		return maxValue;
	}

	public void setMinValue(T minValue) {
		if (minValue != null)
			this.minValue = minValue;
	}

	public void setMaxValue(T maxValue) {
		if (maxValue != null)
			this.maxValue = maxValue;
	}

	public Number getRandomValue() throws SolutionSpaceException {
		if (minValue == null || maxValue == minValue)
			throw new SolutionSpaceException(solutionSpace,
					Constants.ERROR_MULDIM_DIMENSION_RANGE_NOT_SET);
		if (minValue instanceof Double) {
			return (Double) random.nextDouble()
					* (maxValue.doubleValue() - minValue.doubleValue())
					+ minValue.doubleValue();
		} else if (minValue instanceof Integer) {
			double cislo = (random.nextDouble()
					* (maxValue.doubleValue() - minValue.doubleValue()) + minValue
					.doubleValue());
			return Integer.valueOf((int) Math.round(cislo));
		}
		return null;
	}

	@Override
	public String toString() {
		return "Bound[" + getMinValue() + "," + getMaxValue() + "]";
	}
}
