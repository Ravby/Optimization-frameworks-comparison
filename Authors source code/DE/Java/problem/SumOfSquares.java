package DeApp1.problem;

import DeApp1.de.T_DEOptimizer;

import java.util.ArrayList;

public class SumOfSquares extends LoggingDEProblem {

        public SumOfSquares(int dimensions) {
            this.dim = dimensions;
            name = "SumOfSquares";
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
            double sum = 0.0;
            for (int i = 0; i < dim; i++) {
                sum += (i + 1) * x[i] * x[i];
            }
            return sum;
        }
}
