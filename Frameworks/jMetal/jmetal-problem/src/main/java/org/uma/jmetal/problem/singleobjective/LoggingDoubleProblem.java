package org.uma.jmetal.problem.singleobjective;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import java.util.ArrayList;
import java.util.List;

public abstract class LoggingDoubleProblem extends AbstractDoubleProblem {
    private long evaluationCount;
    private double bestFitness;
    private boolean isFirstEvaluation;
    private List<double[]> improvements; // Stores [evaluationCount, fitness] pairs

    public LoggingDoubleProblem() {
        evaluationCount = 0;
        bestFitness = Double.POSITIVE_INFINITY; // Minimization problem
        isFirstEvaluation = true;
        improvements = new ArrayList<>();
    }

    @Override
    public DoubleSolution evaluate(DoubleSolution solution) {
        evaluationCount++;
        DoubleSolution evaluatedSolution = computeFitness(solution);
        double fitness = evaluatedSolution.objectives()[0];

        // Log improvement (minimization: lower is better)
        if (isFirstEvaluation || fitness < bestFitness) {
            improvements.add(new double[]{evaluationCount, fitness});
            bestFitness = fitness;
            isFirstEvaluation = false;
        }

        return solution;
    }

    // Abstract method for subclasses to implement fitness computation
    protected abstract DoubleSolution computeFitness(DoubleSolution solution);

    // Get logged improvements
    public List<double[]> getImprovements() {
        return improvements;
    }

    public void reset() {
        evaluationCount = 0;
        bestFitness = Double.POSITIVE_INFINITY;
        isFirstEvaluation = true;
        improvements.clear();
    }
}