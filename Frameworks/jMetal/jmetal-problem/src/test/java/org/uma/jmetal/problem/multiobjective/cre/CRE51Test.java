package org.uma.jmetal.problem.multiobjective.cre;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

class CRE51Test {
  @Test
  void shouldConstructorCreateAProblemWithTheRightProperties() {
    DoubleProblem problem = new CRE51();

    assertEquals(3, problem.numberOfVariables());
    assertEquals(5, problem.numberOfObjectives());
    assertEquals(7, problem.numberOfConstraints());
    assertEquals("CRE51", problem.name());
  }

  @Test
  void shouldEvaluateWorkProperly() {
    DoubleProblem problem = new CRE51();
    DoubleSolution solution = problem.createSolution();
    problem.evaluate(solution);

    assertEquals(3, solution.variables().size());
    assertEquals(5, solution.objectives().length);
    assertEquals(7, solution.constraints().length);
  }
}
