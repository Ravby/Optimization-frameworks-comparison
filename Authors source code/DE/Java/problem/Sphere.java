package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

public class Sphere extends DEProblem {

    public Sphere(int dimensions) {
        this.dim = dimensions;
        name = "Sphere";
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
    public double evaluate(T_DEOptimizer t_DEOptimizer, double[] x, int dim) {
        double fitness = 0;
        for (int i = 0; i < dim; i++) {
            fitness += Math.pow(x[i], 2);
        }
        return fitness;
    }
}
