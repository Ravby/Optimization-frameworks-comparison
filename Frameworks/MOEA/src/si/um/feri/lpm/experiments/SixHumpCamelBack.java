package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.pow;

public class SixHumpCamelBack extends LoggingAbstractProblem {

    public SixHumpCamelBack() {
        super(2, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-5.0, 5.0));
        }
        return solution;
    }

    @Override
    public void computeFitness(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);
        
        solution.setObjective(0, 4 * pow(x[0], 2)
                - 2.1 * pow(x[0], 4)
                + (1.0 / 3.0) * pow(x[0], 6)
                + x[0] * x[1]
                - 4 * pow(x[1], 2)
                + 4 * pow(x[1], 4));
    }
}
