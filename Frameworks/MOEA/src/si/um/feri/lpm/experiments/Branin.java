package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.*;

public class Branin extends LoggingAbstractProblem {

    public Branin() {
        super(2, 1, 0);
    }

    @Override
    public Solution newSolution() {
        Solution solution = new Solution(numberOfVariables, 1, 0);
        solution.setVariable(0, EncodingUtils.newReal(-5.0, 10));
        solution.setVariable(1, EncodingUtils.newReal(0.0, 15));
        return solution;
    }

    @Override
    public void computeFitness(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);
        
        solution.setObjective(0, pow(x[1] - (5.1 / (4 * PI * PI)) * x[0] * x[0] + (5.0 / PI) * x[0] - 6, 2) + 10 * (1 - 1.0 / (8.0 * PI)) * cos(x[0]) + 10);
    }
}
