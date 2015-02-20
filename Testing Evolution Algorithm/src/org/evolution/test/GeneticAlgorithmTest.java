package org.evolution.test;

import static org.junit.Assert.fail;

import org.evolution.algorithm.ga.GeneticAlgorithm;
import org.evolution.solution.ArraySolution;
import org.junit.Test;

public class GeneticAlgorithmTest {

	private GeneticAlgorithm<ArraySolution> algorithm = new GeneticAlgorithm<ArraySolution>();

	@Test
	public void defaultStart() {
		try {
			algorithm.start();
		} catch (Exception exception) {
			fail("Chyba bìhu algoritmu");
		}
	}
}
