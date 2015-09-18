package org.evolution.solution.space.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.evolution.solution.space.StructureSpace;

public class ModelConfiguration<T extends ITerminalExpression<T>> {
	private List<Double> values;
	private static Logger log = Logger.getLogger(ModelConfiguration.class);

	protected void setValue(int index, Double value) {
		values.set(index, value);
	}

	/*
	 * public static ModelConfiguration createConfiguration(Double[] values,
	 * ProgrammingSpace<?> space) { ModelConfiguration model = new
	 * ModelConfiguration(space); try { if (values == null) {
	 * log.error("Values of configuration model are null"); return null; } else
	 * if (values.length != space.getDimension()) {
	 * log.error("Number of values is not same as dimension of problem"); return
	 * null; } for (int i = 0; i < values.length; i++) model.setValue(i,
	 * values[i]); return model; } catch (Exception exc) { log.error(exc); }
	 * return null; }
	 */

	/*
	 * public static ModelConfiguration createRandomConfiguration(
	 * ProgrammingSpace<?> space) { ModelConfiguration model = new
	 * ModelConfiguration(space); int dimension = space.getDimension(); try {
	 * 
	 * for (int i = 0; i < dimension; i++) { Bounds b =
	 * space.getVariableBound(i); Double val = b.getRandomValue();
	 * model.setValue(i, val); } return model; } catch (Exception exc) {
	 * log.error(exc); } return null; }
	 */
	public static List<ModelConfiguration<?>> createConfigurations(int count,
			StructureSpace space) {
		List<ModelConfiguration<?>> models = new LinkedList<>();
		int dimension = space.getDimension();
		for (int i = 0; i < count; i++) {
			Double[] values = new Double[dimension];
			for (int dim = 0; dim < dimension; dim++) {

			}
		}
		return models;
	}
}
