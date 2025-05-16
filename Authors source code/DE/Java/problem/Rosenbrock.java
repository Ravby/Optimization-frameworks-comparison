package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

public class Rosenbrock extends LoggingDEProblem {

    public Rosenbrock(int dimensions) {

        this.dim = dimensions;
        name = "Rosenbrock";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 30.0;
            lowerLimit[i] = -30.0;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double computeFitness(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {

        double fitness = 0;
        double sum = 0.0;
        for (int i = 0; i < dim - 1; i++) {
            double temp1 = (x[i] * x[i]) - x[i + 1];
            double temp2 = x[i] - 1.0;
            sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
        }
        fitness = sum;

        return fitness;
    }
}
