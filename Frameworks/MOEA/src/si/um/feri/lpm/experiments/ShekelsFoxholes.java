package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.pow;

public class ShekelsFoxholes extends AbstractProblem {

    private static final double[][] a = {{-32, -32},
            {-16, -32},
            {0, -32},
            {16, -32},
            {32, -32},
            {-32, -16},
            {-16, -16},
            {0, -16},
            {16, -16},
            {32, -16},
            {-32, 0},
            {-16, 0},
            {0, 0},
            {16, 0},
            {32, 0},
            {-32, 16},
            {-16, 16},
            {0, 16},
            {16, 16},
            {32, 16},
            {-32, 32},
            {-16, 32},
            {0, 32},
            {16, 32},
            {32, 32}
    };

    public ShekelsFoxholes() {
        super(2, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-65.536, 65.536));
        }
        return solution;
    }

    @Override
    public void evaluate(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);

        double fitness = 0;
        double sum;
        for (int j = 0; j < 25; j++) {
            sum = 0;
            for (int i = 0; i < numberOfVariables; i++) {
                sum += pow(x[i] - a[j][i], 6);
            }
            sum += j + 1;
            fitness += 1.0 / sum;
        }
        fitness += 1.0 / 500.0;

        solution.setObjective(0, pow(fitness, -1));
    }
}
