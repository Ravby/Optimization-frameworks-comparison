package org.uma.jmetal.component.catalogue.common.evaluation.impl;

import java.util.List;
import org.uma.jmetal.component.catalogue.common.evaluation.Evaluation;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.errorchecking.Check;

/**
 * Class that evaluates a list of solutions sequentially.
 *
 * @author Antonio J. Nebro (ajnebro@uma.es)
 *
 * @param <S>
 */
public class SequentialEvaluation<S extends Solution<?>> implements Evaluation<S> {

  private int computedEvaluations;
  private final Problem<S> problem;

  public SequentialEvaluation(Problem<S> problem) {
    Check.notNull(problem);
    this.problem = problem;
    computedEvaluations = 0;
  }

  @Override
  public List<S> evaluate(List<S> solutionList) {
    Check.notNull(solutionList);
    solutionList.forEach(problem::evaluate);
    computedEvaluations = solutionList.size();

    return solutionList;
  }

  @Override
  public int computedEvaluations() {
    return computedEvaluations;
  }

  @Override
  public Problem<S> problem() {
    return problem;
  }
}
