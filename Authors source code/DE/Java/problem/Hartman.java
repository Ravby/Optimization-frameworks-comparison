package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class Hartman extends DEProblem {

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

        this.dim = 3;
        name = "Hartman";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 0.0;
            lowerLimit[i] = 1.0;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double evaluate(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {
        double fitness = 0;
        double sum;
        for (int i = 0; i < 4; i++) {
            sum = 0;
            for (int j = 0; j < dim; j++) {
                sum += a[i][j] * pow(x[j] - p[i][j], 2);
            }
            fitness += c[i] * Math.exp(sum * (-1));
        }
        return fitness * -1;
    }
}
