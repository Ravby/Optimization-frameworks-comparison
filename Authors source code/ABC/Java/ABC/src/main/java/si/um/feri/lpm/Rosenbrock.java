package si.um.feri.lpm;

import java.util.ArrayList;

public class Rosenbrock extends Problem {

    public Rosenbrock(int dimensions) {
        super("Rosenbrock", dimensions);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-30.0);
            upperLimit.add(30.0);
        }
    }

    @Override
    public double evaluate(double[] x) {

        double fitness = 0;
        double sum = 0.0;
        for (int i = 0; i < dimensions - 1; i++) {
            double temp1 = (x[i] * x[i]) - x[i + 1];
            double temp2 = x[i] - 1.0;
            sum += (100.0 * temp1 * temp1) + (temp2 * temp2);
        }
        fitness = sum;

        return fitness;
    }
}
