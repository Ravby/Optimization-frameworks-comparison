package org.uma.jmetal.problem.multiobjective;

import java.util.ArrayList;
import java.util.List;
import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

/** Class representing problem Viennet4 */
@SuppressWarnings("serial")
public class Viennet4 extends AbstractDoubleProblem {
  /** Constructor. Creates a default instance of the Viennet4 problem. */
  public Viennet4() {
    int numberOfVariables = 2 ;
    numberOfObjectives(3);
    numberOfConstraints(3);
    name("Viennet4");

    List<Double> lowerLimit = new ArrayList<>(numberOfVariables);
    List<Double> upperLimit = new ArrayList<>(numberOfVariables);

    for (int i = 0; i < numberOfVariables; i++) {
      lowerLimit.add(-4.0);
      upperLimit.add(4.0);
    }

    variableBounds(lowerLimit, upperLimit);
  }

  /** Evaluate() method */
  @Override
  public DoubleSolution evaluate(DoubleSolution solution) {
    int numberOfVariables = numberOfVariables();

    double[] f = new double[solution.objectives().length];
    double[] x = new double[numberOfVariables];

    for (int i = 0; i < numberOfVariables; i++) {
      x[i] = solution.variables().get(i);
    }

    f[0] = (x[0] - 2.0) * (x[0] - 2.0) / 2.0 + (x[1] + 1.0) * (x[1] + 1.0) / 13.0 + 3.0;

    f[1] =
        (x[0] + x[1] - 3.0) * (x[0] + x[1] - 3.0) / 175.0
            + (2.0 * x[1] - x[0]) * (2.0 * x[1] - x[0]) / 17.0
            - 13.0;

    f[2] =
        (3.0 * x[0] - 2.0 * x[1] + 4.0) * (3.0 * x[0] - 2.0 * x[1] + 4.0) / 8.0
            + (x[0] - x[1] + 1.0) * (x[0] - x[1] + 1.0) / 27.0
            + 15.0;

    for (int i = 0; i < solution.objectives().length; i++) {
      solution.objectives()[i] = f[i];
    }

    evaluateConstraints(solution);
    return solution ;
  }

  /** EvaluateConstraints() method */
  public void evaluateConstraints(DoubleSolution solution) {
    double[] constraint = new double[this.numberOfConstraints()];

    double x1 = solution.variables().get(0);
    double x2 = solution.variables().get(1);

    constraint[0] = -x2 - (4.0 * x1) + 4.0;
    constraint[1] = x1 + 1.0;
    constraint[2] = x2 - x1 + 2.0;

    for (int i = 0; i < numberOfConstraints(); i++) {
      solution.constraints()[i] = constraint[i];
    }
  }
}
