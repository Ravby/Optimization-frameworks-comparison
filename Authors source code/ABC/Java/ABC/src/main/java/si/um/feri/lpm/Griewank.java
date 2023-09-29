package si.um.feri.lpm;

import java.util.ArrayList;

public class Griewank extends Problem {

    public Griewank(int dimensions) {
        super("Griewank", dimensions);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-600.0);
            upperLimit.add(600.0);
        }
    }

    @Override
    public double evaluate(double[] x) {

        double fitness = 0;
        double sum = 0.0;
        double mult = 1.0;
        double d = 4000.0;
        for (int i = 0; i < dimensions; i++) {
            sum += x[i] * x[i];
            mult *= Math.cos(x[i] / Math.sqrt(i + 1));
        }
        fitness = 1.0 / d * sum - mult + 1.0;

        return fitness;
    }
}
