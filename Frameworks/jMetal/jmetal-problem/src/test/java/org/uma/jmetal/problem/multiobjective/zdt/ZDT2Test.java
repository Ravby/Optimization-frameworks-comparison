package org.uma.jmetal.problem.multiobjective.zdt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.uma.jmetal.problem.doubleproblem.DoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

class ZDT2Test {
  @Test
  void constructorMustCreateAValidInstanceUsingTheDefaultConstructor() {
    int defaultNumberOfVariables = 30 ;
    DoubleProblem problem = new ZDT2() ;

    assertThat(problem.numberOfVariables()).isEqualTo(defaultNumberOfVariables) ;
    assertThat(problem.numberOfObjectives()).isEqualTo(2) ;
    assertThat(problem.numberOfConstraints()).isZero() ;
    assertThat(problem.name()).isEqualTo("ZDT2") ;

    assertThat(problem.variableBounds().get(0).getLowerBound()).isZero() ;
    assertThat(problem.variableBounds().get(0).getUpperBound()).isEqualTo(1) ;
    assertThat(problem.variableBounds().get(problem.numberOfVariables()-1).getLowerBound()).isZero() ;
    assertThat(problem.variableBounds().get(problem.numberOfVariables()-1).getUpperBound()).isEqualTo(1) ;
  }

  @Test
  void constructorMustCreateAValidInstanceWhenIndicatingTheNumberOVariables() {
    int numberOfVariables = 10 ;
    DoubleProblem problem = new ZDT2(numberOfVariables) ;

    assertThat(problem.numberOfVariables()).isEqualTo(numberOfVariables) ;
    assertThat(problem.numberOfObjectives()).isEqualTo(2) ;
    assertThat(problem.numberOfConstraints()).isZero() ;
    assertThat(problem.name()).isEqualTo("ZDT2") ;

    assertThat(problem.variableBounds().get(0).getLowerBound()).isZero() ;
    assertThat(problem.variableBounds().get(0).getUpperBound()).isEqualTo(1) ;
    assertThat(problem.variableBounds().get(problem.numberOfVariables()-1).getLowerBound()).isZero() ;
    assertThat(problem.variableBounds().get(problem.numberOfVariables()-1).getUpperBound()).isEqualTo(1) ;
  }

  @Test
  void createSolutionGeneratesAValidSolution() {
    int numberOfVariables = 10 ;

    DoubleProblem problem = new ZDT2(numberOfVariables) ;
    DoubleSolution solution = problem.createSolution() ;

    assertThat(solution).isNotNull() ;
    assertThat(solution.variables()).hasSize(numberOfVariables) ;
    assertThat(solution.objectives()).hasSize(2) ;
    assertThat(solution.constraints()).isEmpty() ;
  }
}