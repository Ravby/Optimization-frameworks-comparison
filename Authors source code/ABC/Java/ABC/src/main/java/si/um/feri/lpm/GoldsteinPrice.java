package si.um.feri.lpm;

import java.util.ArrayList;

import static java.lang.Math.pow;

public class GoldsteinPrice extends Problem {

    public GoldsteinPrice() {
        super("GoldsteinPrice", 2);
        lowerLimit = new ArrayList<>();
        upperLimit = new ArrayList<>();

        for (int i = 0; i < dimensions; i++) {
            lowerLimit.add(-2.0);
            upperLimit.add(2.0);
        }
    }

    @Override
    public double evaluate(double[] x) {
        double fitness = 0;
        fitness = (1 + pow(x[0] + x[1] + 1, 2) * (19 - 14 * x[0] + 3 * pow(x[0], 2) - 14 * x[1] + 6 * x[0] * x[1] + 3 * pow(x[1], 2))) *
                (30 + pow(2 * x[0] - 3 * x[1], 2) * (18 - 32 * x[0] + 12 * pow(x[0], 2) + 48 * x[1] - 36 * x[0] * x[1] + 27 * pow(x[1], 2)));
        return fitness;
    }
}
