package si.um.feri.lpm.problems;

import org.um.feri.ears.problems.DoubleProblem;
import java.util.ArrayList;
import java.util.List;

public abstract class LoggingDoubleProblem extends DoubleProblem {
    private long evaluationCount;
    private double bestFitness;
    private boolean isFirstEvaluation;
    private List<double[]> improvements; // Stores [evaluationCount, fitness] pairs

    public LoggingDoubleProblem(String name, int numberOfDimensions, int numberOfGlobalOptima, int numberOfObjectives, int numberOfConstraints) {
        super(name, numberOfDimensions, numberOfGlobalOptima, numberOfObjectives, numberOfConstraints);
        evaluationCount = 0;
        bestFitness = Double.POSITIVE_INFINITY; // Minimization problem
        isFirstEvaluation = true;
        improvements = new ArrayList<>();
    }

    @Override
    public double eval(double[] x) {
        evaluationCount++;
        double fitness = computeFitness(x);

        // Log improvement (minimization: lower is better)
        if (isFirstEvaluation || fitness < bestFitness) {
            improvements.add(new double[]{evaluationCount, fitness});
            bestFitness = fitness;
            isFirstEvaluation = false;
        }
        return fitness;
    }

    // Abstract method for subclasses to implement fitness computation
    protected abstract double computeFitness(double[] solution);

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