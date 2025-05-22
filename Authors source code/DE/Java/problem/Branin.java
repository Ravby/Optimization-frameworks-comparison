package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

import static java.lang.Math.*;

public class Branin extends LoggingDEProblem {

    public Branin() {
        this.dim = 2;
        name = "Branin";

        upperLimit = new double[dim];
        lowerLimit = new double[dim];

        lowerLimit[0] = -5.0;
        upperLimit[0] = 10.0;

        lowerLimit[1] = 0.0;
        upperLimit[1] = 15.0;
    }
    @Override
    public boolean completed() {
        return false;
    }


    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {

        double fitness = pow(x[1] - (5.1 / (4 * PI * PI)) * x[0] * x[0] + (5.0 / PI) * x[0] - 6, 2) + 10 * (1 - 1.0 / (8.0 * PI)) * cos(x[0]) + 10;

        return fitness;
    }
}
