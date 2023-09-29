package si.um.feri.lpm;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class Schwefel extends Problem {

    public Schwefel(int dimensions) {
        super("Schwefel", dimensions);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-100.0);
            upperLimit.add(100.0);
        }
    }

    @Override
    public double evaluate(double[] x) {

        double fitness = 0;
        double sum;
        for (int i = 0; i < dimensions; i++) {
            sum = 0;
            for (int j = 0; j < i; j++) {
                sum += x[i];
            }
            fitness += pow(sum, 2);
        }

        return fitness;
    }
}
