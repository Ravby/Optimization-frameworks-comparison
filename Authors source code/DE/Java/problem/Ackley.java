package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

public class Ackley extends LoggingDEProblem {

    public Ackley(int dimensions) {
        this.dim = dimensions;
        name = "Ackley";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 32;
            lowerLimit[i] = -32;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {

        double fitness = 0;
        double a = 20.0, b = 0.2, c = 2 * Math.PI;
        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < dim; i++) {
            sum1 += x[i] * x[i];
            sum2 += Math.cos(c * x[i]);
        }
        fitness = -a * Math.exp(-b * Math.sqrt(1.0 / dim * sum1)) - Math.exp(1.0 / dim * sum2) + a + Math.E;

        return fitness;
    }
}