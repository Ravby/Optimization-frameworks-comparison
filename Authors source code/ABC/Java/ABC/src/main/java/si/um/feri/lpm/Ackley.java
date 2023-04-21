package si.um.feri.lpm;

import java.util.ArrayList;

public class Ackley extends Problem {

    public Ackley(int dimensions) {
        super("Ackley", dimensions);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-32.0);
            upperLimit.add(32.0);
        }
    }

    @Override
    public double evaluate(double[] x) {

        double fitness = 0;
        double a = 20.0, b = 0.2, c = 2 * Math.PI;
        double sum1 = 0, sum2 = 0;

        for (int i = 0; i < dimensions; i++) {
            sum1 += x[i] * x[i];
            sum2 += Math.cos(c * x[i]);
        }
        fitness = -a * Math.exp(-b * Math.sqrt(1.0 / dimensions * sum1)) - Math.exp(1.0 / dimensions * sum2) + a + Math.E;

        return fitness;
    }
}