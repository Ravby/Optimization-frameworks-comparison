package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

@SuppressWarnings("serial")
public class Rastrigin extends AbstractProblem {

    public Rastrigin() {
        this(10);
    }

    /**
     * Constructor
     * Creates a default instance of the Rastrigin problem
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public Rastrigin(Integer numberOfVariables) {
        super(numberOfVariables, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-5.12, 5.12));
        }
        return solution;
    }

    /**
     * Evaluate() method
     */
    @Override
    public void evaluate(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);

        double result = 0.0;
        double a = 10.0;
        double w = 2 * Math.PI;

        for (int i = 0; i < numberOfVariables; i++) {
            result += x[i] * x[i] - a * Math.cos(w * x[i]);
        }
        result += a * numberOfVariables;

        solution.setObjective(0, result);
    }
}

