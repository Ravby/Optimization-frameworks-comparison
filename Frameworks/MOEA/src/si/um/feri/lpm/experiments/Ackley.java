package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.*;

public class Ackley extends LoggingAbstractProblem {

    public Ackley() {
        this(10);
    }

    public Ackley(Integer numberOfVariables) {
        super(numberOfVariables, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        for (int i = 0; i < numberOfVariables; i++) {
            solution.setVariable(i, EncodingUtils.newReal(-32.0, 32.0));
        }
        return solution;
    }

    @Override
    public void computeFitness(Solution solution) {
        double a = 20.0, b = 0.2, c = 2 * PI;
        double sum1 = 0, sum2 = 0;

        double[] x = EncodingUtils.getReal(solution);

        for (double v : x) {
            sum1 += pow(v, 2);
            sum2 += cos(c * v);
        }
        solution.setObjective(0, -a * exp(-b * sqrt(1.0 / numberOfVariables * sum1)) - exp(1.0 / numberOfVariables * sum2) + a + E);
    }
}
