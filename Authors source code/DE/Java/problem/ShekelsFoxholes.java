package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class ShekelsFoxholes extends LoggingDEProblem {
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

        this.dim = 2;
        name = "ShekelsFoxholes";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 65.536;
            lowerLimit[i] = -65.536;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {
        double fitness = 0;
        double sum;
        for (int j = 0; j < 25; j++) {
            sum = 0;
            for (int i = 0; i < dim; i++) {
                sum += pow(x[i] - a[j][i], 6);
            }
            sum += j + 1;
            fitness += 1.0 / sum;
        }
        fitness += 1.0 / 500.0;
        fitness = pow(fitness, -1);

        return fitness;
    }
}
