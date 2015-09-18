package org.evolution.test;

import static org.junit.Assert.fail;

import org.evolution.algorithm.ga.NumericGeneticAlgorithm;
import org.junit.Test;

public class GeneticAlgorithmTest {

	private NumericGeneticAlgorithm algorithm = new NumericGeneticAlgorithm();

	@Test
	public void defaultStart() {
		try {
			algorithm.start();
		} catch (Exception exception) {
			fail("Chyba bìhu algoritmu");
		}
	}
}
