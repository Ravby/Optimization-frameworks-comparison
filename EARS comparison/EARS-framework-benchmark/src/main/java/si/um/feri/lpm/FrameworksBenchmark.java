package si.um.feri.lpm;

import org.um.feri.ears.algorithms.NumberAlgorithm;
import org.um.feri.ears.benchmark.SOBenchmark;
import org.um.feri.ears.problems.*;

public class FrameworksBenchmark extends SOBenchmark<NumberSolution<Double>, NumberSolution<Double>, DoubleProblem, NumberAlgorithm> {
    protected int dimension = 3;

    public FrameworksBenchmark() {
        this(1e-7);
    }

    public FrameworksBenchmark(double drawLimit) {
        super();
        name = "Frameworks benchmark";
        this.drawLimit = drawLimit;
        maxEvaluations = 3000;
        maxIterations = 0;
    }

    @Override
    protected void addTask(DoubleProblem p, StopCriterion stopCriterion, int eval, long time, int maxIterations) {
        tasks.add(new Task<>(p, stopCriterion, eval, time, maxIterations));
    }

    @Override
    public void initAllProblems() {

        addTask(new DummyProblem("AckleyD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("BraninD2", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("GoldsteinPriceD2", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("GriewankD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("HartmanD3", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("RastriginD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("RosenbrockD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("SchwefelD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("ShekelsFoxholesD2", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("SixHumpCamelBackD2", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("SphereD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("SumOfSquaresD60", false), stopCriterion, maxEvaluations, 0, maxIterations);
    }
}