package org.evolution;

import java.util.Hashtable;

import org.evolution.algorithm.gp.FunctionGeneticProgramming;
import org.evolution.algorithm.gp.GeneticProgramming;
import org.evolution.algorithm.gp.types.Constant;
import org.evolution.algorithm.gp.types.Expression;
import org.evolution.algorithm.gp.types.KratFce;
import org.evolution.algorithm.gp.types.NumericExpression;
import org.evolution.algorithm.gp.types.PlusFce;
import org.evolution.algorithm.gp.types.SinFce;
import org.evolution.algorithm.gp.types.VarExpr;
import org.evolution.algorithm.population.Population;
import org.evolution.algorithm.state.GeneticAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmState;
import org.evolution.algorithm.state.OptimizeAlgorithmStateListener;
import org.evolution.function.cross.StructureCrossFunction;
import org.evolution.function.mutate.StructureMutation;
import org.evolution.function.objective.ApproximateFunctionProblem;
import org.evolution.function.select.FunctionRouleteWheelSelect;
import org.evolution.solution.SExpressionSolution;
import org.evolution.solution.space.StructureSpace;

public class Main {

	private GeneticProgramming<NumericExpression> algorithm = new FunctionGeneticProgramming();

	public static void main(String[] args) {
		Main start = new Main();
		start.startGeneticAlgorithm();
	}

	public void startGeneticAlgorithm() {
		// Cross function
		StructureCrossFunction<NumericExpression> crossFce = new StructureCrossFunction<NumericExpression>();
		// crossFce.setCrossProbability(0.8);
		algorithm.setCrossFunction(crossFce);

		// Mutate function
		StructureMutation<NumericExpression> mutateFce = new StructureMutation<NumericExpression>();
		// mutateFce.setMutatedProbability(0.05);
		algorithm.setMutateFunction(mutateFce);

		// Select function
		FunctionRouleteWheelSelect selectFce = new FunctionRouleteWheelSelect();
		algorithm.setSelectFunction(selectFce);

		// Solution Space
		ApproximateFunctionProblem objectiveFce = new ApproximateFunctionProblem(
				solutionSpace);
		SExpressionSolution function = new SExpressionSolution(3);
		Expression root = new KratFce();
		Expression sin = new SinFce();
		Expression plus = new PlusFce();
		Constant deset = new Constant(10.0);
		VarExpr x = new VarExpr("x");
		x.setMinValue(-2.0);
		x.setMaxValue(1.0);
		VarExpr y = new VarExpr("y");
		y.setMinValue(-2.0);
		y.setMaxValue(5.0);
		sin.add(x);
		plus.add(deset);
		plus.add(sin);
		root.add(y);
		root.add(plus);
		function.setRoot(root);
		objectiveFce.setFunction(function);

		StructureSpace solutionSpace = new StructureSpace();

		solutionSpace.setObjectiveFunction(objectiveFce);
		algorithm.setSolutionSpace(solutionSpace);

		// Population
		Population<SExpressionSolution> population = new Population<SExpressionSolution>();
		population.createRandomPopulation(10, solutionSpace);
		algorithm.setPopulation(population);

		algorithm.setMaxIteration(200);
		algorithm
				.addStateListener(new OptimizeAlgorithmStateListener<SExpressionSolution>() {
					public void handleStateChanged(
							OptimizeAlgorithmState<SExpressionSolution> eventCode) {
						if (eventCode.getState() == OptimizeAlgorithmState.STARTED) {
							System.out.println("START [POPULACE = "
									+ eventCode.getAlgorithm().getPopulation()
											.size()
									+ "][ITERACI = "
									+ eventCode.getAlgorithm()
											.getMaxIteration() + "]");
						} else if (eventCode.getState() == OptimizeAlgorithmState.ENDED) {
							System.out.println("END [BEST = "
									+ eventCode.getAlgorithm()
											.getBestSolution()
											.getFunctionValue() + "]");
							System.out.println("HISTOGRAM:");
							Hashtable<Integer, Double> histogram = eventCode
									.getAlgorithm().getHistogram();
							for (int i = 1; i <= histogram.size(); i++) {
								System.out.println(i + " = " + histogram.get(i));
							}
						} else if (eventCode.getState() == OptimizeAlgorithmState.ITERATION_CHANGED) {
							System.out.println("\tGENERATION ("
									+ eventCode.getAlgorithm()
											.getActualIteration()
									+ ") [POPULACE = "
									+ eventCode.getAlgorithm().getPopulation()
											.size()
									+ "][BEST = "
									+ eventCode.getAlgorithm()
											.getBestSolution()
											.getFunctionValue() + "]");
						} else if (eventCode.getState() == GeneticAlgorithmState.SELECT_END) {
							System.out.println("\t\tSELECT");
						} else if (eventCode.getState() == GeneticAlgorithmState.CROSS_END) {
							System.out.println("\t\tCROSS");
						} else if (eventCode.getState() == GeneticAlgorithmState.MUTATE_END) {
							System.out.println("\t\tMUTATE");
						} else if (eventCode.getState() == GeneticAlgorithmState.ELITISMUS_END) {
							System.out.println("\t\tELITISMUS");
						}
					}
				});

		algorithm.start();
		/*
		 * try { Document doc = XMLManager.createDocument(); Element elem =
		 * algorithm.createXML(); Element root = doc.createElement("config");
		 * root.appendChild(doc.importNode(elem, true)); doc.appendChild(root);
		 * TransformerFactory transformerFactory = TransformerFactory
		 * .newInstance(); Transformer transformer =
		 * transformerFactory.newTransformer(); DOMSource source = new
		 * DOMSource(doc); StreamResult result = new StreamResult(new
		 * File("file.xml"));
		 * 
		 * transformer.transform(source, result);
		 * 
		 * System.out.println("File saved!"); System.out.println(elem); } catch
		 * (Exception exc) { exc.printStackTrace(); }
		 */
	}
}
