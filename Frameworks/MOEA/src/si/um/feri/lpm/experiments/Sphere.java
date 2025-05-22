package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

/**
 * Class representing a Sphere problem.
 */
@SuppressWarnings("serial")
public class Sphere extends LoggingAbstractProblem {
    /**
     * Constructor
     */
    public Sphere() {
        this(10);
    }

    /**
     * Constructor
     */
    public Sphere(Integer numberOfVariables) {
        super(numberOfVariables, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-100.0, 100.0));
        }
        return solution;
    }

    /**
     * Evaluate() method
     */
    @Override
    public void computeFitness(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);
        double sum = 0.0;
        for (double v : x) {
            sum += v * v;
        }

        solution.setObjective(0, sum);
    }
}

