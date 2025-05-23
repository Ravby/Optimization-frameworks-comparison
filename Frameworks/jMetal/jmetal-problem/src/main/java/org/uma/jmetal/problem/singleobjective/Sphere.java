package org.uma.jmetal.problem.singleobjective;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/**
 * Class representing a Sphere problem.
 */
@SuppressWarnings("serial")
public class Sphere extends LoggingDoubleProblem {
  /** Constructor */
  public Sphere() {
    this(10) ;
  }

  /** Constructor */
  public Sphere(Integer numberOfVariables) {
    numberOfObjectives(1);
    name("Sphere");

    List<Double> lowerLimit = new ArrayList<>() ;
    List<Double> upperLimit = new ArrayList<>(numberOfVariables) ;

    IntStream.range(0, numberOfVariables).forEach(i -> {
      lowerLimit.add(-100.0);
      upperLimit.add(100.0);
    });

    variableBounds(lowerLimit, upperLimit);
  }

  /** Evaluate() method */
  @Override
  public DoubleSolution computeFitness(DoubleSolution solution) {
    double sum = 0.0;
    for (double v : solution.variables()) {
      sum += v * v;
    }

    solution.objectives()[0] = sum;

    return solution;
  }
}

