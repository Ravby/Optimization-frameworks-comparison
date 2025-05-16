package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.problem.AbstractProblem;

import java.util.ArrayList;
import java.util.List;

public abstract class LoggingAbstractProblem extends AbstractProblem {
    private long evaluationCount;
    private double bestFitness;
    private boolean isFirstEvaluation;
    private List<double[]> improvements; // Stores [evaluationCount, fitness] pairs

    public LoggingAbstractProblem(int numberOfVariables, int numberOfObjectives, int numberOfConstraints) {
        super(numberOfVariables, numberOfObjectives, numberOfConstraints);
        evaluationCount = 0;
        bestFitness = Double.POSITIVE_INFINITY; // Minimization problem
        isFirstEvaluation = true;
        improvements = new ArrayList<>();
    }

    @Override
    public void evaluate(Solution solution) {
        evaluationCount++;
        computeFitness(solution);
        double fitness = solution.getObjective(0);

        // Log improvement (minimization: lower is better)
        if (isFirstEvaluation || fitness < bestFitness) {
            improvements.add(new double[]{evaluationCount, fitness});
            bestFitness = fitness;
            isFirstEvaluation = false;
        }

        solution.setObjective(0, fitness);
    }

    // Abstract method for subclasses to implement fitness computation
    protected abstract void computeFitness(Solution solution);

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