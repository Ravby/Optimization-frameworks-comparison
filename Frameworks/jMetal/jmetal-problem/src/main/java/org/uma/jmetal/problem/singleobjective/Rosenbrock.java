package org.uma.jmetal.problem.singleobjective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

public class Rosenbrock extends LoggingDoubleProblem {

  public Rosenbrock() {
    this(10);
  }
  /**
   * Constructor
   * Creates a default instance of the Rosenbrock problem
   *
   * @param numberOfVariables Number of variables of the problem
   */
  public Rosenbrock(Integer numberOfVariables) {
    numberOfObjectives(1);
    numberOfConstraints(0) ;
    name("Rosenbrock");

    List<Double> lowerLimit = new ArrayList<>(numberOfVariables) ;
    List<Double> upperLimit = new ArrayList<>(numberOfVariables) ;

    for (int i = 0; i < numberOfVariables; i++) {
      lowerLimit.add(-30.0);
      upperLimit.add(30.0);
    }

    variableBounds(lowerLimit, upperLimit);
  }

  /** Evaluate() method */
  @Override
  public DoubleSolution computeFitness(DoubleSolution solution) {
    int numberOfVariables = numberOfVariables() ;

    double[] x = IntStream.range(0, numberOfVariables).mapToDouble(i -> solution.variables().get(i))
        .toArray();

    double sum = 0.0;

    for (int i = 0; i < numberOfVariables - 1; i++) {
      double temp1 = (x[i] * x[i]) - x[i + 1];
      double temp2 = x[i] - 1.0;
      sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
    }

    solution.objectives()[0] = sum;

    return solution ;
  }
}

