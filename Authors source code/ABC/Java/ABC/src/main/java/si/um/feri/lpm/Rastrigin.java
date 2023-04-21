package si.um.feri.lpm;

import java.util.ArrayList;

public class Rastrigin extends Problem {

    public Rastrigin(int dimensions) {
        super("Rastrigin", dimensions);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-5.12);
            upperLimit.add(5.12);
        }
    }

    @Override
    public double evaluate(double[] x) {

        double fitness = 0;
        double a = 10.0;
        double w = 2 * Math.PI;

        for (int i = 0; i < dimensions; i++) {
            fitness += x[i] * x[i] - a * Math.cos(w * x[i]);
        }
        fitness += a * dimensions;

        return fitness;
    }
}
