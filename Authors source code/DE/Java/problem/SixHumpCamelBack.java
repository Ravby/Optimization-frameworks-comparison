package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class SixHumpCamelBack extends LoggingDEProblem {

    public SixHumpCamelBack() {

        this.dim = 2;
        name = "SixHumpCamelBack";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 5.0;
            lowerLimit[i] = -5.0;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {
        double fitness = 0;
        fitness = 4 * pow(x[0], 2)
                - 2.1 * pow(x[0], 4)
                + (1.0 / 3.0) * pow(x[0], 6)
                + x[0] * x[1]
                - 4 * pow(x[1], 2)
                + 4 * pow(x[1], 4);
        return fitness;
    }
}
