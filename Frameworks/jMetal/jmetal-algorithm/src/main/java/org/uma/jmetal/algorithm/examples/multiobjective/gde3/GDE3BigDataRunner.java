package org.uma.jmetal.algorithm.examples.multiobjective.gde3;

import java.util.List;
import org.uma.jmetal.algorithm.examples.AlgorithmRunner;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3;
import org.uma.jmetal.algorithm.multiobjective.gde3.GDE3Builder;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.selection.impl.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.problem.multiobjective.cec2015OptBigDataCompetition.BigOpt2015;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

/**
 * Class for configuring and running the GDE3 algorithm for solving a problem of the Big
 * Optimization competition at CEC2015
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
public class GDE3BigDataRunner {

  /**
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    DoubleProblem problem = new BigOpt2015("D12");

    double cr = 1.5;
    double f = 0.5;
    var crossover = new DifferentialEvolutionCrossover(cr, f,
        DifferentialEvolutionCrossover.DE_VARIANT.RAND_1_BIN);

    var selection = new DifferentialEvolutionSelection();

    var algorithm = new GDE3Builder(problem)
        .setCrossover(crossover)
        .setSelection(selection)
        .setMaxEvaluations(250000)
        .setPopulationSize(100)
        .build();

    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
        .execute();

    List<DoubleSolution> population = ((GDE3) algorithm).result();
    long computingTime = algorithmRunner.getComputingTime();

    new SolutionListOutput(population)
        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
        .print();

    JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");
    JMetalLogger.logger.info("Objectives values have been written to file FUN.tsv");
    JMetalLogger.logger.info("Variables values have been written to file VAR.tsv");
  }
}
