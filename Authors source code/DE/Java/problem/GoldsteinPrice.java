package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class GoldsteinPrice extends LoggingDEProblem {

    public GoldsteinPrice() {

        this.dim = 2;
        name = "GoldsteinPrice";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = -2.0;
            lowerLimit[i] = 2.0;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }


    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {
        double fitness = 0;
        fitness = (1 + pow(x[0] + x[1] + 1, 2) * (19 - 14 * x[0] + 3 * pow(x[0], 2) - 14 * x[1] + 6 * x[0] * x[1] + 3 * pow(x[1], 2))) *
                (30 + pow(2 * x[0] - 3 * x[1], 2) * (18 - 32 * x[0] + 12 * pow(x[0], 2) + 48 * x[1] - 36 * x[0] * x[1] + 27 * pow(x[1], 2)));
        return fitness;
    }
}
