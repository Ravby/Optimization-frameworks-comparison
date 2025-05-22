package si.um.feri.lpm.experiments;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.problem.AbstractProblem;

import static java.lang.Math.pow;

public class Schwefel extends LoggingAbstractProblem {

    public Schwefel() {
        this(10);
    }

    public Schwefel(Integer numberOfVariables) {
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

    @Override
    public void computeFitness(Solution solution) {

        double[] x = EncodingUtils.getReal(solution);

        double fitness = 0;
        double sum;
        for (int i = 0; i < numberOfVariables; i++) {
            sum = 0;
            for (int j = 0; j < i; j++) {
                sum += x[i];
            }
            fitness += pow(sum, 2);
        }

        solution.setObjective(0, fitness);
    }
}
