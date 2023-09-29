package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.exp;
import static java.lang.Math.pow;

public class Hartman extends AbstractProblem {

    private static final double[][] a = {
            {3, 10, 30},
            {0.1, 10, 35},
            {3, 10, 30},
            {0.1, 10, 35}
    };
    private static final double[][] p = {
            {0.3689, 0.1170, 0.2673},
            {0.4699, 0.4387, 0.7470},
            {0.1091, 0.8732, 0.5547},
            {0.03815, 0.5743, 0.8828}
    };
    private static final double[] c = {1, 1.2, 3, 3.2};

    public Hartman() {
        super(3, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(0.0, 1.0));
        }
        return solution;
    }

    @Override
    public void evaluate(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);

        double fitness = 0;
        double sum;
        for (int i = 0; i < 4; i++) {
            sum = 0;
            for (int j = 0; j < numberOfVariables; j++) {
                sum += a[i][j] * pow(x[j] - p[i][j], 2);
            }
            fitness += c[i] * exp(sum * (-1));
        }

        solution.setObjective(0, fitness * -1);
    }
}
