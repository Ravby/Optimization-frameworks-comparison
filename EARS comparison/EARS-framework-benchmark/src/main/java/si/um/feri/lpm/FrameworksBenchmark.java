package si.um.feri.lpm;

import org.um.feri.ears.algorithms.NumberAlgorithm;
import org.um.feri.ears.benchmark.SOBenchmark;
import org.um.feri.ears.problems.*;

public class FrameworksBenchmark extends SOBenchmark<NumberSolution<Double>, NumberSolution<Double>, DoubleProblem, NumberAlgorithm> {
    protected int dimension = 3;

    public FrameworksBenchmark() {
        this(0.000001);
    }

    public FrameworksBenchmark(double draw_limit) {
        super();
        name = "Frameworks benchmark";
        this.drawLimit = 1e-16; //1e-10;
        maxEvaluations = 3000;
        maxIterations = 0;
    }

    @Override
    protected void addTask(DoubleProblem p, StopCriterion stopCriterion, int eval, long time, int maxIterations) {
        tasks.add(new Task<>(p, stopCriterion, eval, time, maxIterations));
    }

    @Override
    public void initAllProblems() {

        addTask(new DummyProblem("SphereD10", true), stopCriterion, maxEvaluations, 0, maxIterations);
        addTask(new DummyProblem("AckleyD10", true), stopCriterion, maxEvaluations, 0, maxIterations);
    }
}