package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

/**
 * Class representing problem Griewank
 */
public class Griewank extends AbstractProblem {

    public Griewank() {
        this(10);
    }

    /**
     * Constructor
     * Creates a default instance of the Griewank problem
     *
     * @param numberOfVariables Number of variables of the problem
     */
    public Griewank(Integer numberOfVariables) {
        super(numberOfVariables, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-600.0, 600.0));
        }
        return solution;
    }

    /**
     * Evaluate() method
     */
    @Override
    public void evaluate(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);

        double sum = 0.0;
        double mult = 1.0;
        double d = 4000.0;
        for (int var = 0; var < numberOfVariables; var++) {
            sum += x[var] * x[var];
            mult *= Math.cos(x[var] / Math.sqrt(var + 1));
        }

        solution.setObjective(0, 1.0 / d * sum - mult + 1.0);
    }
}

