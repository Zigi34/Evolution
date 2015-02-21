package org.evolution.test;

import static org.junit.Assert.fail;

import org.evolution.algorithm.ga.ArrayGeneticAlgorithm;
import org.junit.Test;

public class GeneticAlgorithmTest {

	private ArrayGeneticAlgorithm algorithm = new ArrayGeneticAlgorithm();

	@Test
	public void defaultStart() {
		try {
			algorithm.start();
		} catch (Exception exception) {
			fail("Chyba bìhu algoritmu");
		}
	}
}
