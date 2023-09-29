package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

public class Griewank extends DEProblem {

    public Griewank(int dimensions) {

        this.dim = dimensions;
        name = "Griewank";
        upperLimit = new double[dim];
        lowerLimit = new double[dim];
        for (int i = 0; i < dim; i++) {
            upperLimit[i] = 600;
            lowerLimit[i] = -600;
        }
    }

    @Override
    public boolean completed() {
        return false;
    }

    @Override
    public double evaluate(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {

        double fitness = 0;
        double sum = 0.0;
        double mult = 1.0;
        double d = 4000.0;
        for (int i = 0; i < dim; i++) {
            sum += x[i] * x[i];
            mult *= Math.cos(x[i] / Math.sqrt(i + 1));
        }
        fitness = 1.0 / d * sum - mult + 1.0;

        return fitness;
    }
}
