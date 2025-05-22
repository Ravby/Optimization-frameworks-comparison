package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class Schwefel extends LoggingDEProblem {

    public Schwefel(int dimensions) {
        this.dim = dimensions;
        name = "Schwefel";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 100;
            lowerLimit[i] = -100;
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
        for (int i = 0; i < dim; i++) {
            sum = 0;
            for (int j = 0; j < i; j++) {
                sum += x[i];
            }
            fitness += pow(sum, 2);
        }

        return fitness;
    }
}
