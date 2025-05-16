package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

public class Rastrigin extends LoggingDEProblem {

    public Rastrigin(int dimensions) {

        this.dim = dimensions;
        name = "Rastrigin";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 5.12;
            lowerLimit[i] = -5.12;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {

        double fitness = 0;
        double a = 10.0;
        double w = 2 * Math.PI;

        for (int i = 0; i < dim; i++) {
            fitness += x[i] * x[i] - a * Math.cos(w * x[i]);
        }
        fitness += a * dim;

        return fitness;
    }
}
