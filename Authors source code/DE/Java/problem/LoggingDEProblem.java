package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;
import java.util.ArrayList;
import java.util.List;

public abstract class LoggingDEProblem extends DEProblem {
    private long evaluationCount;
    private double bestFitness;
    private boolean isFirstEvaluation;
    private List<double[]> improvements; // Stores [evaluationCount, fitness] pairs

    public LoggingDEProblem() {
        evaluationCount = 0;
        bestFitness = Double.POSITIVE_INFINITY; // Minimization problem
        isFirstEvaluation = true;
        improvements = new ArrayList<>();
    }

    @Override
    public double evaluate(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {
        evaluationCount++;
        double fitness = computeFitness(t_DEOptimizer, x, dim);

        // Log improvement (minimization: lower is better)
        if (isFirstEvaluation || fitness < bestFitness) {
            improvements.add(new double[]{evaluationCount, fitness});
            bestFitness = fitness;
            isFirstEvaluation = false;
        }

        return fitness;
    }

    // Abstract method for subclasses to implement fitness computation
    protected abstract double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim);

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