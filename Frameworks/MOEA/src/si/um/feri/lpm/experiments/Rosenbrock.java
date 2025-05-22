package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import java.util.stream.IntStream;

public class Rosenbrock extends LoggingAbstractProblem {

    public Rosenbrock() {
        this(10);
    }

    /**
     * Constructor
     * Creates a default instance of the Rosenbrock problem
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public Rosenbrock(Integer numberOfVariables) {
        super(numberOfVariables, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-30.0, 30.0));
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

        for (int i = 0; i < numberOfVariables - 1; i++) {
            double temp1 = (x[i] * x[i]) - x[i + 1];
            double temp2 = x[i] - 1.0;
            sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
        }

        solution.setObjective(0, sum);
    }
}

