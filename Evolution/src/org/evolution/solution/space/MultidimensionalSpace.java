package org.evolution.solution.space;

import java.util.Random;

import org.apache.log4j.Logger;
import org.evolution.algorithm.exception.SolutionSpaceException;
import org.evolution.algorithm.util.Constants;
import org.evolution.solution.ArraySolution;

public class MultidimensionalSpace extends SolutionSpace<ArraySolution> {
	private Logger log = Logger.getLogger(getClass());
	private Random random = new Random();
	private Bounds[] bounds;

	public MultidimensionalSpace(int dimension) {
		bounds = new Bounds[dimension];
		for (int i = 0; i < dimension; i++) {
			bounds[i] = new Bounds(this);
		}
	}

	public int getDimension() {
		return bounds.length;
	}

	public Bounds get(int index) {
		return bounds[index];
	}

	public void setBounds(int index, Bounds bounds) {
		this.bounds[index] = bounds;
	}

	public Double getRandomValue(int dimensionIndex)
			throws SolutionSpaceException {
		return bounds[dimensionIndex].getRandomValue();
	}

	@Override
	public ArraySolution getRandomSolution() {
		ArraySolution solution = new ArraySolution();
		try {
			for (int i = 0; i < getDimension(); i++) {
				Bounds bound = get(i);
				Double randomValue = bound.getRandomValue();
				solution.setValue(i, randomValue);
			}
			return solution;
		} catch (SolutionSpaceException exception) {
			log.error(exception);
		}
		return null;
	}

	public class Bounds {
		private Double minValue = null;
		private Double maxValue = null;
		private MultidimensionalSpace solutionSpace;

		public Bounds(MultidimensionalSpace solutionSpace) {
			this.solutionSpace = solutionSpace;
		}

		public Bounds(MultidimensionalSpace solutionSpace, Double minValue,
				Double maxValue) {
			this.solutionSpace = solutionSpace;
			if (minValue != null)
				this.minValue = minValue;
			if (maxValue != null)
				this.maxValue = maxValue;
		}

		public Double getMinValue() {
			return minValue;
		}

		public Double getMaxValue() {
			return maxValue;
		}

		public void setMinValue(Double minValue) {
			if (minValue != null)
				this.minValue = minValue;
		}

		public void setMaxValue(Double maxValue) {
			if (maxValue != null)
				this.maxValue = maxValue;
		}

		public Double getRandomValue() throws SolutionSpaceException {
			if (minValue == null || maxValue == minValue)
				throw new SolutionSpaceException(solutionSpace,
						Constants.ERROR_MULDIM_DIMENSION_RANGE_NOT_SET);
			return random.nextDouble() * (maxValue - minValue) + minValue;
		}

		@Override
		public String toString() {
			return "Multidimensional space";
		}
	}
}
