package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.pow;

public class GoldsteinPrice extends LoggingAbstractProblem {

    public GoldsteinPrice() {
        super(2, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-2.0, 2.0));
        }
        return solution;
    }

    @Override
    public void computeFitness(Solution solution) {
        
        double[] x = EncodingUtils.getReal(solution);

        solution.setObjective(0, (1 + pow(x[0] + x[1] + 1, 2) * (19 - 14 * x[0] + 3 * x[0] * x[0] - 14 * x[1] + 6 * x[0] * x[1] + 3 * x[1] * x[1])) *
                (30 + pow(2 * x[0] - 3 * x[1], 2) * (18 - 32 * x[0] + 12 * x[0] * x[0] + 48 * x[1] - 36 * x[0] * x[1] + 27 * x[1] * x[1])));
    }
}
